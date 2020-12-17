package by.itechart.retailers.repository;

import by.itechart.retailers.entity.WriteOffActRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WriteOffActRecordRepository extends JpaRepository<WriteOffActRecord, Long> {
}
