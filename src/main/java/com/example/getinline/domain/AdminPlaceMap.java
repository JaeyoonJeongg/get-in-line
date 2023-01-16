package com.example.getinline.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AdminPlaceMap {

    private Long id;

    private String adminId;
    private String placeId;

    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

}
