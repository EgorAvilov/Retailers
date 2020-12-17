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
@Table(name = LOCATION_TABLE)
public class Location implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = LOCATION_IDENTIFIER)
    private String identifier;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = LOCATION_CUSTOMER)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = LOCATION_ADDRESS)
    private Address address;

    @Column(name = LOCATION_TOTAL_CAPACITY)
    private Integer totalCapacity;

    @Column(name = LOCATION_AVAILABLE_CAPACITY)
    private Integer availableCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = LOCATION_LOCATION_TYPE)
    private LocationType locationType;

    @Column(name = LOCATION_LOCATION_TAX)
    private BigDecimal locationTax;

    @Enumerated(EnumType.STRING)
    @Column(name = LOCATION_STATUS)
    private DeletedStatus status;
}
