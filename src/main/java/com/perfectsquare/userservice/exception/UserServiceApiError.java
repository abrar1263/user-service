package com.perfectsquare.userservice.exception;

import com.perfectsquare.common.exception.PSApiError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@AllArgsConstructor
public enum UserServiceApiError implements PSApiError {
    BAD_REQUEST(2001,"BAD Request."),
    INVALID_INPUT(2002,"Invalid Input."),
    UNDEFINED_USER_ERROR(2003,"An Error Occurred"),
    USER_ALREADY_EXIST(3001,"User Already Registered"),
    USER_NOT_EXIST(3002,"User Not Registered"),
    USER_NOT_ACTIVE(3002,"User Not ACTIVE"),
    INCORRECT_PASSWORD(3003,"Incorrect Password"),
    USER_NOT_FOUND(3004,"User not found for given userId"),
    EMAIL_ALREADY_EXIST(3005,"Email Address already linked with another account"),
    USERNAME_ALREADY_EXIST(3006,"UserName Already Taken by another user"),
    MOBILE_ALREADY_EXIST(3007,"Mobile Number Already Taken by another user");

    final int code;
    final String message;
    @Setter
    @Getter
    Map<String,String> errorDetails;

    private UserServiceApiError(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrorCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return message;
    }

}
