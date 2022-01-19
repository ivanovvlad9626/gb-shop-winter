package ru.gb.web.dto.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gb.entity.Category;
import ru.gb.web.dto.CategoryDto;

@Mapper
public interface CategoryMapper {
    Category toCategory(CategoryDto categoryDto);
    CategoryDto toCategoryDto(Category category);
}
