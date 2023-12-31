package com.perfectsquare.userservice.service;

import com.perfectsquare.common.exception.PSApiException;
import com.perfectsquare.userservice.exception.UserServiceApiError;
import com.perfectsquare.common.mapper.MapperUtil;
import com.perfectsquare.userservice.model.dto.AddressDto;
import com.perfectsquare.userservice.model.dto.LoginRequestDto;
import com.perfectsquare.userservice.model.dto.UserResponseDto;
import com.perfectsquare.userservice.model.dto.UsersRequestDto;
import com.perfectsquare.userservice.model.entities.Address;
import com.perfectsquare.userservice.model.entities.UsersEntity;
import com.perfectsquare.userservice.model.enums.UserStatus;
import com.perfectsquare.userservice.respository.AddressRepository;
import com.perfectsquare.userservice.respository.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    MapperUtil mapperUtil;

    UsersRepository usersRepository;

    AddressRepository addressRepository;

    public UserResponseDto registerUsers(UsersRequestDto usersRequestDto){
        Optional<UsersEntity> byUsername = usersRepository.findByUsernameOrEmailAddressOrMobileNumber(usersRequestDto.getUsername(),usersRequestDto.getEmailAddress(),usersRequestDto.getMobileNumber());
        if(byUsername.isPresent()){
            throw new PSApiException(UserServiceApiError.USER_ALREADY_EXIST);
        }
        UsersEntity usersEntity = mapperUtil.mapSourceToTargetClass(usersRequestDto, UsersEntity.class);
        usersEntity.setPassword(DigestUtils.md5DigestAsHex(usersRequestDto.getPassword().getBytes()));
        usersEntity.setCreatedBy(usersRequestDto.getUsername());
        usersEntity.setUserStatus(UserStatus.ACTIVE);
        UsersEntity userSaved = usersRepository.save(usersEntity);
        return mapperUtil.mapSourceToTargetClass(userSaved, UserResponseDto.class);
    }

    public List<UserResponseDto> getAllUsers(){
        List<UsersEntity> all = usersRepository.findAll();
        return mapperUtil.mapSourceToTargetClass(all, UserResponseDto.class);
    }

    public UserResponseDto loginUser(LoginRequestDto loginRequestDto){
        Optional<UsersEntity> byUsername = usersRepository.findByEmailAddress(loginRequestDto.getEmailAddress());
        if(byUsername.isEmpty()) {
            throw new PSApiException(UserServiceApiError.USER_NOT_EXIST);
        }
        UsersEntity usersEntity = byUsername.get();
        if(!UserStatus.ACTIVE.equals(usersEntity.getUserStatus())){
            throw new PSApiException(UserServiceApiError.USER_NOT_ACTIVE);
        }
        if(!usersEntity.getPassword().equals(DigestUtils.md5DigestAsHex(loginRequestDto.getPassword().getBytes()))){
            throw new PSApiException(UserServiceApiError.INCORRECT_PASSWORD);
        }
        return mapperUtil.mapSourceToTargetClass(usersEntity, UserResponseDto.class);

    }

    public UserResponseDto updateUser(UsersRequestDto usersRequestDto, Long userId) {
        UsersEntity byId = usersRepository.findById(userId).orElseThrow(() -> new PSApiException(UserServiceApiError.USER_NOT_FOUND));

        if(!UserStatus.ACTIVE.equals(byId.getUserStatus())){
            throw new PSApiException(UserServiceApiError.USER_NOT_ACTIVE);
        }

        if("".equals(usersRequestDto.getEmailAddress())) {
            Optional<UsersEntity> byEmailAddress = usersRepository.findByEmailAddress(usersRequestDto.getEmailAddress());
            if (byEmailAddress.isPresent()) {
                if (!byEmailAddress.get().getUserId().equals(userId)) {
                    throw new PSApiException(UserServiceApiError.EMAIL_ALREADY_EXIST);
                }
            }
        }

        if("".equals(usersRequestDto.getUsername())) {
            Optional<UsersEntity> byUsername = usersRepository.findByUsername(usersRequestDto.getUsername());
            if (byUsername.isPresent()) {
                if (!byUsername.get().getUserId().equals(userId)) {
                    throw new PSApiException(UserServiceApiError.USERNAME_ALREADY_EXIST);
                }
            }
        }

        if("".equals(usersRequestDto.getMobileNumber())) {
            Optional<UsersEntity> byMobileNumber = usersRepository.findByMobileNumber(usersRequestDto.getMobileNumber());
            if (byMobileNumber.isPresent()) {
                if (!byMobileNumber.get().getUserId().equals(userId)) {
                    throw new PSApiException(UserServiceApiError.MOBILE_ALREADY_EXIST);
                }
            }
        }
        byId.setFirstName(usersRequestDto.getFirstName());
        byId.setLastName(usersRequestDto.getLastName());
        byId.setUsername(usersRequestDto.getUsername());
        byId.setEmailAddress(usersRequestDto.getEmailAddress());
        byId.setDateOfBirth(usersRequestDto.getDateOfBirth());
        byId.setUpdatedAt(LocalDateTime.now());
        byId.setUpdatedBy(usersRequestDto.getUsername());

        Address address = byId.getAddress();
        AddressDto addressdto = usersRequestDto.getAddress();
        if(addressdto != null) {
            address.setStreet1(addressdto.getStreet1());
            address.setStreet2(addressdto.getStreet2());
            address.setCity(addressdto.getCity());
            address.setState(addressdto.getState());
            address.setPostalCode(addressdto.getPostalCode());
            address.setCountry(addressdto.getCountry());
            byId.setAddress(address);
        }
        UsersEntity save = usersRepository.save(byId);
        return mapperUtil.mapSourceToTargetClass(save, UserResponseDto.class);
    }

    public UserResponseDto deleteUser(Long userId){
        UsersEntity byId = usersRepository.findById(userId).orElseThrow(() -> new PSApiException(UserServiceApiError.USER_NOT_FOUND));

        if(!UserStatus.ACTIVE.equals(byId.getUserStatus())){
            throw new PSApiException(UserServiceApiError.USER_NOT_ACTIVE);
        }
    // Test Commit
        byId.setUserStatus(UserStatus.DELETED);
        UsersEntity save = usersRepository.save(byId);
        return mapperUtil.mapSourceToTargetClass(save, UserResponseDto.class);
    }
}
