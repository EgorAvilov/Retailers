package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = SUPPLIER_APPLICATION_TABLE)
public class SupplierApplication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SUPPLIER_APPLICATION_NUMBER)
    private Integer applicationNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SUPPLIER_APPLICATION_SUPPLIER_WAREHOUSE)
    private SupplierWarehouse supplierWarehouse;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = SUPPLIER_APPLICATION_DESTINATION_LOCATION)
    private Location destinationLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SUPPLIER_APPLICATION_CREATOR)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SUPPLIER_APPLICATION_UPDATER)
    private User updater;

    @Column(name = SUPPLIER_APPLICATION_REGISTRATION_DATE_TIME)
    private LocalDateTime registrationDateTime;

    @Column(name = SUPPLIER_APPLICATION_UPDATING_DATE_TIME)
    private LocalDateTime updatingDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = SUPPLIER_APPLICATION_APPLICATION_STATUS)
    private ApplicationStatus applicationStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = SUPPLIER_APPLICATION_JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = SUPPLIER_APPLICATION_JOIN_TABLE_JOIN_COLUMNS),
            inverseJoinColumns = {@JoinColumn(name = SUPPLIER_APPLICATION_JOIN_TABLE_INVERSE_JOIN_COLUMNS)})
    private List<ApplicationRecord> recordsList;

    @Column(name = SUPPLIER_APPLICATION_TOTAL_PRODUCT_AMOUNT)
    private Integer totalProductAmount;

    @Column(name = SUPPLIER_APPLICATION_TOTAL_UNIT_NUMBER)
    private Integer totalUnitNumber;
}
