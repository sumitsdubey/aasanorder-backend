package com.sumit.aasanorder_backend.utils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

public class CookieClient {

    public static void setCookie(HttpServletResponse resp, String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(12*60*60);
        cookie.setPath("/");
        resp.addCookie(cookie);
    }
}
