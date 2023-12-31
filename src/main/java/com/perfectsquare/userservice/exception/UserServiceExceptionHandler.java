package com.perfectsquare.userservice.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.perfectsquare.common.exception.PSApiException;
import com.perfectsquare.common.model.dto.PSApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(PSApiException.class)
    public PSApiResponse apiException(PSApiException exception){
        return new PSApiResponse.Error(exception);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, JsonProcessingException.class})
    public PSApiResponse httpMessageNotReadableException(Exception exception){
        return new PSApiResponse.Error(UserServiceApiError.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public PSApiResponse httpMediaTypeNotSupportedException(HttpMediaTypeNotSupportedException exception){
        return new PSApiResponse.Error(UserServiceApiError.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<PSApiResponse> httpMediaTypeNotAcceptableException(HttpMediaTypeNotAcceptableException exception){
        MultiValueMap<String,String> headers = new LinkedMultiValueMap<>();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        return new ResponseEntity<PSApiResponse>(new PSApiResponse.Error(UserServiceApiError.BAD_REQUEST),headers, HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public PSApiResponse methodArgumentNotValidException(MethodArgumentNotValidException exception){
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        Map<String,String> fieldErrorMap = new HashMap<String,String>();
        fieldErrors.forEach(fieldError -> fieldErrorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
        UserServiceApiError userServiceApiError = UserServiceApiError.INVALID_INPUT;
        userServiceApiError.setErrorDetails(fieldErrorMap);
        return new PSApiResponse.Error(userServiceApiError);
    }

    @ExceptionHandler(Exception.class)
    public PSApiResponse globalExceptionHandler(Exception exception){
        return new PSApiResponse.Error(UserServiceApiError.UNDEFINED_USER_ERROR);
    }
}
