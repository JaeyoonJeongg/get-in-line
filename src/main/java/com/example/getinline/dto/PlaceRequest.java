package com.example.getinline.dto;

import com.example.getinline.constant.PlaceType;

public record PlaceRequest(
        Long id,
        PlaceType placeType,
        String placeName,
        String address,
        String phoneNumber,
        Integer capacity,
        String memo
) {
    public static PlaceRequest of(
            Long id,
            PlaceType placeType,
            String placeName,
            String address,
            String phoneNumber,
            Integer capacity,
            String memo
    ) {
        return new PlaceRequest(id, placeType, placeName, address, phoneNumber, capacity, memo);
    }

}
