package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = CUSTOMER_TABLE)
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = CUSTOMER_NAME)
    private String name;

    @Column(name = CUSTOMER_EMAIL)
    private String email;

    @Column(name = CUSTOMER_REGISTRATION_DATE)
    private LocalDate registrationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = CUSTOMER_CUSTOMER_STATUS)
    private Status customerStatus;

}
