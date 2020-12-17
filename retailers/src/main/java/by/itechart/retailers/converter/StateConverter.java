package by.itechart.retailers.converter;

import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.entity.State;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class StateConverter {
    public StateDto entityToDto(State state) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(state, StateDto.class);
    }

    public State dtoToEntity(StateDto stateDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(stateDto, State.class);
    }

    public List<StateDto> entityToDto(List<State> states) {
        return states.stream()
                     .map(this::entityToDto)
                     .collect(Collectors.toList());
    }

    public List<State> dtoToEntity(List<StateDto> stateDtos) {
        return stateDtos.stream()
                        .map(this::dtoToEntity)
                        .collect(Collectors.toList());
    }
}
