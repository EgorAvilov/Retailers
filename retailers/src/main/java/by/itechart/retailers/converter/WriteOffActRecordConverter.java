package by.itechart.retailers.converter;

import by.itechart.retailers.dto.WriteOffActRecordDto;
import by.itechart.retailers.entity.WriteOffActRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriteOffActRecordConverter {
    public WriteOffActRecordDto entityToDto(WriteOffActRecord writeOffActRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffActRecord, WriteOffActRecordDto.class);
    }

    public WriteOffActRecord dtoToEntity(WriteOffActRecordDto writeOffActRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffActRecordDto, WriteOffActRecord.class);
    }

    public List<WriteOffActRecordDto> entityToDto(List<WriteOffActRecord> writeOffActRecords) {
        return writeOffActRecords.stream()
                                     .map(this::entityToDto)
                                     .collect(Collectors.toList());
    }

    public List<WriteOffActRecord> dtoToEntity(List<WriteOffActRecordDto> writeOffActRecordDtos) {
        return writeOffActRecordDtos.stream()
                                        .map(this::dtoToEntity)
                                        .collect(Collectors.toList());
    }
}
