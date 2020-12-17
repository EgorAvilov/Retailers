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
@Table(name = WRITE_OFF_ACT_RECORD_TABLE)
public class WriteOffActRecord implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WRITE_OFF_ACT_RECORD_PRODUCT)
    private Product product;

    @Column(name = WRITE_OFF_ACT_RECORD_AMOUNT)
    private Integer amount;

    @Enumerated(EnumType.STRING)
    @Column(name = WRITE_OFF_ACT_RECORD_REASON)
    private Reason reason;
}
