package by.itechart.retailers.converter;

import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.entity.Category;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryConverter {
    public CategoryDto entityToDto(Category category) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(category, CategoryDto.class);
    }

    public Category dtoToEntity(CategoryDto categoryDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(categoryDto, Category.class);
    }

    public List<CategoryDto> entityToDto(List<Category> categories) {
        return categories.stream()
                         .map(this::entityToDto)
                         .collect(Collectors.toList());
    }

    public List<Category> dtoToEntity(List<CategoryDto> categoryDtos) {
        return categoryDtos.stream()
                           .map(this::dtoToEntity)
                           .collect(Collectors.toList());
    }
}
