package by.itechart.retailers.repository;

import by.itechart.retailers.entity.BillRecord;
import by.itechart.retailers.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillRecordRepository extends JpaRepository<BillRecord, Long> {
    BillRecord findBillProductRecordByProduct(Product product);
}
