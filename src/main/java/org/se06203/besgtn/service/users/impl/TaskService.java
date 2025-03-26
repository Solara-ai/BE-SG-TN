package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.dto.request.tasks.CreateTaskRequest;
import org.se06203.besgtn.dto.response.tasks.GetTaskDetail;
import org.se06203.besgtn.dto.response.tasks.GetTaskRes;
import org.se06203.besgtn.dto.response.tasks.GetTotalTaskAndPlanResponse;
import org.se06203.besgtn.dto.response.tasks.TaskItem;
import org.se06203.besgtn.persistence.entity.Tasks;
import org.se06203.besgtn.persistence.repository.PlanRepository;
import org.se06203.besgtn.persistence.repository.TaskManagementRepository;
import org.se06203.besgtn.persistence.repository.TaskRepository;
import org.se06203.besgtn.utils.Constants;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final PlanRepository planRepository;
    private final TaskManagementRepository taskManagementRepository;

    public GetTotalTaskAndPlanResponse getTotalTaskAndPlansByStatus() {
        long count = taskRepository.countByStatusNot(Constants.TypeTask.DONE);
        long count2 = planRepository.countByStatus(Constants.Status.INCOMPLETE);

        return GetTotalTaskAndPlanResponse.builder()
                .totalTasks(String.valueOf(count))
                .totalPlans(String.valueOf(count2))
                .build();

    }

    @Transactional
    public void createTask(String id, CreateTaskRequest request) {
        var epic = taskManagementRepository.findActiveEpicById(id)
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
