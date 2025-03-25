package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.scheduleDto.*;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.dto.response.scheduleDto.GetDateTimeRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetDetailScheduleRes;
import org.se06203.besgtn.dto.response.scheduleDto.GetListScheduleRes;
import org.se06203.besgtn.persistence.entity.Categories;
import org.se06203.besgtn.persistence.entity.ChildSchedule;
import org.se06203.besgtn.persistence.entity.ScheduleException;
import org.se06203.besgtn.persistence.entity.Schedules;
import org.se06203.besgtn.persistence.repository.CategoryRepository;
import org.se06203.besgtn.persistence.repository.ScheduleRepository;
import org.se06203.besgtn.utils.mapper.CategoriesMapper;
import org.se06203.besgtn.utils.mapper.ChildScheduleMapper;
import org.se06203.besgtn.utils.mapper.ScheduleMapper;
import org.se06203.besgtn.utils.method.ScheduleUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.se06203.besgtn.utils.ConvertDateTime.convertStringToDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {
    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final ChildScheduleMapper childScheduleMapper;
    private final CategoryRepository categoryRepository;
    private final CategoriesMapper categoriesMapper;

    @Transactional
    public void createSchedule(InsertScheduleReq req) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var schedule = scheduleMapper.mapInsertScheduleReqToSchedules(req);

        var nextDate = convertStringToDate(req.getDate());
        var repeatEndDate = convertStringToDate(req.getRepeatEndDate());

        List<ChildSchedule> child = new ArrayList<>();
        while (nextDate.isBefore(repeatEndDate)) {
            child.add(ChildSchedule.builder()
                    .id(new ObjectId().toString())
                    .title(req.getName())
                    .weekday(nextDate.getDayOfWeek().toString())
                    .date(nextDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                    .startTime(req.getStartTime())
                    .endTime(req.getEndTime())
                    .build());

            nextDate = ScheduleUtils.incrementDate(nextDate, req.getRepeat(), repeatEndDate);
        }
        schedule.setChildSchedules(child);
        schedule.setUserId(userId);

        scheduleRepository.save(schedule);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateSchedule(String scheduleId, UpdateScheduleReq req) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var schedule = scheduleRepository.findByIdAndUserId(scheduleId, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        switch (req.getUpdatedType()) {
            case THIS -> updateSingleEvent(schedule, req, userId);
            case CONTINUE -> splitSchedule(schedule, req, userId);
            case ALL -> updateFullSchedule(schedule, req);
        }
    }

    @Transactional
    protected void updateFullSchedule(Schedules schedule, UpdateScheduleReq req) {
        var repeatEndDate = convertStringToDate(req.getRepeatEndDate());
        var currentDate = convertStringToDate(req.getDate());

        var childSchedules = generateChildSchedules(schedule, req, currentDate, repeatEndDate);

        schedule.setChildSchedules(childSchedules);
        scheduleRepository.save(schedule);
    }

    @Transactional
    protected void splitSchedule(Schedules schedule, UpdateScheduleReq req, String userId) {
        var eventId = req.getEventId();
        var updatedEventDate = convertStringToDate(req.getDate());
        var repeatEndDate = convertStringToDate(req.getRepeatEndDate());

        schedule.getChildSchedules().stream()
                .filter(child -> child.getId().equals(eventId))
                .findFirst()
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        schedule.getChildSchedules().removeIf(child ->
                !convertStringToDate(child.getDate()).isBefore(updatedEventDate));

        schedule.setRepeatEndDate(updatedEventDate.minusDays(1)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

        var childSchedules = generateChildSchedules(null, req, updatedEventDate, repeatEndDate);
        saveNewSchedule(req, userId, childSchedules);

        scheduleRepository.save(schedule);
    }

    @Transactional
    protected void updateSingleEvent(Schedules schedule, UpdateScheduleReq req, String userId) {
        var updatedEventDate = convertStringToDate(req.getDate());
        var repeatEndDate = convertStringToDate(req.getRepeatEndDate());

        schedule.getChildSchedules().stream()
                .filter(child -> child.getId().equals(req.getEventId()))
                .findFirst()
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        if (schedule.getExceptions() == null) {
            schedule.setExceptions(new ArrayList<>());
        }
        schedule.getExceptions().add(ScheduleUtils.createScheduleException(req));
        schedule.getChildSchedules().removeIf(child -> child.getId().equals(req.getEventId()));

        var childSchedules = generateChildSchedules(null, req, updatedEventDate, repeatEndDate);
        saveNewSchedule(req, userId, childSchedules);

        scheduleRepository.save(schedule);
    }

    private List<ChildSchedule> generateChildSchedules(Schedules schedule,
                                                       UpdateScheduleReq req,
                                                       LocalDate currentDate,
                                                       LocalDate repeatEndDate) {
        List<ChildSchedule> childSchedules = new ArrayList<>();

        while (currentDate.isBefore(repeatEndDate)) {
            var formattedDate = ScheduleUtils.formatDate(currentDate);
            if (schedule != null && ScheduleUtils.isExceptionDate(schedule, formattedDate)) {
                currentDate = ScheduleUtils.incrementDate(currentDate, req.getRepeat(), repeatEndDate);
                continue;
            }
            childSchedules.add(ScheduleUtils.createChildSchedule(req, currentDate));
            currentDate = ScheduleUtils.incrementDate(currentDate, req.getRepeat(), repeatEndDate);
        }
        return childSchedules;
    }

    private void saveNewSchedule(UpdateScheduleReq req, String userId, List<ChildSchedule> childSchedules) {
        scheduleRepository.save(Schedules.builder()
                .userId(userId)
                .name(req.getName())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .date(req.getDate())
                .repeat(req.getRepeat())
                .categoryId(req.getCategoryId())
                .childSchedules(childSchedules)
                .build());
    }

    public List<GetListScheduleRes> getListSchedulesByDate(String date) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var categories = categoryRepository.findAll();
        var schedules = scheduleRepository.findAllByUserIdAndDate(userId, date);

        Map<String, List<String>> exceptionDates = schedules.stream()
                .collect(Collectors.toMap(
                        Schedules::getId,
                        schedule -> schedule.getExceptions() == null ? Collections.emptyList()
                                : schedule.getExceptions().stream()
                                .map(ScheduleException::getExceptionDate)
                                .toList()
                ));

        return schedules.parallelStream()
                .flatMap(schedule -> {
                    var exceptions = exceptionDates.getOrDefault(schedule.getId(), Collections.emptyList());
                    var color = categories.stream()
                            .filter(category -> category.getId().equals(schedule.getCategoryId()))
                            .map(Categories::getColor)
                            .findFirst()
                            .orElse(null);

                    return schedule.getChildSchedules().stream()
                            .filter(child -> child.getDate().equals(date) && !exceptions.contains(child.getDate()))
                            .map(child -> {
                                var getListScheduleRes = childScheduleMapper.mapSchedulesToGetListScheduleRes(child);
                                getListScheduleRes.setScheduleId(schedule.getId());
                                getListScheduleRes.setDescription(schedule.getDescription());
                                getListScheduleRes.setEventName(schedule.getName());
                                getListScheduleRes.setCategoryColor(color);
                                return getListScheduleRes;
                            });
                })
                .distinct()
                .sorted(Comparator.comparing(GetListScheduleRes::getStartTime))
                .toList();
    }

    public List<GetDateTimeRes> getListSchedules() {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var schedules = scheduleRepository.findAllByUserId(userId);
        var categories = categoryRepository.findAll();

        // Tạo map chứa danh sách exception theo scheduleId
        Map<String, List<String>> exceptionDates = schedules.stream()
                .collect(Collectors.toMap(
                        Schedules::getId,
                        schedule -> schedule.getExceptions() == null ? Collections.emptyList()
                                : schedule.getExceptions().stream()
                                .map(ScheduleException::getExceptionDate)
                                .toList()
                ));

        return schedules.parallelStream()
                .flatMap(schedule -> {
                    var exceptions = exceptionDates.getOrDefault(schedule.getId(), Collections.emptyList());
                    var categoryColor = categories.stream()
                            .filter(category -> category.getId().equals(schedule.getCategoryId()))
                            .map(Categories::getColor)
                            .findFirst()
                            .orElse(null);

                    return schedule.getChildSchedules().stream()
                            .filter(child -> !exceptions.contains(child.getDate()))
                            .map(child -> {
                                var getDateTime = childScheduleMapper.mapSchedulesToGetDateTime(child);
                                getDateTime.setCategoryColor(categoryColor);
                                return getDateTime;
                            });
                })
                .distinct()
                .sorted(Comparator.comparing(GetDateTimeRes::getDate))
                .toList();
    }

    public GetDetailScheduleRes getScheduleDetailByEventId(String eventId) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var categories = categoryRepository.findAll();
        var schedule = scheduleRepository.findByChildSchedulesIdAndUserId(eventId, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        var childSchedules = schedule.getChildSchedules().stream()
                .filter(child -> child.getId().equals(eventId))
                .toList();

        if (childSchedules.isEmpty()) {
            throw new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND);
        }

        var childEvent = childSchedules.get(0);
        var exceptionExists = schedule.getExceptions().stream()
                .anyMatch(ex -> ex.getExceptionDate().equals(childEvent.getDate()));

        if (exceptionExists) {
            return null;
        }

        schedule.setChildSchedules(childSchedules);
        var response = scheduleMapper.mapSchedulesToGetDetailSchedule(schedule);

        var category = categories.stream()
                .filter(cat -> cat.getId().equals(schedule.getCategoryId()))
                .map(categoriesMapper::mapCategoriesToGetCategories)
                .findFirst()
                .orElse(null);

        response.setCategories(category);
        response.setEventId(eventId);
        return response;
    }

    @Transactional
    public void deleteScheduleByEventId(String eventId) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var schedule = scheduleRepository.findByChildSchedulesIdAndUserId(eventId, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        var childEvent = schedule.getChildSchedules().stream()
                .filter(child -> child.getId().equals(eventId))
                .findFirst()
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        if (schedule.getExceptions() == null) {
            schedule.setExceptions(new ArrayList<>());
        }

        schedule.getExceptions().add(ScheduleException.builder()
                .id(childEvent.getId())
                .exceptionDate(childEvent.getDate())
                .statTime(childEvent.getStartTime())
                .endTime(childEvent.getEndTime())
                .build());

        scheduleRepository.save(schedule);
    }
}
