package com.app.device.handler;


import com.app.device.domain.loginVo.User;

public  class CommonBusiness {
    public static boolean isAdmin(){
        boolean flag=false;
        User context = UserInfoContext.getUser();
        if(context!=null && "admin".equalsIgnoreCase(context.getUsername())){
            flag=true;
        }
        return flag;
    }
}
