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
@Table(name = CATEGORY_PRODUCT_TABLE)
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CATEGORY_PRODUCT_NAME)
    private String name;

    @Column(name = CATEGORY_PRODUCT_CATEGORY_TAX)
    private BigDecimal categoryTax;

    @ManyToOne
    @JoinColumn(name = CATEGORY_PRODUCT_CUSTOMER)
    private Customer customer;
}
