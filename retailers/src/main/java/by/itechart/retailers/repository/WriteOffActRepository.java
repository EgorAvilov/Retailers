package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.WriteOffAct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WriteOffActRepository extends JpaRepository<WriteOffAct, Long> {
   List<WriteOffAct> findAllByWriteOffActNumberAndCustomer_Id(Integer writeOffActNumber, Long customerId);

    Page<WriteOffAct> findAllByLocationIn(Pageable pageable, List<Location> locations);

    Page<WriteOffAct> findAllByLocation_Id(Pageable pageable, Long locationId);

    Optional<WriteOffAct> findByIdAndCustomer_Id(Long id, Long customerId);
}
