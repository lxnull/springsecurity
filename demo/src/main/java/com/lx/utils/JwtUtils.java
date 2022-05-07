package com.lx.utils;

import com.lx.pojo.User;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;

public class JwtUtils {

    private static long TIME = 24 * 60 * 60 * 1000;

    private static String SIGNATURE = "admin";

    public static String createToken(String subject) {
        JwtBuilder builder = Jwts.builder();
        String jwtToken = builder
                // header
                .setHeaderParam("typ","JWT")
                .setHeaderParam("alg","HS256")
                // payload
                .claim("uid", subject)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis() + TIME))
                .setId(String.valueOf(UUID.randomUUID()))
                // signature
                .signWith(SignatureAlgorithm.HS256,SIGNATURE)
                .compact();

        return jwtToken;
    }

    public static boolean checkToken(String token) {
        if (token == null) {
            return false;
        }

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(SIGNATURE).parseClaimsJws(token);
            Claims body = jws.getBody();
            System.out.println(body.get("uid"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public static String parseJWT(String token) {
        String uid = "";

        if (token == null) {
            return "false";
        }

        try {
            Jws<Claims> jws = Jwts.parser().setSigningKey(SIGNATURE).parseClaimsJws(token);
            Claims body = jws.getBody();
            uid = body.get("uid").toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }

        return uid;
    }
}