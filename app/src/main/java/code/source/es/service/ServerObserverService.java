package code.source.es.service;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by zhang on 16-10-19.
 */

public class ServerObserverService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Handler cMessageHandler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==1){

                }
                super.handleMessage(msg);
            }
        };
        return null;
    }


    class putDishNumber extends Thread{
        @Override
        public void run() {
            for (;;){
                if(isRunningApp(getApplicationContext(),"code.source.es"))
                    Log.i(TAG, "run: "+"code.source.es");
            }
        }
    }

    public static boolean isRunningApp(Context context, String packageName) {
        boolean isAppRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals(packageName) && info.baseActivity.getPackageName().equals(packageName)) {
                isAppRunning = true;
                // find it, break
                break;
            }
        }
        return isAppRunning;
    }
}
