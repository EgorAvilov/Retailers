package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.SupplierWarehouseDto;

import java.util.List;

public interface SupplierWarehouseService {

    List<SupplierWarehouseDto> findAll();

    SupplierWarehouseDto findById(Long supplierWarehouseId);

    boolean identifierExists(String identifier);
}
