package com.perfectsquare.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PSApiException extends RuntimeException {

    private int errorCode;
    private String errorMessage;

    public PSApiException(PSApiError apiError){
        this.errorCode = apiError.getErrorCode();
        this.errorMessage = apiError.getErrorMessage();
    }

}
