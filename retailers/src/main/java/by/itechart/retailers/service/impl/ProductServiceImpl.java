package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ProductConverter;
import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.dto.UserDto;
import by.itechart.retailers.entity.*;
import by.itechart.retailers.exceptions.BusinessException;
import by.itechart.retailers.repository.*;
import by.itechart.retailers.service.interfaces.ProductService;
import by.itechart.retailers.service.interfaces.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductConverter productConverter;
    private final ApplicationRecordRepository applicationRecordRepository;
    private final LocationProductRepository locationProductRepository;
    private final InnerApplicationRepository innerApplicationRepository;
    private final SupplierApplicationRepository supplierApplicationRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductConverter productConverter,
                              ApplicationRecordRepository applicationRecordRepository,
                              LocationProductRepository locationProductRepository,
                              InnerApplicationRepository innerApplicationRepository,
                              SupplierApplicationRepository supplierApplicationRepository,
                              UserService userService,
                              CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
        this.applicationRecordRepository = applicationRecordRepository;
        this.locationProductRepository = locationProductRepository;
        this.innerApplicationRepository = innerApplicationRepository;
        this.supplierApplicationRepository = supplierApplicationRepository;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
    }


    @Override
    public ProductDto findById(long productId) {
        logger.info("Find product by id {}", productId);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        Product product = productRepository.findByIdAndCustomer_IdAndStatus(productId, customerId, DeletedStatus.ACTIVE)
                                           .orElse(new Product());
        return productConverter.entityToDto(product);
    }

    @Override
    public Page<ProductDto> findAll(Pageable pageable) {
        logger.info("Find all products");
        UserDto userDto = userService.getCurrentUser();
        Page<Product> productPage = productRepository.findAllByCustomer_IdAndStatus(pageable, userDto.getCustomer()
                                                                                                     .getId(), DeletedStatus.ACTIVE);
        List<ProductDto> productDtos = productConverter.entityToDto(productPage.getContent());
        return new PageImpl<>(productDtos, pageable, productPage.getTotalElements());
    }

    @Override
    public ProductDto create(ProductDto productDto) throws BusinessException {
        logger.info("Create product");
        UserDto userDto = userService.getCurrentUser();
        productDto.setCustomer(userDto.getCustomer());
        Product product = productConverter.dtoToEntity(productDto);
        if (upcExists(product.getUpc())) {
            logger.error("Not unique upc {}", product.getUpc());
            throw new BusinessException("Upc should be unique");
        }
        Category category = findCategory(product);
        product.setCategory(category);
        Product persistProduct = productRepository.save(product);
        return productConverter.entityToDto(persistProduct);
    }

    @Override
    public ProductDto update(ProductDto productDto) throws BusinessException {
        logger.info("Update product");
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        productDto.setCustomer(userDto.getCustomer());
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productRepository.findByIdAndCustomer_IdAndStatus(product.getId(), customerId, DeletedStatus.ACTIVE)
                                                  .orElse(new Product());
        if (!product.getUpc()
                    .equals(persistProduct.getUpc())) {
            if (upcExists(product.getUpc())) {
                logger.error("Not unique upc {}", product.getUpc());
                throw new BusinessException("Upc should be unique");
            }
        }
        Category category = findCategory(product);
        persistProduct.setCategory(category);
        persistProduct.setLabel(product.getLabel());
        persistProduct.setUpc(product.getUpc());
        persistProduct.setVolume(product.getVolume());
        persistProduct.setCustomer(product.getCustomer());
        persistProduct = productRepository.save(persistProduct);
        return productConverter.entityToDto(persistProduct);
    }

    private Category findCategory(Product product) {
        logger.info("Find category");
        Category category = categoryRepository.findByNameIgnoreCaseAndCustomer_Id(
                product.getCategory()
                       .getName(),
                product.getCustomer()
                       .getId())
                                              .orElse(new Category());
        if (category.getId() == null) {
            category = new Category();
            category.setCustomer(product.getCustomer());
            category.setName(product.getCategory()
                                    .getName());
            category.setCategoryTax(BigDecimal.valueOf(0));
        }
        return category;
    }

    @Override
    public List<ProductDto> delete(List<Long> productIds) {
        logger.info("Delete product");
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        List<Product> products = productRepository.findAllByIdInAndCustomer_Id(productIds, customerId);
        List<Product> undeletedProducts = new ArrayList<>(products);
        for (Product product : products) {
            List<ApplicationRecord> applicationRecords = applicationRecordRepository.findAllByProduct(product);
            List<LocationProduct> locationProducts = locationProductRepository.findAllByProduct(product);
            List<SupplierApplication> supplierApplications = supplierApplicationRepository.findAllByRecordsListIn(applicationRecords);
            List<InnerApplication> innerApplications = innerApplicationRepository.findAllByRecordsListIn(applicationRecords);
            if (checkInLocationProducts(locationProducts)
                    || checkInSupplierApplications(supplierApplications)
                    || checkInInnerApplications(innerApplications)) {
                continue;
            }
            product.setStatus(DeletedStatus.DELETED);
            productRepository.save(product);
            undeletedProducts.remove(product);
        }
        return productConverter.entityToDto(undeletedProducts);
    }

    private boolean checkInLocationProducts(List<LocationProduct> locationProducts) {
        logger.info("Check product in location products");
        return locationProducts.stream()
                               .anyMatch(locationProduct -> (locationProduct.getAmount() != 0));
    }

    private boolean checkInSupplierApplications(List<SupplierApplication> supplierApplications) {
        logger.info("Check product in supplier applications");
        return supplierApplications.stream()
                                   .anyMatch(supplierApplication -> (supplierApplication.getApplicationStatus()
                                                                                        .equals(ApplicationStatus.OPEN)));
    }

    private boolean checkInInnerApplications(List<InnerApplication> innerApplications) {
        logger.info("Check product in inner applications");
        return innerApplications.stream()
                                .anyMatch(supplierApplication -> (supplierApplication.getApplicationStatus()
                                                                                     .equals(ApplicationStatus.OPEN)));
    }

    @Override
    public boolean upcExists(Long upc) {
        logger.info("Check for existing upc {}", upc);
        UserDto userDto = userService.getCurrentUser();
        Long customerId = userDto.getCustomer()
                                 .getId();
        return productRepository.findAllByUpcAndCustomer_IdAndStatus(upc, customerId, DeletedStatus.ACTIVE)
                                .size() != 0;
    }
}


