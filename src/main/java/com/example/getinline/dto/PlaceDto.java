package com.example.getinline.dto;

import com.example.getinline.constant.PlaceType;

public record PlaceDto(
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
){
    public static PlaceDto of(
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ){
        return new PlaceDto(placeType, placeName, address, phoneNumber, capacity, memo);
    }
}
