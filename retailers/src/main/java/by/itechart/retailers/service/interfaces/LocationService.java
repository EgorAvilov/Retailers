package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.LocationDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LocationService {
    LocationDto findById(long locationId);

    Page<LocationDto> findAll(Pageable pageable);

    Page<LocationDto> findAllWarehouses(Pageable pageable);

    Page<LocationDto> findAllShops(Pageable pageable);

    LocationDto create(LocationDto locationDto) throws BusinessException;

    LocationDto update(LocationDto locationDto) throws BusinessException;

    List<LocationDto> delete(List<Long> locationIds);

    boolean identifierExists(String identifier);
}
