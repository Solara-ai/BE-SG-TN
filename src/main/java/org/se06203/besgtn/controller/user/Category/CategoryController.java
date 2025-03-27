package org.se06203.besgtn.controller.user.Category;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.config.response.ResponseFactory;
import org.se06203.besgtn.dto.request.InsertCategoryReq;
import org.se06203.besgtn.dto.response.GetCategory;
import org.se06203.besgtn.service.users.impl.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApi{
    private final CategoryService categoryService;
    private final ResponseFactory responseFactory;

    @Override
    public ResponseEntity<BaseResponse> createCategory(InsertCategoryReq req) {
        categoryService.createCategory(req);
        return responseFactory.success(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<BaseDataResponse<List<GetCategory>>> getListCategory() {
        return responseFactory.success(HttpStatus.OK, categoryService.getListCategory());
    }

    @Override
    public ResponseEntity<BaseResponse> updateCategory(String categoryId, InsertCategoryReq req) {
        categoryService.updateCategory(categoryId, req);
        return responseFactory.success(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<BaseDataResponse<GetCategory>> getCategoryDetailByCategoryId(String categoryId) {
        return responseFactory.success(HttpStatus.OK, categoryService.getCategoryDetailByCategoryId(categoryId));
    }

    @Override
    public ResponseEntity<BaseResponse> deleteCategoryByCategoryId(String categoryId) {
        categoryService.deleteCategoryByCategoryId(categoryId);
        return responseFactory.success(HttpStatus.NO_CONTENT);
    }
}
