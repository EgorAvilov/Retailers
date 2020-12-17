package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.StateDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StateService {
    StateDto findById(long stateID);

    Page<StateDto> findAll(Pageable pageable);

    StateDto create(StateDto stateDto);

    StateDto update(StateDto stateDto);
}
