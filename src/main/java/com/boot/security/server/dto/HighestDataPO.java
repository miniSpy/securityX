package com.boot.security.server.dto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HighestDataPO extends AppDataPO {



    private  String description;


    @Override
    public String toString() {
        return "HighestDataDto{" +
                "description='" + description + '\'' +
                '}';
    }
}
