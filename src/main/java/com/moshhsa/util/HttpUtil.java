package com.moshhsa.util;

import javax.servlet.http.HttpServletRequest;

public class HttpUtil {
    private static final String AUTHORIZATION = "Authorization";
    private static final String BEARER = "Bearer ";

    private HttpUtil(){

    }

    public static String extractTokenFromRequest(HttpServletRequest request){

        final String requestTokenHeader = request.getHeader(AUTHORIZATION);

        // JWT Token is in the form "Bearer token". Remove Bearer word and get only the Token
        if (requestTokenHeader != null && requestTokenHeader.startsWith(BEARER)) {

            return requestTokenHeader.substring(7);
        }
        return null;
    }
}
