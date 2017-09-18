package com.example.project.shopanywhere.utils;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class MyIntentService extends IntentService {

    private static Context vcontext;
    public MyIntentService() {
        super("MyIntentService");
    }
    public static void startSevice(Context context){
        vcontext = context;
        context.startService(new Intent(context,MyIntentService.class));
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            new Repository(vcontext).addStoreChangeListener();
            new Repository(vcontext).addSItemChangeListener();
            new Repository(vcontext).addAdminChangeListener();
        }
    }


}
