package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.response.PagedData;
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
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.utils.mapper.CategoriesMapper;
import org.se06203.besgtn.utils.mapper.ChildScheduleMapper;
import org.se06203.besgtn.utils.mapper.PagedDataMapper;
import org.se06203.besgtn.utils.mapper.ScheduleMapper;
import org.se06203.besgtn.utils.method.ScheduleUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static org.se06203.besgtn.utils.ConvertDateTime.convertStringToDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsersScheduleService {
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
        schedule.setRepeatEndDate(req.getRepeat() != Constants.RepeatType.NONE
                ? req.getRepeatEndDate()
                : req.getDate());

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
        req.setRepeatEndDate(req.getDate());
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
                .description(req.getDescription())
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
        var categories = categoryRepository.findAllByUserId(userId);
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

    public List<GetDateTimeRes> getListSchedules(YearMonth yearMonth) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        var schedules = scheduleRepository.findAllByUserIdAndChildDateBetween(
                userId,
                yearMonth.atDay(1).toString(),
                yearMonth.atEndOfMonth().toString()
        );
        var categories = categoryRepository.findAllByUserId(userId);

        Map<String, String> categoryColorMap = categories.stream()
                .collect(Collectors.toMap(Categories::getId, Categories::getColor, (a, b) -> a));

        Map<String, Set<String>> exceptionDates = schedules.stream()
                .collect(Collectors.toMap(
                        Schedules::getId,
                        schedule -> Optional.ofNullable(schedule.getExceptions())
                                .orElse(Collections.emptyList())
                                .stream()
                                .map(ScheduleException::getExceptionDate)
                                .collect(Collectors.toSet()),
                        (a, b) -> a
                ));

        List<Map.Entry<Schedules, ChildSchedule>> filteredSchedules = schedules.stream()
                .filter(schedule -> schedule.getChildSchedules() != null)
                .flatMap(schedule -> schedule.getChildSchedules().stream()
                        .filter(child -> child.getDate() != null)
                        .map(child -> Map.entry(schedule, child)))
                .filter(entry -> !exceptionDates.getOrDefault(entry.getKey().getId(),
                                Collections.emptySet())
                        .contains(entry.getValue().getDate()))
                .toList();

        try {
            Map<LocalDate, List<String>> eventDates = filteredSchedules.stream()
                    .map(entry -> Map.entry(
                            LocalDate.parse(entry.getValue().getDate()),
                            categoryColorMap.getOrDefault(entry.getKey().getCategoryId(), null)
                    ))
                    .filter(entry -> entry.getValue() != null)
                    .filter(entry -> !entry.getKey().isBefore(yearMonth.atDay(1)) &&
                            !entry.getKey().isAfter(yearMonth.atEndOfMonth()))
                    .collect(Collectors.groupingBy(
                            Map.Entry::getKey,
                            Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                    ));

            return eventDates.entrySet().stream()
                    .map(entry -> GetDateTimeRes.builder()
                            .date(entry.getKey())
                            .CategoryColor(entry.getValue())
                            .build())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Error when get list schedules", e);
            throw new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND);
        }
    }


    public GetDetailScheduleRes getScheduleDetailByEventId(String eventId) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var categories = categoryRepository.findAllByUserId(userId);
        var schedule = scheduleRepository.findByChildSchedulesIdAndUserId(eventId, userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        var childSchedules = schedule.getChildSchedules().stream()
                .filter(child -> child.getId().equals(eventId))
                .filter(child -> schedule.getExceptions().stream()
                        .noneMatch(scheduleException -> scheduleException.getExceptionDate()
                                .equals(child.getDate()))
                )
                .toList();

        if (childSchedules.isEmpty()) {
            throw new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND);
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
