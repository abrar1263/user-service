package com.perfectsquare.userservice.respository;

import com.perfectsquare.userservice.model.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
