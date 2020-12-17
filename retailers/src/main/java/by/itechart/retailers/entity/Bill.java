package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = BILL_TABLE)
public class Bill implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = BILL_BILL_NUMBER)
    private Integer billNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BILL_LOCATION)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BILL_SHOP_MANAGER)
    private User shopManager;

    @Column(name = BILL_REGISTRATION_DATE_TIME)
    private LocalDateTime registrationDateTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = BILL_RECORD_LIST)
    private List<BillRecord> recordList;

    @Column(name = BILL_TOTAL_PRODUCT_AMOUNT)
    private Integer totalProductAmount;

    @Column(name = BILL_TOTAL_PRODUCT_PRICE)
    private BigDecimal totalPrice;

    @Column(name = BILL_TOTAL_PRODUCT_COST)
    private BigDecimal totalCost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BILL_CUSTOMER)
    private Customer customer;
}
