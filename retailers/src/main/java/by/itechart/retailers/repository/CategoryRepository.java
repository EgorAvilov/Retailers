package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByCustomer_Id(Pageable pageable, Long customerId);

    Optional<Category> findByNameIgnoreCaseAndCustomer_Id(String name, Long customerId);

    Optional<Category> findByIdAndCustomer_Id(Long id, Long customerId);

    List<Category> findAllByNameIgnoreCaseAndCustomer_Id(String name, Long customerId);
}
