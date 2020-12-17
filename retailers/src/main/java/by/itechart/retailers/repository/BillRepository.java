package by.itechart.retailers.repository;

import by.itechart.retailers.entity.Bill;
import by.itechart.retailers.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {
    List<Bill> findAllByBillNumberAndCustomer_Id(Integer billNumber, Long customerId);

    Page<Bill> findAllByLocationIn(Pageable pageable, List<Location> locations);

    Page<Bill> findAllByLocation_Id(Pageable pageable, Long locationId);

    Optional<Bill> findByIdAndCustomer_Id(Long billId, Long customerId);

    @Query(value = "SELECT manager_id " +
            "FROM ( " +
            "         SELECT manager_id, " +
            "                ROW_NUMBER() " +
            "                OVER (PARTITION BY customer_id ORDER BY profit DESC) as rn " +
            "         FROM ( " +
            "                  SELECT bill.customer_id, " +
            "                         manager_id, " +
            "                         SUM(total_product_price - total_product_cost) as profit " +
            "                  FROM bill " +
            "                           join customer c on bill.customer_id = c.id " +
            "                           join users u on bill.manager_id = u.id " +
            "                  where registration_date_time between (select date_trunc('month', now())) " +
            "                  and (select (date_trunc('month', now()) + interval '1 month - 1 second')) " +
            "                  and customer_status = 'ACTIVE' " +
            "                  and user_status = 'ACTIVE' " +
            "                  GROUP BY bill.customer_id, manager_id " +
            "              ) AS tb1 " +
            "     ) as tb2 " +
            "WHERE rn <= 5",
    nativeQuery = true)
    List<Long> findAllBestEmployeeIds();

}
