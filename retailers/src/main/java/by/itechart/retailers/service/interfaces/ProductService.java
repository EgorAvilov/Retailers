package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.ProductDto;
import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductDto findById(long productId);

    Page<ProductDto> findAll(Pageable pageable);

    ProductDto create(ProductDto productDto) throws BusinessException;

    ProductDto update(ProductDto productDto) throws BusinessException;

    List<ProductDto> delete(List<Long> productIds);

    boolean upcExists(Long upc);
}
