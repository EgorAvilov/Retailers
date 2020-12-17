package by.itechart.retailers.converter;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.entity.InnerApplication;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class InnerApplicationConverter {
    public InnerApplicationDto entityToDto(InnerApplication innerApplication) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(innerApplication, InnerApplicationDto.class);
    }

    public InnerApplication dtoToEntity(InnerApplicationDto innerApplicationDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(innerApplicationDto, InnerApplication.class);
    }

    public List<InnerApplicationDto> entityToDto(List<InnerApplication> innerApplications) {
        return innerApplications.stream()
                                .map(this::entityToDto)
                                .collect(Collectors.toList());
    }

    public List<InnerApplication> dtoToEntity(List<InnerApplicationDto> innerApplicationDtos) {
        return innerApplicationDtos.stream()
                                   .map(this::dtoToEntity)
                                   .collect(Collectors.toList());
    }
}
