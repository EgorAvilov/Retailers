package by.itechart.retailers.converter;

import by.itechart.retailers.dto.BillRecordDto;
import by.itechart.retailers.entity.BillRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BillRecordConverter {
    public BillRecordDto entityToDto(BillRecord billRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(billRecord, BillRecordDto.class);
    }

    public BillRecord dtoToEntity(BillRecordDto billIRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(billIRecordDto, BillRecord.class);
    }

    public List<BillRecordDto> entityToDto(List<BillRecord> billRecords) {
        return billRecords.stream()
                              .map(this::entityToDto)
                              .collect(Collectors.toList());
    }

    public List<BillRecord> dtoToEntity(List<BillRecordDto> billIRecordDtos) {
        return billIRecordDtos.stream()
                                 .map(this::dtoToEntity)
                                 .collect(Collectors.toList());
    }
}
