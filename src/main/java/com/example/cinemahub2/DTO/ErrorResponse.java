package com.example.cinemahub2.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
public class ErrorResponse {

    private Date hour = new Date();
    private String message;
    private String url;

    public ErrorResponse(String message, String url){
        this.message = message;
        this.url = url.replace("uri=", "");
    }
}
