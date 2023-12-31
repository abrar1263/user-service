package com.perfectsquare.userservice.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "blog_master_address")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Address  {

    @Id
    @SequenceGenerator(
            name = "address_sequence",
            sequenceName = "address_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "address_sequence"

    )
    private Long addressId;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private int postalCode;
    private String country;
}
