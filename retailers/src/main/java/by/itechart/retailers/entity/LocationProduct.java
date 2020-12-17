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
@Table(name = LOCATION_PRODUCT_TABLE)
public class LocationProduct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = LOCATION_PRODUCT_COST)
    private BigDecimal cost;

    @Column(name = LOCATION_PRODUCT_AMOUNT)
    private Integer amount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = LOCATION_PRODUCT_PRODUCT)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = LOCATION_PRODUCT_LOCATION)
    private Location location;




}
