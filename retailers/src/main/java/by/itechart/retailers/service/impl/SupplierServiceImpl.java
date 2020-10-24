package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.SupplierConverter;
import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.entity.Supplier;
import by.itechart.retailers.repository.SupplierRepository;
import by.itechart.retailers.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository;
    private SupplierConverter supplierConverter;

    @Autowired
    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierConverter supplierConverter) {
        this.supplierRepository = supplierRepository;
        this.supplierConverter= supplierConverter;
    }

    @Override
    public SupplierDto findById(long supplierId) {
        Supplier supplier = supplierRepository.findById(supplierId).orElse(new Supplier());

        return supplierConverter.entityToDto(supplier);
    }

    @Override
    public List<SupplierDto> findAll() {
        List<Supplier> supplierList = supplierRepository.findAll();

        return supplierConverter.entityToDto(supplierList);
    }

    @Override
    public SupplierDto create(SupplierDto supplierDto) {
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        Supplier persistSupplier = supplierRepository.save(supplier);

        return supplierConverter.entityToDto(persistSupplier);
    }

    @Override
    public SupplierDto update(SupplierDto supplierDto) {
        Supplier supplier = supplierConverter.dtoToEntity(supplierDto);
        Supplier persistSupplier = supplierRepository.findById(supplier.getId()).orElse(new Supplier());

        persistSupplier.setFullName(supplier.getFullName());
        persistSupplier.setIdentifier(supplier.getIdentifier());
        persistSupplier.setWareHouseList(supplier.getWareHouseList());

        return supplierConverter.entityToDto(persistSupplier);
    }
}