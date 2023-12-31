package com.perfectsquare.userservice.controller;

import com.perfectsquare.common.model.dto.PSApiResponse;
import com.perfectsquare.userservice.model.dto.LoginRequestDto;
import com.perfectsquare.userservice.model.dto.UserResponseDto;
import com.perfectsquare.userservice.model.dto.UsersRequestDto;
import com.perfectsquare.userservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@AllArgsConstructor
public class UsersController {

    UserService userService;

    @PostMapping("/register-user")
    public PSApiResponse registerUser(@RequestBody UsersRequestDto usersRequestDto){
        UserResponseDto userResponseDto = userService.registerUsers(usersRequestDto);
        return new PSApiResponse.Success(userResponseDto);
    }

    @GetMapping("/get-all-users")
    public PSApiResponse getAllUsers(){
        List<UserResponseDto> allUsers =  userService.getAllUsers();
        return new PSApiResponse.Success(allUsers);
    }

    @PutMapping("/update-user/{userId}")
    public PSApiResponse updateUser(@RequestBody UsersRequestDto usersRequestDto,@PathVariable Long userId){
        UserResponseDto userResponseDto = userService.updateUser(usersRequestDto, userId);
        return new PSApiResponse.Success(userResponseDto);
    }

    @DeleteMapping("delete-user/{userId}")
    public PSApiResponse deleteUser(@PathVariable Long userId){
        UserResponseDto userResponseDto = userService.deleteUser(userId);
        return new PSApiResponse.Success(userResponseDto);
    }

    @PostMapping("/login-user")
    public PSApiResponse loginUser(@RequestBody LoginRequestDto loginRequestDto){
        UserResponseDto userResponseDto = userService.loginUser(loginRequestDto);
        return new PSApiResponse.Success(userResponseDto);
    }

}
