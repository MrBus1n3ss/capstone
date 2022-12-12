package com.wgu.scheduling.util;

public class Auth {

    private static boolean isUserAuthorized = false;

    public static boolean isIsUserAuthorized() {
        return isUserAuthorized;
    }

    public static void setIsUserAuthorized(boolean isUserAuthorized) {
        Auth.isUserAuthorized = isUserAuthorized;
    }

    public static void logout(){
        isUserAuthorized = false;
    }
}
