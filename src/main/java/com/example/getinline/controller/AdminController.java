package com.example.getinline.controller;

import com.example.getinline.constant.PlaceType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/admin")
@Controller
public class AdminController {
    @GetMapping("/places")
    public ModelAndView adminPlace(
            PlaceType placeType,
            String placeName,
            String address
    ){
        Map<String,Object> map = new HashMap<>();
        map.put("placeType",placeType);
        map.put("placeName",placeName);
        map.put("address",address);
        return new ModelAndView("admin/places", map);
    }

    @GetMapping("/places/{placeId}")
    public String adminPlaceDetail(@PathVariable Integer placeId){
        return "admin/place-detail";
    }

    @GetMapping("/events")
    public String adminEvent(){
        return "admin/events";
    }

    @GetMapping("/events/{eventId}")
    public String adminEventDetail(){
        return "admin/event-detail";
    }
    
    //깃테스트

}
