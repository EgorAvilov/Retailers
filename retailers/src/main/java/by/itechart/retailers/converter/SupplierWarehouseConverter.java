package by.itechart.retailers.converter;

import by.itechart.retailers.dto.SupplierWarehouseDto;
import by.itechart.retailers.entity.SupplierWarehouse;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierWarehouseConverter {
    public SupplierWarehouseDto entityToDto(SupplierWarehouse warehouse) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(warehouse, SupplierWarehouseDto.class);
    }

    public SupplierWarehouse dtoToEntity(SupplierWarehouseDto warehouseDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(warehouseDto, SupplierWarehouse.class);
    }

    public List<SupplierWarehouseDto> entityToDto(List<SupplierWarehouse> warehouse) {
        return warehouse.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<SupplierWarehouse> dtoToEntity(List<SupplierWarehouseDto> warehouseDto) {
        return warehouseDto.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
