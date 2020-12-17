package by.itechart.retailers.service.interfaces;

import by.itechart.retailers.dto.BillDto;
import by.itechart.retailers.exceptions.BusinessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BillService {
    BillDto findById(long billId);

    Page<BillDto> findAllByCustomer(Pageable pageable);

    Page<BillDto> findAllByLocation(Pageable pageable);

    BillDto create(BillDto billDto) throws BusinessException;

    boolean billNumberExists(Integer billNumber);
}
