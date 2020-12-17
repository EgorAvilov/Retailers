package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.CategoryConverter;
import by.itechart.retailers.dto.CategoryDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.Category;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.CategoryRepository;
import by.itechart.retailers.service.interfaces.CategoryService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;
    private final UserService userService;
    Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               CategoryConverter categoryConverter,
                               UserService userService) {
        this.categoryRepository = categoryRepository;
        this.categoryConverter = categoryConverter;
        this.userService = userService;
    }

    @Override
    public CategoryDto findById(long categoryId) {
        logger.info("Find categoty by id {}", categoryId);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        Category persistCategory = categoryRepository.findByIdAndCustomer_Id(categoryId, customerId)
                                                     .orElse(new Category());
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public Page<CategoryDto> findAll(Pageable pageable) {
        logger.info("Find all categories");
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        Page<Category> categoryPage = categoryRepository.findAllByCustomer_Id(pageable, customerId);
        List<CategoryDto> categoryDtos = categoryConverter.entityToDto(categoryPage.getContent());
        return new PageImpl<>(categoryDtos, pageable, categoryPage.getTotalElements());
    }

    @Override
    public CategoryDto create(CategoryDto categoryDto) throws BusinessException {
        logger.info("Create category");
        UserDto userDto = userService.getCurrentUser();
        categoryDto.setCustomer(userDto.getCustomer());
        if (nameExists(categoryDto.getName())) {
            logger.error("Not unique name {}", categoryDto.getName());
            throw new BusinessException("Name should be unique");
        }
        Category category = categoryConverter.dtoToEntity(categoryDto);
        Category persistCategory = categoryRepository.save(category);
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto) throws BusinessException {
        logger.info("Update category");
        UserDto userDto = userService.getCurrentUser();
        categoryDto.setCustomer(userDto.getCustomer());
        Category category = categoryConverter.dtoToEntity(categoryDto);
        Category persistCategory = categoryRepository.findByIdAndCustomer_Id(categoryDto.getId(),
                userDto.getCustomer()
                       .getId())
                                                     .orElse(new Category());
        if (!category.getName()
                     .equals(persistCategory.getName())) {
            if (nameExists(categoryDto.getName())) {
                logger.error("Not unique name {}", categoryDto.getName());
                throw new BusinessException("Name should be unique");
            }
        }
        persistCategory.setName(category.getName());
        persistCategory.setCategoryTax(category.getCategoryTax());
        persistCategory = categoryRepository.save(persistCategory);
        return categoryConverter.entityToDto(persistCategory);
    }

    @Override
    public boolean nameExists(String name) {
        logger.info("Check for unique category name {}", name);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        return categoryRepository.findAllByNameIgnoreCaseAndCustomer_Id(name, customerId)
                                 .size() != 0;
    }
}
