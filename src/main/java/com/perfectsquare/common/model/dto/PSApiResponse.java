package com.perfectsquare.common.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.perfectsquare.common.exception.PSApiError;
import com.perfectsquare.common.exception.PSApiException;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class PSApiResponse {

    static final int CODE_SUCCESS = 200;
    static final String MESSAGE_SUCCESS = "SUCCESS";

    int code;
    String message;
    Object data;

    public static class Success extends PSApiResponse{
        public Success(Object object){
            code = CODE_SUCCESS;
            message = MESSAGE_SUCCESS;
            data = object;
        }
    }

    public static class Error extends PSApiResponse{
        public Error(PSApiException exception){
            code = exception.getErrorCode();
            message = exception.getErrorMessage();
            data = null;
        }
        public Error(PSApiError apiError){
            code = apiError.getErrorCode();
            message = apiError.getErrorMessage();
            data = null;
        }

        public Error(int errorCode, String errorMsg, Map<String,String> errorDetails){
            code = errorCode;
            message = errorMsg;
            data = errorDetails;
        }

    }
}
