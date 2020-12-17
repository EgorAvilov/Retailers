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
@Table(name = WRITE_OFF_ACT_TABLE)
public class WriteOffAct implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = WRITE_OFF_ACT_NUMBER)
    private Integer writeOffActNumber;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = WRITE_OFF_ACT_RECORDS)
    private List<WriteOffActRecord> writeOffActRecords;

    @Column(name = WRITE_OFF_ACT_ACT_DATE_TIME)
    private LocalDateTime actDateTime;

    @Column(name = WRITE_OFF_ACT_TOTAL_PRODUCT_AMOUNT)
    private Integer totalProductAmount;

    @Column(name = WRITE_OFF_ACT_TOTAL_PRODUCT_SUM)
    private BigDecimal totalProductSum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WRITE_OFF_ACT_ACT_LOCATION)
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WRITE_OFF_ACT_ACT_CUSTOMER)
    private Customer customer;
}
