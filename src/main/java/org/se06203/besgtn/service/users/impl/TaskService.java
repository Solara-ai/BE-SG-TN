package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.tasks.CreateTaskRequest;
import org.se06203.besgtn.dto.response.tasks.GetTaskDetail;
import org.se06203.besgtn.dto.response.tasks.GetTaskRes;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.dto.response.tasks.TaskItem;
import org.se06203.besgtn.persistence.entity.Categories;
import org.se06203.besgtn.persistence.entity.Tasks;
import org.se06203.besgtn.persistence.repository.*;
import org.se06203.besgtn.utils.Constants;
import org.se06203.besgtn.utils.mapper.ChildScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final PlanRepository planRepository;
    private final TaskManagementRepository taskManagementRepository;
    private final ScheduleRepository scheduleRepository;
    private final ChildScheduleMapper childScheduleMapper;
    private final CategoryRepository categoryRepository;

    public GetTotalTaskAndPlanResponse getTotalTaskAndPlansByStatus(String date) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();
        var categories = categoryRepository.findAll();

        long count = taskRepository.countByStatusNot(Constants.TypeTask.DONE);
        long count2 = planRepository.countByStatus(Constants.Status.INCOMPLETE);

        var schedule = scheduleRepository.findById(userId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.SCHEDULE_NOT_FOUND));

        var item = schedule.getChildSchedules().stream()
                .filter(child -> date.contains(child.getDate()))
                .map(child -> {
                    var color = categories.stream()
                            .filter(category -> category.getId().equals(schedule.getCategoryId()))
                            .map(Categories::getColor)
                            .findFirst()
                            .orElse(null);

                    var getListScheduleRes = childScheduleMapper.mapSchedulesToGetListScheduleRes(child);
                    getListScheduleRes.setScheduleId(schedule.getId());
                    getListScheduleRes.setDescription(schedule.getDescription());
                    getListScheduleRes.setEventName(schedule.getName());
                    getListScheduleRes.setCategoryColor(color);
                    return getListScheduleRes;
                })
                .distinct()
                .findFirst()
                .orElse(null);

        return GetTotalTaskAndPlanResponse.builder()
                .totalTasks(String.valueOf(count))
                .totalPlans(String.valueOf(count2))
                .schedule(item)
                .build();
    }

    @Transactional
    public void createTask( CreateTaskRequest request) {
        var epic = taskManagementRepository.findActiveEpicById(request.getEpicId())
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        var tasks = Tasks.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .epicId(epic.getId())
                .build();
        taskRepository.save(tasks);
    }

    public GetTaskRes getAllTaskByEpic(String id) {
        var epic = taskManagementRepository.findActiveEpicById(id)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        var item = taskRepository.findAllByEpicId(epic.getId()).stream()
                .map(task -> TaskItem.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .status(task.getStatus())
                        .build())
                .toList();

        var response = GetTaskRes.builder()
                .epicName(epic.getNameEpic())
                .epicId(epic.getId())
                .description(epic.getDescription())
                .build();
        response.setTasks(item);

        return response;
    }

    public GetTaskDetail getTaskDetail(String epicId, String taskId) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.TASK_NOT_FOUND));

        var epic = taskManagementRepository.findActiveEpicById(epicId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        return GetTaskDetail.builder()
                .id(task.getId())
                .epicId(epic.getId())
                .epicName(epic.getNameEpic())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .build();
    }

    public void updateTask(String epicId, String taskId, CreateTaskRequest request) {
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.TASK_NOT_FOUND));

        var epic = taskManagementRepository.findActiveEpicById(epicId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        task.setTitle(request.getTitle());
        task.setEpicId(epic.getId());
        task.setDescription(request.getDescription());
        taskRepository.save(task);
    }

    public void updateTaskStatus(String epicId, String taskId, Constants.TypeTask status) {
        taskManagementRepository.findActiveEpicById(epicId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));
        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.TASK_NOT_FOUND));

        task.setStatus(status);
        taskRepository.save(task);
    }

    public void deleteTask(String epicId, String taskId) {
        taskManagementRepository.findActiveEpicById(epicId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.EPIC_NOT_FOUND));

        var task = taskRepository.findById(taskId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.TASK_NOT_FOUND));

        taskRepository.delete(task);
    }
}
