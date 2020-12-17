package by.itechart.retailers.converter;

import by.itechart.retailers.dto.SupplierDto;
import by.itechart.retailers.entity.Supplier;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierConverter {
    public SupplierDto entityToDto(Supplier supplier) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(supplier, SupplierDto.class);
    }

    public Supplier dtoToEntity(SupplierDto supplierDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(supplierDto, Supplier.class);
    }

    public List<SupplierDto> entityToDto(List<Supplier> suppliers) {
        return suppliers.stream()
                        .map(this::entityToDto)
                        .collect(Collectors.toList());
    }

    public List<Supplier> dtoToEntity(List<SupplierDto> supplierDtos) {
        return supplierDtos.stream()
                           .map(this::dtoToEntity)
                           .collect(Collectors.toList());
    }
}
