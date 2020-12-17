package by.itechart.retailers.repository;

import by.itechart.retailers.entity.LocationProduct;
import by.itechart.retailers.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationProductRepository extends JpaRepository<LocationProduct, Long> {

    List<LocationProduct> findAllByProduct(Product product);

    LocationProduct findByLocation_IdAndProduct_Id(Long locationId, Long productId);

    Page<LocationProduct> findAllByLocation_IdAndAmountGreaterThan(Pageable pageable, Long locationId, Integer amount);

    Optional<LocationProduct> findByIdAndLocation_IdAndAmountGreaterThan(Long id, Long locationId, Integer amount);

    Optional<LocationProduct> findByIdAndLocation_Id(Long id, Long locationId);
}
