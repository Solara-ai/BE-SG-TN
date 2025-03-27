package org.se06203.besgtn.service.users.impl;

import lombok.RequiredArgsConstructor;
import org.se06203.besgtn.config.exception.BaseRuntimeException;
import org.se06203.besgtn.config.exception.ErrorCodeMsg;
import org.se06203.besgtn.config.security.SecurityUtils;
import org.se06203.besgtn.dto.request.InsertCategoryReq;
import org.se06203.besgtn.dto.response.GetCategory;
import org.se06203.besgtn.persistence.entity.Categories;
import org.se06203.besgtn.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {


    private final CategoryRepository categoryRepository;

    public void createCategory(InsertCategoryReq req) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        categoryRepository.save(Categories.builder()
                .userId(userId)
                .name(req.getCategoryName())
                .color(req.getCategoryColor())
                .build());
    }

    public List<GetCategory> getListCategory() {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        return categoryRepository.findAllByUserId(userId).stream()
                .map(category -> GetCategory.builder()
                        .categoryId(category.getId())
                        .categoryName(category.getName())
                        .categoryColor(category.getColor())
                        .build())
                .toList();
    }

    public void updateCategory(String categoryId, InsertCategoryReq req) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND));

        if (!category.getUserId().equals(userId)) {
            throw new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND);
        }

        category.setName(req.getCategoryName());
        category.setColor(req.getCategoryColor());

        categoryRepository.save(category);
    }

    public GetCategory getCategoryDetailByCategoryId(String categoryId) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND));

        if (!category.getUserId().equals(userId)) {
            throw new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND);
        }

        return GetCategory.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .categoryColor(category.getColor())
                .build();
    }

    public void deleteCategoryByCategoryId(String categoryId) {
        var userId = SecurityUtils.getAuthenticatedUser().getId();

        var category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND));

        if (!category.getUserId().equals(userId)) {
            throw new BaseRuntimeException(ErrorCodeMsg.CATEGORY_NOT_FOUND);
        }

        categoryRepository.deleteById(categoryId);
    }
}
