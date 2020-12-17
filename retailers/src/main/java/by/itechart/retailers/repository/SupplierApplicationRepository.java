package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.entity.SupplierApplication;
import by.itechart.retailers.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SupplierApplicationRepository extends JpaRepository<SupplierApplication, Long> {

    List<SupplierApplication> findAllByRecordsListIn(List<ApplicationRecord> applicationRecords);

    List<SupplierApplication> findAllByApplicationNumberAndCreator(Integer applicationNumber, User creator);

    Page<SupplierApplication> findAllByDestinationLocation_Id(Pageable pageable, Long locationId);

    Optional<SupplierApplication> findByIdAndDestinationLocation_Id(Long id, Long destinationLocationId);
}
