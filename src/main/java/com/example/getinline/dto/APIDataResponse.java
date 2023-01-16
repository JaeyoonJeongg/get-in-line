package com.example.getinline.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class APIDataResponse extends com.example.getinline.dto.APIErrorResponse {

    private final Object data;

    private APIDataResponse(boolean success, Integer errorCode, String message, Object data) {
        super(success, errorCode, message);
        this.data = data;
    }

    public static APIDataResponse of(boolean success, Integer errorCode, String message, Object data) {
        return new APIDataResponse(success, errorCode, message, data);
    }
}