package com.boot.security.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class HighestDataDto {
    private int id;
    private int Aid;
    private String trackName;
    private BigInteger sizeBytes;
    private Double price;
    private  String description;


    @Override
    public String toString() {
        return "HighestDataDto{" +
                "id=" + id +
                ", Aid=" + Aid +
                ", trackName='" + trackName + '\'' +
                ", sizeBytes=" + sizeBytes +
                ", price=" + price +
                ", description='" + description + '\'' +
                '}';
    }
}

