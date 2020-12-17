package by.itechart.retailers.repository;

import by.itechart.retailers.entity.ApplicationRecord;
import by.itechart.retailers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRecordRepository extends JpaRepository<ApplicationRecord, Long> {
    List<ApplicationRecord> findAllByProduct(Product product);

    ApplicationRecord findApplicationRecordByProduct(Product product);
}
