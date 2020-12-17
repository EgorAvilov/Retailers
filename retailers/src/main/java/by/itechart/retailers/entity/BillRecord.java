package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = BILL_RECORD_TABLE)
public class BillRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BILL_RECORD_PRODUCT)
    private Product product;

    @Column(name = BILL_RECORD_PRODUCT_AMOUNT)
    private Integer productAmount;

    @Column(name = BILL_RECORD_PRODUCT_PRICE)
    private BigDecimal productPrice;
}
