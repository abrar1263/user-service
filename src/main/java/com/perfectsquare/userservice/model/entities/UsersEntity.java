package com.perfectsquare.userservice.model.entities;

import com.perfectsquare.userservice.model.enums.UserStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "blog_master_users")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UsersEntity extends BaseEntity{

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1001
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )

    private Long userId;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    @Column(unique = true)
    private String emailAddress;
    @Column(unique = true)
    private String mobileNumber;
    @Column(unique = true)
    private String username;
    private String password;
    @OneToOne(fetch = FetchType.LAZY,cascade =  CascadeType.ALL)
    @JoinColumn(name = "address_id",referencedColumnName = "addressId")
    private Address address;
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;


}
