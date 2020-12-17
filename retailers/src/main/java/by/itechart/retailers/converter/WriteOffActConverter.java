package by.itechart.retailers.converter;

import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.entity.WriteOffAct;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WriteOffActConverter {
    public WriteOffActDto entityToDto(WriteOffAct writeOffAct) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffAct, WriteOffActDto.class);
    }

    public WriteOffAct dtoToEntity(WriteOffActDto writeOffActDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(writeOffActDto, WriteOffAct.class);
    }

    public List<WriteOffActDto> entityToDto(List<WriteOffAct> writeOffActs) {
        return writeOffActs.stream()
                           .map(this::entityToDto)
                           .collect(Collectors.toList());
    }

    public List<WriteOffAct> dtoToEntity(List<WriteOffActDto> writeOffActDtos) {
        return writeOffActDtos.stream()
                              .map(this::dtoToEntity)
                              .collect(Collectors.toList());
    }
}
