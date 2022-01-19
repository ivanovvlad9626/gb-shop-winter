package ru.gb.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gb.dao.CategoryDao;
import ru.gb.entity.Category;
import ru.gb.web.dto.CategoryDto;
import ru.gb.web.dto.mapper.CategoryMapper;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryDto save(final CategoryDto CategoryDto) {
        Category category = categoryMapper.toCategory(CategoryDto);
        if (category.getId() != null) {
            categoryDao.findById(category.getId()).ifPresent(
                    (p) -> category.setVersion(p.getVersion())
            );
        }
        return categoryMapper.toCategoryDto(categoryDao.save(category));
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return categoryMapper.toCategoryDto(categoryDao.findById(id).orElse(null));
    }


    public List<CategoryDto> findAll() {
        return categoryDao.findAll().stream().map(categoryMapper::toCategoryDto).collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        try {
            categoryDao.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
        }
    }
}
