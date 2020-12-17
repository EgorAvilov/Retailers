package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.InnerApplicationDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InnerApplicationService {
    InnerApplicationDto findById(long innerApplicationId);

    Page<InnerApplicationDto> findAll(Pageable pageable);

    InnerApplicationDto create(InnerApplicationDto innerApplicationDto) throws BusinessException;

    InnerApplicationDto update(InnerApplicationDto innerApplicationDto) ;

    InnerApplicationDto updateStatus(Long innerApplicationId) throws BusinessException;

    boolean applicationNumberExists(Integer applicationNumber);
}
