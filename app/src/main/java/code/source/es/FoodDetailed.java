package code.source.es;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import code.source.es.Fragment.FoodDetailedFragment;
import code.source.es.model.Food;

public class FoodDetailed extends AppCompatActivity {


    ViewPager viewPager;
    List<Fragment> fragments;
    ArrayList<Food> foods=new ArrayList<Food>();
    {
        foods.add(new Food("first",10));
        foods.add(new Food("sec",20));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detailed);
        Intent intent=getIntent();
        if(getIntent().getSerializableExtra("foods")!=null)
            foods=(ArrayList<Food>) getIntent().getSerializableExtra("foods");
        viewPager=(ViewPager)findViewById(R.id.viewPageDetailed);
        fragments = new LinkedList<Fragment>();
        initView();
        initData();
        viewPager.setCurrentItem(intent.getExtras().getInt("Item"));
        ((TextView)findViewById(R.id.costButton)).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                TextView viewOn=(TextView)v;
                if("点菜".equals(viewOn.getText().toString()))
                { //要点菜
                    viewOn.setText("取消");
                    foods.get(viewPager.getCurrentItem()).setOrder(true);
                }else {
                    //要取消
                    viewOn.setText("点菜");
                    foods.get(viewPager.getCurrentItem()).setOrder(false);
                }
            }
        });
    }
    private void initView() {


    }


    private void initData() {
        for (int i = 0; i < foods.size(); i++) {
            Fragment fragment = new FoodDetailedFragment();
            ((FoodDetailedFragment) fragment).setFood(foods.get(i));
            fragments.add(fragment);
        }
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
//      MyFragmentStatePagerAdapter adapter = new MyFragmentStatePagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
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
