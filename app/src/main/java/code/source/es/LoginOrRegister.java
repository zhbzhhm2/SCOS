package code.source.es;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Pattern;

import code.source.es.model.User;

/**
 * Created by zhang on 16-10-12.
 */

public class LoginOrRegister extends Activity {
    EditText accountEt,pwdEt;
    Button returnButton,loginButton,regButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        initView();
    }
    private void returnActivity(String flag){
        Intent intent=new Intent();
        if(flag.equals("Return"))
            intent.putExtra("LoginOrRegister",flag);
        else if(flag.equals("LoginSuccess")){

            Bundle bundle=new Bundle();
            bundle.putSerializable("User",new User(accountEt.getText().toString(),pwdEt.getText().toString(),true));
            intent.putExtras(bundle);
            intent.putExtra("LoginOrRegister",flag);
        }else if(flag.equals("RegisterSuccess")){
            Bundle bundle=new Bundle();
            bundle.putSerializable("User",new User(accountEt.getText().toString(),pwdEt.getText().toString(),false));
            intent.putExtras(bundle);
            intent.putExtra("LoginOrRegister",flag);
        }
        setResult(0,intent);
        finish();

    }
    private void initView(){
        accountEt=(EditText)findViewById(R.id.accountEt);
        pwdEt=(EditText)findViewById(R.id.pwdEt);
        loginButton=(Button)findViewById(R.id.subButton);
        returnButton=(Button)findViewById(R.id.returnButton);
        regButton=(Button) findViewById(R.id.regButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check(accountEt.getText().toString())){
                    accountEt.setError(getString(R.string.userInputErr));
                    return;
                }
                if(!check(pwdEt.getText().toString())){
                    pwdEt.setError(getString(R.string.pwdInputErr));
                    return;
                }
                sendSharedPreferences(new String[]{"userName","loginState"}
                        ,new String[]{accountEt.getText().toString(),"1"});
                returnActivity("LoginSuccess");
            }
        });

        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check(accountEt.getText().toString())){
                    accountEt.setError(getString(R.string.userInputErr));
                    return;
                }
                if(!check(pwdEt.getText().toString())){
                    pwdEt.setError(getString(R.string.pwdInputErr));
                    return;
                }
                sendSharedPreferences(new String[]{"userName","loginState"}
                        ,new String[]{accountEt.getText().toString(),"1"});
                returnActivity("RegisterSuccess");
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(reciveSharedPreferences(new String []{"userName"})[0].length()>0)
                    sendSharedPreferences(new String []{"loginState"},new String[]{"0"});
                returnActivity("Return");
            }
        });
        String[] recive= reciveSharedPreferences(new String[]{"userName"});
        if(recive[0].length()>0){
            regButton.setVisibility(Button.GONE);
            accountEt.setText(recive[0]);
        }else {
            loginButton.setVisibility(Button.GONE);
        }
    }
    private boolean sendSharedPreferences(String[] name, String[] values){
        if(name.length!=values.length)
            return false;
        SharedPreferences mySharedPreferences= getSharedPreferences("LoginOrRegister",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        for(int i=0;i<name.length;i++){
            editor.putString(name[i],values[i]);
        }
        editor.commit();
        return true;
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

    private boolean check(String c){
        return Pattern.compile("[0-9a-zA-Z]+").matcher(c).matches();
    }
}
