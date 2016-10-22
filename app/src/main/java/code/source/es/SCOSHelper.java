package code.source.es;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SCOSHelper extends Activity {
    GridView gridView;
    List<Map<String,Object>> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoshelper);
        initView();
    }
    private void initView(){
        String []icoName=new String []{"用户使用协议","关于系统", "电话人工帮助","短信帮助","邮件帮助"};
        int []image=new int[]{R.drawable.help,R.drawable.help,R.drawable.help,R.drawable.help,R.drawable.help};
        String []from=new String[]{"name","image"};
        int []to=new int[]{R.id.textE,R.id.imageE};
        list=new LinkedList<>();
        SimpleAdapter simpleAdapter=new SimpleAdapter(this,list,R.layout.entry_but,from,to);
        gridView=(GridView)findViewById(R.id.helperGridView);
        for(int i=0;i<icoName.length;i++){
            Map map=new HashMap();
            map.put("name",icoName[i]);
            map.put("image",image[i]);
            list.add(map);
        }
        gridView.setAdapter(simpleAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String mobile="5554";
                String content="“test scos helper";
                switch (position){

                    case 2:
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:"+ mobile));//mobile为你要拨打的电话号码，模拟器中为模拟器编号也可
                        startActivity(intent);
                        break;
                    case 3:
                        SmsManager smsManager = SmsManager.getDefault();
                        ArrayList<String> texts = smsManager.divideMessage(content);//拆分短信,短信字数太多了的时候要分几次发
                        for(String text : texts){
                            smsManager.sendTextMessage(mobile, null, text, null, null);//发送短信,mobile是对方手机号
                        }
                        Toast.makeText(SCOSHelper.this,R.string.sendSuccess,Toast.LENGTH_SHORT);
                        break;
                    case 4:
                        final Handler myHandler = new Handler() {
                            public void handleMessage(Message msg) {
                                switch (msg.what) {
                                    case 1:
                                        Toast.makeText(SCOSHelper.this,R.string.sendSuccess,Toast.LENGTH_SHORT).show();
                                        break;
                                }
                                super.handleMessage(msg);
                            }
                        };
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent data=new Intent(Intent.ACTION_SENDTO);
                                data.setData(Uri.parse("mailto:zhbzhhm2@mail.ustc.edu.cn"));
                                data.putExtra(Intent.EXTRA_SUBJECT, "这是标题");
                                data.putExtra(Intent.EXTRA_TEXT, "这是内容");
                                startActivity(data);
                                myHandler.sendEmptyMessage(1);
                            }
                        }).start();
                        break;
                }
            }
        });
    }

}
