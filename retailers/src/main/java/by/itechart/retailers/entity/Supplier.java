package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = SUPPLIER_TABLE)
public class Supplier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SUPPLIER_FULL_NAME)
    private String fullName;

    @Column(name = SUPPLIER_IDENTIFIER)
    private Integer identifier;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = SUPPLIER_WARE_HOUSE_LIST)
    private List<SupplierWarehouse> wareHouseList;

    @ManyToOne
    @JoinColumn(name = SUPPLIER_CUSTOMER)
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = SUPPLIER_SUPPLIER_STATUS)
    private Status supplierStatus;
}
