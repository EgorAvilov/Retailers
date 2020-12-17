package by.itechart.retailers.repository;

import by.itechart.retailers.entity.DeletedStatus;
import by.itechart.retailers.entity.Location;
import by.itechart.retailers.entity.LocationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Page<Location> findAllByCustomer_IdAndStatus(Pageable pageable, Long customerId, DeletedStatus status);

    Page<Location> findAllByCustomer_IdAndLocationTypeAndStatus(Pageable pageable, Long customerId, LocationType locationType, DeletedStatus status);

    List<Location> findAllByIdentifierIgnoreCaseAndCustomer_Id(String identifier, Long customerId);

    List<Location> findAllByCustomer_Id(Long customerId);

    Optional<Location> findByIdAndCustomer_IdAndStatus(Long id, Long customerId, DeletedStatus status);

    List<Location> findAllByIdInAndCustomer_Id(List<Long> ids, Long customerId);
}
