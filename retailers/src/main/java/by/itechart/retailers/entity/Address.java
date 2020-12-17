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
@Table(name = ADDRESS_TABLE)
public class Address implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ADDRESS_STATE)
    private State state;

    @Column(name = ADDRESS_CITY)
    private String city;

    @Column(name = ADDRESS_FIRST_ADDRESS_LINE)
    private String firstAddressLine;

    @Column(name = ADDRESS_SECOND_ADDRESS_LINE)
    private String secondAddressLine;
}
