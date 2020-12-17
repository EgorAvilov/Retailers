package by.itechart.retailers.converter;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.Product;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductConverter {
    public ProductDto entityToDto(Product product) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(product, ProductDto.class);
    }

    public Product dtoToEntity(ProductDto productDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(productDto, Product.class);
    }

    public List<ProductDto> entityToDto(List<Product> products) {
        return products.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
    }

    public List<Product> dtoToEntity(List<ProductDto> productDtos) {
        return productDtos.stream()
                       .map(this::dtoToEntity)
                       .collect(Collectors.toList());
    }
}
