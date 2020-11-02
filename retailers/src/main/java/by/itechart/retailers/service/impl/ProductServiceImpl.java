package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.ProductConverter;
import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.Product;
import by.itechart.retailers.repository.ProductRepository;
import by.itechart.retailers.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;
    private ProductConverter productConverter;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductConverter productConverter) {
        this.productRepository = productRepository;
        this.productConverter = productConverter;
    }

    @Override
    public ProductDto findById(long productId) {
        Product product = productRepository.findById(productId).orElse(new Product());

        return productConverter.entityToDto(product);
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> productList = productRepository.findAll();

        return productConverter.entityToDto(productList);
    }

    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productRepository.save(product);

        return productConverter.entityToDto(persistProduct);
    }

    @Override
    public ProductDto update(ProductDto productDto) {
        Product product = productConverter.dtoToEntity(productDto);
        Product persistProduct = productRepository.findById(product.getId()).orElse(new Product());

        persistProduct.setCategory(product.getCategory());
        persistProduct.setLabel(product.getLabel());
        persistProduct.setUpc(product.getUpc());
        persistProduct.setVolume(product.getVolume());

        return productConverter.entityToDto(persistProduct);
    }
}