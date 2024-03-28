package com.soft.mapp.basecenter.handler;

import com.soft.mapp.basecenter.domain.loginVo.User;

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
