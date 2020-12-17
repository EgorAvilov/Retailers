package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierApplicationService {
    SupplierApplicationDto findById(long supplierApplicationId);

    Page<SupplierApplicationDto> findAll(Pageable pageable);

    SupplierApplicationDto create(SupplierApplicationDto supplierApplicationDto) throws BusinessException;

    SupplierApplicationDto update(SupplierApplicationDto supplierApplicationDto);

    SupplierApplicationDto updateStatus(Long supplierApplicationId) throws BusinessException;

    boolean applicationNumberExists(Integer applicationNumber);
}
