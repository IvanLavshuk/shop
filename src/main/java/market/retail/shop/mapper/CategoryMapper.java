package market.retail.shop.mapper;

import market.retail.shop.dto.CategoryDto;
import market.retail.shop.dto.CreateCategoryDto;
import market.retail.shop.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDto toDto(Category category) {
        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setId(category.getId());
        categoryDto.setName(category.getName());
        return categoryDto;
    }

    public Category toEntity(CreateCategoryDto createCategoryDto) {
        Category category = new Category();
        category.setName(createCategoryDto.getName());
        return category;
    }
}
