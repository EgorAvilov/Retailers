package by.itechart.retailers.service.impl;

import by.itechart.retailers.converter.StateConverter;
import by.itechart.retailers.dto.StateDto;
import by.itechart.retailers.entity.State;
import by.itechart.retailers.repository.StateRepository;
import by.itechart.retailers.service.interfaces.StateService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    private final StateRepository stateRepository;
    private final StateConverter stateConverter;
    Logger logger = LoggerFactory.getLogger(StateServiceImpl.class);

    @Autowired
    public StateServiceImpl(StateRepository stateRepository, StateConverter stateConverter) {
        this.stateRepository = stateRepository;
        this.stateConverter = stateConverter;
    }

    @Override
    public StateDto findById(long stateId) {
        logger.info("Find state by id {}", stateId);
        State state = stateRepository.findById(stateId)
                                     .orElse(new State());
        return stateConverter.entityToDto(state);
    }

    @Override
    public Page<StateDto> findAll(Pageable pageable) {
        logger.info("Find all states");
        Page<State> statePage = stateRepository.findAll(pageable);
        List<StateDto> stateDtos = stateConverter.entityToDto(statePage.getContent());
        return new PageImpl<>(stateDtos, pageable, statePage.getTotalElements());
    }

    @Override
    public StateDto create(StateDto stateDto) {
        logger.info("Create state");
        State state = stateConverter.dtoToEntity(stateDto);
        State persistState = stateRepository.save(state);
        return stateConverter.entityToDto(persistState);
    }

    @Override
    public StateDto update(StateDto stateDto) {
        logger.info("Update state");
        State state = stateConverter.dtoToEntity(stateDto);
        State persistState = stateRepository.findById(state.getId())
                                            .orElse(new State());
        persistState.setName(state.getName());
        persistState.setStateTax(state.getStateTax());
        persistState = stateRepository.save(persistState);
        return stateConverter.entityToDto(persistState);
    }
}
