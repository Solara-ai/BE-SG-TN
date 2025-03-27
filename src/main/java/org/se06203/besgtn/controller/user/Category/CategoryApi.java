package org.se06203.besgtn.controller.user.Category;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.se06203.besgtn.config.response.BaseDataResponse;
import org.se06203.besgtn.config.response.BaseResponse;
import org.se06203.besgtn.dto.request.InsertCategoryReq;
import org.se06203.besgtn.dto.response.GetCategory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/category")
@SecurityRequirement(name = "Bearer Authentication")
@Tag(name = "Category", description = "Api for user Category")
public interface CategoryApi {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(tags = "Category", summary = "Create Category.")
    ResponseEntity<BaseResponse> createCategory(InsertCategoryReq req);

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Category", summary = "Get List Category.")
    ResponseEntity<BaseDataResponse<List<GetCategory>>> getListCategory();

    @PutMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Category", summary = "Update Category.")
    ResponseEntity<BaseResponse> updateCategory(@PathVariable String categoryId, InsertCategoryReq req);

    @GetMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(tags = "Category", summary = "Get Category Detail By Category Id.")
    ResponseEntity<BaseDataResponse<GetCategory>> getCategoryDetailByCategoryId(@PathVariable String categoryId);

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(tags = "Category", summary = "Delete Category By Category Id.")
    ResponseEntity<BaseResponse> deleteCategoryByCategoryId(@PathVariable String categoryId);
}
