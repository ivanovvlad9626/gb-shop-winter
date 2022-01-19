package ru.gb.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.entity.Category;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
