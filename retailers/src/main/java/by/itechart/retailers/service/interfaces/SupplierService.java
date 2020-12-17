package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SupplierService {
    SupplierDto findById(long supplierId);

    Page<SupplierDto> findAll(Pageable pageable);

    SupplierDto create(SupplierDto supplierDto) throws BusinessException;

    SupplierDto update(SupplierDto supplierDto) throws BusinessException;

    List<SupplierDto> updateStatus(List<Long> supplierIdsList);

    boolean identifierExists(Integer identifier);
}
