package com.example.qr_code_scanner;


import android.content.Context;

import androidx.room.Room;

public class Databaseclient {
    private Context context;
    private  static  Databaseclient mInstance;

    private  AppaDatabase appaDatabase;
    private  Databaseclient(Context context){
        this.context=context;
        appaDatabase= Room.databaseBuilder(context,AppaDatabase.class,"MyContents").build();
    }
    public  static  synchronized  Databaseclient getInstance(Context context){
        if (mInstance==null){
            mInstance=new Databaseclient(context);
        }
        return mInstance;
    }

    public  AppaDatabase getAppaDatabase(){
        return  appaDatabase;
    }
}
