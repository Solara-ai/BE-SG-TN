package org.se06203.besgtn.controller.user.epic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.epic.CreateEpicRequest;
import org.se06203.besgtn.dto.response.tasks.GetAllTaskResponse;
import org.se06203.besgtn.service.users.EpicService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EpicController implements EpicApi {

    private final EpicService epicService;

    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseResponse> createEpic(CreateEpicRequest request) {
        return responseFactory.success(HttpStatus.OK,epicService.createEpic(request));
    }

    @Override
    public ResponseEntity<BaseDataResponse<List<GetAllTaskResponse>>> getEpicsByType(String type) {
        return responseFactory.success(HttpStatus.OK,epicService.getALlEpicByType(type));
    }

    @Override
    public ResponseEntity<BaseResponse> updateEpic(CreateEpicRequest request) {
        return null;
    }

    @Override
    public ResponseEntity<BaseResponse> deleteEpic() {
        return null;
    }
}
