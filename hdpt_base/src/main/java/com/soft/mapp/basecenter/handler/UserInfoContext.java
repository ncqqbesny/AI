package com.soft.mapp.basecenter.handler;

import com.soft.mapp.basecenter.domain.loginVo.User;

public class UserInfoContext {
    private static ThreadLocal<User> userInfo = new ThreadLocal<User>();
    public static String KEY_USERINFO_IN_HTTP_HEADER = "X-AUTO-FP-USERINFO";

    public UserInfoContext() {
    }

    public static User getUser(){
        return userInfo.get();
    }
    public static void setUser(User user){
        userInfo.set(user);
    }
}