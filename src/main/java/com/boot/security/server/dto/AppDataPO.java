package com.boot.security.server.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@NoArgsConstructor
public class AppDataPO {
    private int id;
    private int Aid;
    private String trackName;
    private BigInteger sizeBytes;
    private String currency;
    private Double price;
    private BigInteger ratingCountTot;
    private BigInteger ratingCountVer;
    private double userRating;
    private double userRatingVer;
    private String ver;
    private String contRating;
    private String primeGenre;
    private BigInteger supDevicesNum;
    private BigInteger ipadScUrlsNum;
    private BigInteger langNum;
    private BigInteger vppLic;

    @Override
    public String toString() {
        return "AppDataPO{" +
                "id=" + id +
                ", Aid=" + Aid +
                ", trackName='" + trackName + '\'' +
                ", sizeBytes=" + sizeBytes +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", ratingCountTot=" + ratingCountTot +
                ", ratingCountVer=" + ratingCountVer +
                ", userRating=" + userRating +
                ", userRatingVer=" + userRatingVer +
                ", ver='" + ver + '\'' +
                ", contRating='" + contRating + '\'' +
                ", primeGenre='" + primeGenre + '\'' +
                ", supDevicesNum=" + supDevicesNum +
                ", ipadScUrlsNum=" + ipadScUrlsNum +
                ", langNum=" + langNum +
                ", vppLic=" + vppLic +
                '}';
    }
}
