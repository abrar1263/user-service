package com.perfectsquare.userservice.respository;

import com.perfectsquare.userservice.model.entities.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<UsersEntity, Long> {

    Optional<UsersEntity> findByUsernameOrEmailAddressOrMobileNumber(String username,String email, String mobileNumber);

    Optional<UsersEntity> findByEmailAddress(String emailAddress);

    Optional<UsersEntity> findByUsername(String emailAddress);
    Optional<UsersEntity> findByMobileNumber(String emailAddress);

}
