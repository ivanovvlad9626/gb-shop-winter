package ru.gb.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.gb.service.CategoryService;
import ru.gb.web.dto.CategoryDto;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping
    public List<CategoryDto> getCategoryList() {
        return categoryService.findAll();
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<?> getCategory(@PathVariable("categoryId") Long id) {
        CategoryDto category;
        if (id != null) {
            category = categoryService.findById(id);
            if (category != null) {
                return new ResponseEntity<>(category, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody CategoryDto categoryDto) {
        CategoryDto savedCategory = categoryService.save(categoryDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(URI.create("/api/v1/category/" + savedCategory.getId()));
        return new ResponseEntity<>(httpHeaders, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("categoryId") Long id, @Validated @RequestBody CategoryDto categoryDto) {
        categoryDto.setId(id);
        categoryService.save(categoryDto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("categoryId") Long id) {
        categoryService.deleteById(id);
    }
}
