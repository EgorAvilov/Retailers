package by.itechart.retailers.converter;

import by.itechart.retailers.dto.SupplierApplicationDto;
import by.itechart.retailers.entity.SupplierApplication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SupplierApplicationConverter {
    public SupplierApplicationDto entityToDto(SupplierApplication supplierApplication) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(supplierApplication, SupplierApplicationDto.class);
    }

    public SupplierApplication dtoToEntity(SupplierApplicationDto supplierApplicationDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(supplierApplicationDto, SupplierApplication.class);
    }

    public List<SupplierApplicationDto> entityToDto(List<SupplierApplication> suppliers) {
        return suppliers.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    public List<SupplierApplication> dtoToEntity(List<SupplierApplicationDto> supplierDtos) {
        return supplierDtos.stream()
                .map(this::dtoToEntity)
                .collect(Collectors.toList());
    }
}
