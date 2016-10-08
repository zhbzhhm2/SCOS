package code.source.es;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainScreen extends FragmentActivity implements View.OnClickListener {


    private  int order=0,search=1,login=2,help=3;
    private boolean loginFlag=false;
    private int[] viewId={R.id.order,R.id.search,R.id.login,R.id.help};
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ViewPager viewPager;
    private LinearLayout linearLayout;
    Fragment orderFragment,searchFragment,loginFragment,helpFragment;

    HashMap<Integer,IcoView> ico;
    IcoView current;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        //-------------------------------------------------------
        linearLayout=(LinearLayout) findViewById(R.id.daohang);
        ico=new HashMap<Integer, IcoView>();
        //-------------------------------------------------------
        Intent intent=getIntent();
        String FromEntry=intent.getStringExtra("FromEntry");
        if(FromEntry!=null&&FromEntry.equals("enable" +
                ""))
            loginFlag=true;
        String FromLogin=intent.getStringExtra("Login");
        if(FromLogin!=null&&FromLogin.equals("LoginSuccess"))
            loginFlag=true;
        initView();
        initData();
        changeTab(R.id.login);
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        for(int at:viewId){
            LinearLayout LL=(LinearLayout) findViewById(at);
            ImageView IV = (ImageView) LL.getChildAt(0);
            TextView TV =  (TextView) LL.getChildAt(1);
            ico.put(at, new IcoView(LL, IV, TV));
            LL.setOnClickListener(this);
        }


        if(!loginFlag)
            hide();

        current=ico.get(viewId[login]);
        current.setSelected(true);


        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }

        });
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        viewPager.setOffscreenPageLimit(2); //设置向左和向右都缓存limit个页面
    }

    private void hide(){

        linearLayout.getChildAt(0).setVisibility(View.GONE);
        linearLayout.getChildAt(1).setVisibility(View.GONE);

    }

    private void initData() {
        orderFragment = new OrderFragment();
        searchFragment = new SearchFragment();
        loginFragment = new LoginFragment();
        helpFragment = new HelpFragment();

        if(loginFlag) {
            fragments.add(orderFragment);
            fragments.add(searchFragment);
        }
        fragments.add(loginFragment);
        fragments.add(helpFragment);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
//      MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        current.setSelected(false);
        switch (id) {
            case R.id.order:
                viewPager.setCurrentItem(order);
            case 0:
                current=ico.get(viewId[order]);
                break;
            case R.id.search:
                viewPager.setCurrentItem(search);
            case 1:
                current=ico.get(viewId[search]);
                break;
            case R.id.login:
                viewPager.setCurrentItem(loginFlag?login:login-2);
            case 2:
                current=ico.get(viewId[login]);
                break;
            case R.id.help:
                viewPager.setCurrentItem(loginFlag?help:help-2);
            case 3:
                current=ico.get(viewId[help]);
                break;
            default:
                break;
        }
        current.setSelected(true);
    }
}

class IcoView{
    LinearLayout linearLayout;
    private ImageView imageView;
    private TextView textView;


    public IcoView(LinearLayout l,ImageView i,TextView t){
        linearLayout=l;
        imageView=i;
        textView =t;
    }
    public LinearLayout getLinearLayout() {
        return linearLayout;
    }
    public TextView getTextView() {
        return textView;
    }

    public ImageView getImageView() {

        return imageView;
    }
    public void setSelected(boolean flag){
        imageView.setSelected(flag);
        textView.setSelected(flag);
    }
}
