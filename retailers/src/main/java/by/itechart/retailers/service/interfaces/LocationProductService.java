package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationProductDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationProductService {
    LocationProductDto findById(long locationProductId);

    Page<LocationProductDto> findAll(Pageable pageable);

    LocationProductDto create(LocationProductDto locationProductDto);

    LocationProductDto update(LocationProductDto locationProductDto);
}
