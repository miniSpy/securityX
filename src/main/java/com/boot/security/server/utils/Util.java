package com.boot.security.server.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 *放弃使用java
public class Util {
    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        String filePath = "C:\\Users\\lenovo\\Desktop\\AppleStore.csv";
        BufferedReader bufferedReader = null;
        Connection conn = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/appdata?serverTimezone=UTC";
        try {
            Class.forName(driver);
            conn =  DriverManager.getConnection(url, "root", "12345678");

            bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                String[] columns = line.split(",");

                PreparedStatement pstmt =  pstmt = conn.prepareStatement("insert into appdata(id,Aid,track_name,size_bytes,currency,price,rating_count_tot,rating_count_ver,user_rating,"+
                        "user_rating_ver,ver,cont_rating,prime_genre,sup_devices_num,ipadSc_urls_num,lang_num,vpp_lic)value(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    pstmt.setString(1, columns[0]);
                    pstmt.setString(2, columns[1]);
                    pstmt.setString(3, columns[2]);
                    pstmt.setString(4, columns[3]);
                    pstmt.setString(5, columns[4]);
                    pstmt.setString(6, columns[5]);
                    pstmt.setString(7, columns[6]);
                    pstmt.setString(8, columns[7]);
                    pstmt.setString(9, columns[8]);
                    pstmt.setString(10, columns[9]);
                    pstmt.setString(11, columns[10]);
                    pstmt.setString(12, columns[11]);
                    pstmt.setString(13, columns[12]);
                    pstmt.setString(14, columns[13]);
                    pstmt.setString(15, columns[14]);
                    pstmt.setString(16, columns[15]);
                    pstmt.setString(17, columns[16]);
                    pstmt.executeUpdate();

            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally{
            try {
                conn.close();
                bufferedReader.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
 **/



