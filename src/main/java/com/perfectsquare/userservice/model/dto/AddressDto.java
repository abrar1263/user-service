package com.perfectsquare.userservice.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AddressDto {

    private Long addressId;
    private String street1;
    private String street2;
    private String city;
    private String state;
    private int postalCode;
    private String country;
}
