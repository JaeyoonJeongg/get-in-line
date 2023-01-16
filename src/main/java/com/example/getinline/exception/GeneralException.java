package com.example.getinline.exception;

import com.example.getinline.constant.ErrorCode;
import lombok.Getter;

@Getter
public class GeneralException extends RuntimeException{
    private final ErrorCode errorCode;

    public GeneralException(){
        super();
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message){
        super(message);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause){
        super(message, cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(Throwable cause){
        super(cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(String message, Throwable cause, boolean enableSupression, boolean writableStackTrace){
        super(message, cause, enableSupression, writableStackTrace);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause){
        super(errorCode.getMessage(), cause);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

    public GeneralException(ErrorCode errorCode, Throwable cause, boolean enableSuppression, boolean writableStackTrace){
        super(errorCode.getMessage(), cause, enableSuppression, writableStackTrace);
        this.errorCode = ErrorCode.INTERNAL_ERROR;
    }

}
