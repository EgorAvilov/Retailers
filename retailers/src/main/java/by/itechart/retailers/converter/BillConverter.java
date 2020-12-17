package by.itechart.retailers.converter;

import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.entity.Bill;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillConverter {
    public BillDto entityToDto(Bill bill) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(bill, BillDto.class);
    }

    public Bill dtoToEntity(BillDto billDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(billDto, Bill.class);
    }

    public List<BillDto> entityToDto(List<Bill> bills) {
        return bills.stream()
                    .map(this::entityToDto)
                    .collect(Collectors.toList());
    }

    public List<Bill> dtoToEntity(List<BillDto> billDtos) {
        return billDtos.stream()
                       .map(this::dtoToEntity)
                       .collect(Collectors.toList());
    }
}
