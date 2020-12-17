package by.itechart.retailers.converter;

import by.itechart.retailers.dto.ApplicationRecordDto;
import by.itechart.retailers.entity.ApplicationRecord;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApplicationRecordConverter {
    public ApplicationRecordDto entityToDto(ApplicationRecord applicationRecord) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(applicationRecord, ApplicationRecordDto.class);
    }

    public ApplicationRecord dtoToEntity(ApplicationRecordDto applicationRecordDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(applicationRecordDto, ApplicationRecord.class);
    }

    public List<ApplicationRecordDto> entityToDto(List<ApplicationRecord> applicationRecords) {
        return applicationRecords.stream()
                                     .map(this::entityToDto)
                                     .collect(Collectors.toList());
    }

    public List<ApplicationRecord> dtoToEntity(List<ApplicationRecordDto> applicationRecordDtos) {
        return applicationRecordDtos.stream()
                                        .map(this::dtoToEntity)
                                        .collect(Collectors.toList());
    }
}
