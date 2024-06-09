//package com.example.travelwing.travelwing.Service;
//
//
//import com.example.travelwing.travelwing.Domain.Hotel;
//import lombok.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//
//@Service
//public class HotelService {
//
//
//    @Value("${google.places.api.key}")
//    private String apiKey;
//
//    public List<Hotel> getNearbyHotels(String destinationLocation) {
//        String url = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%s&radius=1500&type=hotel&key=%s",
//                destinationLocation, apiKey);
//
//        RestTemplate restTemplate = new RestTemplate();
//        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
//
//        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
//
//        return results.stream().map(result -> {
//            Hotel hotel = new Hotel();
//            hotel.setName((String) result.get("name"));
//            hotel.setAddress((String) result.get("vicinity"));
//            hotel.setRating(result.get("rating") != null ? (Double) result.get("rating") : 0.0);
//            return hotel;
//        })A.collect(Collectors.toList());
//    }
//
//}
