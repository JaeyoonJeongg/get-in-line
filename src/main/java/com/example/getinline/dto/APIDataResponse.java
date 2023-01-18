package com.example.getinline.dto;

import com.example.getinline.constant.ErrorCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class APIDataResponse<T> extends com.example.getinline.dto.APIErrorResponse {

    private final T data;

    private APIDataResponse(T data) {
        super(true, ErrorCode.OK.getCode(), ErrorCode.OK.getMessage());
        this.data = data;
    }

    public static <T> APIDataResponse<T> of(T data) {
        return new APIDataResponse<>(data);
    }
}
