package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final AddService addService;

    public MainController(AddService addService) {
        this.addService = addService;
    }


    @PostMapping("/modify")
    public ResponseEntity<?> add(@RequestBody RequestData requestDto) {

        try {

            ResponseData responseDto = addService.add(requestDto);

            if (responseDto != null) return new ResponseEntity<>(responseDto, HttpStatus.OK);

            return ResponseEntity.status(418).build();

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(503).build();
        }

    }
}
