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
@Table(name = INNER_APPLICATION_TABLE)
public class InnerApplication implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = INNER_APPLICATION_APPLICATION_NUMBER)
    private Integer applicationNumber;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = INNER_APPLICATION_SOURCE_LOCATION)
    private Location sourceLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = INNER_APPLICATION_DESTINATION_LOCATION)
    private Location destinationLocation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = INNER_APPLICATION_CREATOR)
    private User creator;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = INNER_APPLICATION_UPDATER)
    private User updater;

    @Column(name = INNER_APPLICATION_REGISTRATION_DATE_TIME)
    private LocalDateTime registrationDateTime;

    @Column(name = INNER_APPLICATION_UPDATING_DATE_TIME)
    private LocalDateTime updatingDateTime;

    @Enumerated(EnumType.STRING)
    @Column(name = INNER_APPLICATION_APPLICATION_STATUS)
    private ApplicationStatus applicationStatus;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(name = INNER_APPLICATION_JOIN_TABLE_NAME,
            joinColumns = @JoinColumn(name = INNER_APPLICATION_JOIN_TABLE_JOIN_COLUMNS),
            inverseJoinColumns = {@JoinColumn(name = INNER_APPLICATION_JOIN_TABLE_INVERSE_COLUMNS)})
    private List<ApplicationRecord> recordsList;

    @Column(name = INNER_APPLICATION_TOTAL_PRODUCT_AMOUNT)
    private Integer totalProductAmount;

    @Column(name = INNER_APPLICATION_TOTAL_UNIT_NUMBER)
    private Integer totalUnitNumber;
}
