package com.meysam.myinsta.Classes;

import android.content.Context;
import android.content.SharedPreferences;

public class MySharedPreference {

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private static MySharedPreference instance;

    public static MySharedPreference getInstance(Context context){
        if (instance == null)
            instance = new MySharedPreference(context);
        return instance;
    }



   private MySharedPreference(Context context){

       sp = context.getSharedPreferences("myapp",Context.MODE_PRIVATE);
       editor = sp.edit();
   }

   public void setIsLogin(boolean isLogin){

        editor.putBoolean("isLogin",isLogin).apply();
   }

    public boolean getIsLogin(){
        return sp.getBoolean("isLogin",false);
   }

}
