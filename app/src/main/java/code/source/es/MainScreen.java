package code.source.es;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import code.source.es.model.User;

import static android.content.ContentValues.TAG;

public class MainScreen extends Activity implements View.OnClickListener {





    private int order=0,search=1,login=2,help=3;
    private Class[] activity;
    SimpleAdapter entryButtonAdapter=null;
    List<HashMap<String,Object>> entryList=null;
    User user=new User();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);

        initView();
        GridView gridView=(GridView)findViewById(R.id.mainGrid);
        gridView.setAdapter(entryButtonAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                Bundle bundle= new Bundle();
                bundle.putSerializable("user",user);
                intent.putExtras(bundle);
                if(activity==null)
                    activity=new Class[]{FoodView.class,FoodOrderView.class,LoginOrRegister.class,SCOSHelper.class};
                if (user==null)
                    position+=2;
                intent.setClass(MainScreen.this,activity[position]);
                startActivityForResult(intent,0);
            }
        });
        Intent intent=getIntent();
        if(!"isLogin".equals(intent.getExtras().getString("FromEntry")))
            hideView();

    }

    private  void initView(){
        Log.i(TAG, "initView: initView Start");
        entryList=new ArrayList<>();
        String []from=new String[]{"imageE","textE"};
        int []to=new int[]{R.id.imageE,R.id.textE};

        int []image=new int[]{R.drawable.order,R.drawable.search,R.drawable.login,R.drawable.help};
        String []text=new String[]{getString(R.string.order),getString(R.string.search),getString(R.string.login),getString(R.string.help)};

        for(int i=0;i<image.length;i++){
            HashMap<String ,Object> map=new HashMap<>();
            map.put("imageE",image[i]);
            map.put("textE",text[i]);
            entryList.add(map);
        }
        entryButtonAdapter=new SimpleAdapter(this,entryList,R.layout.entry_but,from,to);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data==null)
            return;
        String ret=data.getExtras().getString("LoginOrRegister");
        if (ret!=null){
            if(ret.equals("LoginSuccess")) {
                Log.i(TAG, "onActivityResult: " + ret);
               // showView(data);
            }
            else if (ret.equals("Return"))
                Log.i(TAG, "onActivityResult: "+ret);
            else if(ret.equals("RegisterSuccess")){
                Log.i(TAG, "onActivityResult: " + ret);
              //  showView(data);
                Toast.makeText(getApplicationContext(),R.string.welcomeReg,Toast.LENGTH_SHORT).show();
            }
        }
        if("1".equals(reciveSharedPreferences(new String[]{"loginState"})[0]))
            showView(data);
        else
            hideView();
    }
    private void hideView(){
        if(user!=null) {
            entryList.remove(search);
            entryList.remove(order);
            entryButtonAdapter.notifyDataSetChanged();
        }
        user=null;
    }
    private void showView(Intent date){
        if(user==null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("imageE", R.drawable.order);
            map.put("textE", getString(R.string.order));
            entryList.add(order, map);
            map = new HashMap<>();
            map.put("imageE", R.drawable.search);
            map.put("textE", getString(R.string.search));
            entryList.add(search, map);
            entryButtonAdapter.notifyDataSetChanged();
        }

        user = (User) date.getSerializableExtra("User");
        Log.i(TAG, "showView: "+user.getUserName());
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
