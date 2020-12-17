package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.WriteOffActDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WriteOffActService {
    WriteOffActDto findById(long writeOffActId);

    Page<WriteOffActDto> findAllByCustomer(Pageable pageable);

    Page<WriteOffActDto> findAllByLocation(Pageable pageable);

    WriteOffActDto create(WriteOffActDto writeOffActDto) throws BusinessException;

    boolean writeOffActNumberExists(Integer writeOffActNumber);
}
