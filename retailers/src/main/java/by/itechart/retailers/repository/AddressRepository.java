package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Address;
import by.itechart.retailers.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
