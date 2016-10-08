package code.source.es;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    Button loginButton;
    Button returnButton;
    EditText account;
    EditText pwd;
    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view=inflater.inflate(R.layout.login, null);
        account=(EditText)view.findViewById(R.id.accountEt);
        pwd=(EditText)view.findViewById(R.id.pwdEt);
        loginButton=(Button)view.findViewById(R.id.subButton);
        returnButton=(Button)view.findViewById(R.id.returnButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!check(account.getText().toString())){
                    account.setError("Can only contain Numbers and letters");
                    return;
                }
                if(!check(pwd.getText().toString())){
                    pwd.setError("Can only contain Numbers and letters");
                    return;
                }
                Intent intent=new Intent();
                intent.setClass(getActivity(),MainScreen.class);
                intent.putExtra("Login","LoginSuccess");
                startActivity(intent);
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),MainScreen.class);
                intent.putExtra("Login","Return");
                startActivity(intent);

            }
        });
        return view;
    }
    private boolean check(String ch){
        if(ch==null||ch.length()==0)
            return false;
        char[] chars=ch.toCharArray();
        for(char c:chars){
            if((c>='a'&&c<='z')||(c>='0'&&c<='9'))
                continue;
            return false;
        }
        return true;
    }

}
