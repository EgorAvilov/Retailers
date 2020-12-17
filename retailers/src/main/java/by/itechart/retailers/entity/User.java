package by.itechart.retailers.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static by.itechart.retailers.constant.DataBaseConstants.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = USER_TABLE)
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = USER_FIRST_NAME)
    private String firstName;

    @Column(name = USER_LAST_NAME)
    private String lastName;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = USER_ADDRESS)
    private Address address;

    @Column(name = USER_BIRTHDAY)
    private LocalDate birthday;

    @CollectionTable(name = USER_COLLECTION_TABLE_NAME, joinColumns = @JoinColumn(name = USER_COLLECTION_TABLE_JOIN_COLUMNS))
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<Role> userRole = new ArrayList<>();

    @Column(name = USER_EMAIL)
    private String email;

    @Column(name = USER_PASSWORD)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = USER_STATUS)
    private Status userStatus;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_LOCATION)
    private Location location;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = USER_CUSTOMER)
    private Customer customer;

    @Column(name = USER_LOGIN)
    private String login;
}
