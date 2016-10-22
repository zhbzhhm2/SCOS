package code.source.es;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import code.source.es.Fragment.order_pay;
import code.source.es.model.User;

public class FoodOrderView extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    List<Fragment> fragments;
    TextView[] itemLinearLayout;
    int[] itemId;
    TextView current;
    String[] type = new String[]{"未点","已点"};
    String[] buttonName=new String[]{"提交","结账"};
    private User user;
    ProgressBar progressBar;
    boolean payFlag=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_order_view);
        viewPager = (ViewPager) findViewById(R.id.fragmentViewPager_Order);
        fragments = new LinkedList<Fragment>();
        initView();
        initData();
        progressBar=new ProgressBar(this);
        user=(User)getIntent().getSerializableExtra("user");

    }

    private void initView() {
        itemId = new int[]{R.id.notOrder,R.id.alreadyOrder};
        itemLinearLayout = new TextView[itemId.length];
        for (int i = 0; i < itemId.length; i++) {
            itemLinearLayout[i] = (TextView) findViewById(itemId[i]);
            itemLinearLayout[i].setOnClickListener(this);
        }

        itemLinearLayout[0].setSelected(true);
        current = itemLinearLayout[0];

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                changeTab(position);
                ((TextView)findViewById(R.id.numberDish)).setText(String.valueOf(((order_pay)fragments.get(position)).getNumberSum()));
                ((TextView)findViewById(R.id.costOfDsh)).setText(String.valueOf(((order_pay)fragments.get(position)).getCostSum()));
                ((TextView)findViewById(R.id.costButton)).setText(buttonName[position]);
                if(position==1&&payFlag)
                    ((TextView)findViewById(R.id.costButton)).setBackgroundColor(getColor(R.color.black));
                else
                    ((TextView)findViewById(R.id.costButton)).setBackgroundColor(getColor(R.color.orange));
                ((TextView)findViewById(R.id.costButton)).setOnClickListener(FoodOrderView.this);
            }
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        viewPager.setOffscreenPageLimit(2); //设置向左和向右都缓存limit个页面
    }


    private void initData() {
        for (int i = 0; i < type.length; i++) {
            Fragment fragment = new order_pay();
            ((order_pay) fragment).setType(type[i]);
            fragments.add(fragment);
        }

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
//      MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.costButton){
            if(user!=null&&((TextView)v).getText().equals("结账")&&!payFlag) {
                if (user.getOldUser())
                    Toast.makeText(v.getContext(), "您好，老顾客，本次你可享受 7 折优惠", Toast.LENGTH_SHORT).show();

                AsyncTask  execute = new AsyncTask() {
                    ProgressDialog progressDialog;
                    @Override
                    protected void onPreExecute() {
                        progressDialog = new ProgressDialog(FoodOrderView.this);
                        progressDialog.setProgressStyle(progressDialog.STYLE_HORIZONTAL);
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        super.onPreExecute();
                    }
                    @Override
                    protected Object doInBackground(Object[] params) {
                        long startTime = new Date().getTime();
                        long endTime = new Date().getTime();
                        for (; endTime - startTime < 6000; endTime = new Date().getTime())
                            progressDialog.setProgress((int) (endTime - startTime)/ 60);
                        progressDialog.setProgress(100);
                        return null;
                    }
                    @Override
                    protected void onPostExecute(Object o) {
                        progressDialog.dismiss();
                        progressDialog=null;
                        Toast.makeText(FoodOrderView.this,"结账成功,剩余0元，1亿积分.",Toast.LENGTH_SHORT).show();
                        setPayFlag(true);
                        super.onPostExecute(o);
                    }
                };
                execute.execute();

            }
        }
        changeTab(v.getId());
    }
    private void setPayFlag(boolean flag){
        payFlag=flag;
        if (payFlag){
            ((TextView)findViewById(R.id.costButton)).setBackgroundColor(getColor(R.color.black));
        }

    }

    private void changeTab(int id) {
        current.setSelected(false);

        for (int i = 0; i < itemId.length; i++) {
            if (itemId[i] == id)
                viewPager.setCurrentItem(i);
            else
                continue;
            itemLinearLayout[i].setSelected(true);
            current = itemLinearLayout[i];

            break;
        }

    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }


        public Fragment getItem(int fragment) {
            return fragments.get(fragment);
        }

        public int getCount() {
            return fragments.size();
        }

    }
}
