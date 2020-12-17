package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = PRODUCT_TABLE)
public class Product implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = PRODUCT_UPC)
    private Long upc;

    @Column(name = PRODUCT_LABEL)
    private String label;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = PRODUCT_CATEGORY)
    private Category category;

    @Column(name = PRODUCT_VOLUME)
    private Integer volume;

    @ManyToOne
    @JoinColumn(name = PRODUCT_CUSTOMER)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = PRODUCT_STATUS)
    private DeletedStatus status;
}
