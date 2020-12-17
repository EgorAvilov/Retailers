package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    CategoryDto findById(long categoryId);

    Page<CategoryDto> findAll(Pageable pageable);

    CategoryDto create(CategoryDto categoryDto) throws BusinessException;

    CategoryDto update(CategoryDto categoryDto) throws BusinessException;

    boolean nameExists(String name);
}
