package code.source.es;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuAdapter;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.GestureDetector;
import android.widget.ViewFlipper;

import java.util.Map;

import code.source.es.model.User;

public class SCOSEntry extends Activity implements GestureDetector.OnGestureListener
{
    private ViewFlipper flipper;
    private GestureDetector detector;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.entry);
        detector = new GestureDetector(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        return detector.onTouchEvent(event);
    }


    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if(e1.getX()-e2.getX()>100){
            Intent intent=new Intent(SCOSEntry.this,MainScreen.class);
            if("1".equals(reciveSharedPreferences(new String[]{"loginState"})[0])){
                intent.putExtra("FromEntry","isLogin");
                intent.putExtra("user",new User(reciveSharedPreferences(new String[]{"loginState"})[0]));
            }else
                intent.putExtra("FromEntry","noLogin");
            startActivity(intent);
            return true;
        }
        return false;
    }
    private String[] reciveSharedPreferences(String[] name){
        //同样，在读取SharedPreferences数据前要实例化出一个SharedPreferences对象
        SharedPreferences sharedPreferences= getSharedPreferences("LoginOrRegister",
                Activity.MODE_PRIVATE);
        String []ret=new String[name.length];
        for (int i=0;i<name.length;i++)
            ret[i]= sharedPreferences.getString(name[i],"");
        return ret;
    }
}
