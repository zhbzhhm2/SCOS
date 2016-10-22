package code.source.es;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import code.source.es.Fragment.Dish;
import code.source.es.model.User;


public class FoodView extends AppCompatActivity implements View.OnClickListener {
    ViewPager viewPager;
    List<Fragment> fragments;
    TextView []itemLinearLayout;
    int[] itemId;
    TextView current;
    String[] type = new String[]{"clod", "hot", "sea", "drink"};
    User user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);
        viewPager=(ViewPager)findViewById(R.id.fragmentViewPager);
        fragments=new LinkedList<Fragment>();
        initView();
        initData();
        user=(User) getIntent().getSerializableExtra("user");

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuReaday||item.getItemId()==R.id.menuNotReaday){
            Intent intent=new Intent(FoodView.this,FoodOrderView.class);
            Bundle bundle=new Bundle();
            bundle.putSerializable("user",user);
            intent.putExtra("index",item.getItemId());
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return true;
    }

    private void initView() {
        itemId=new int[]{R.id.coldDish,R.id.hotDish,R.id.seaFood,R.id.drink};
        itemLinearLayout=new TextView[itemId.length];
        for (int i=0;i<itemId.length;i++) {
            itemLinearLayout[i] = (TextView) findViewById(itemId[i]);
            itemLinearLayout[i].setOnClickListener(this);
        }
        itemLinearLayout[0].setSelected(true);
        current=itemLinearLayout[0];
        current.setBackgroundColor(getResources().getColor(R.color.inBlue));

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                current.setBackgroundColor(getResources().getColor(R.color.backBlue));
                changeTab(position);
                itemLinearLayout[position].setBackgroundColor(getResources().getColor(R.color.inBlue));
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
        for (int i=0;i<type.length;i++) {
            Fragment fragment=new Dish();
            ((Dish)fragment).setType(type[i]);
            fragments.add(fragment);
        }
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
        current.setBackgroundColor(getResources().getColor(R.color.backBlue));


        for(int i=0;i<itemId.length;i++) {
            if(itemId[i]==id)
                viewPager.setCurrentItem(i);
            else
                continue;
            itemLinearLayout[i].setSelected(true);
            current=itemLinearLayout[i];
            itemLinearLayout[i].setBackgroundColor(getResources().getColor(R.color.inBlue));
            break;
        }

    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        private List<Fragment> fragments;

        public FragmentAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments=fragments;
        }


        public Fragment getItem(int fragment) {
            return fragments.get(fragment);
        }

        public int getCount() {
            return fragments.size();
        }

    }
}




    /*
    ViewPager viewPager;
    private GestureDetector detector;
    FragmentManager fManager;
    Fragment[] fragment;
    private int currentItem=0;
    int []itemLayout=new int[]{R.id.coldDish,R.id.hotDish,R.id.seaFood,R.id.drink};
    String[] type = new String[]{"clod", "hot", "sea", "drink"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_view);

       // viewPager = (ViewPager) findViewById(R.id.viewPager);
        fManager = getSupportFragmentManager();
        fragment = new Dish[4];

        for(int i=0;i<itemLayout.length;i++)
            findViewById(itemLayout[i]).setOnClickListener(this);
        detector=new GestureDetector(this);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return detector.onTouchEvent(event);
    }

        //定义一个选中一个item后的处理
    public void setChioceItem(int index) {
        //重置选项+隐藏所有Fragment
        FragmentTransaction transaction  = fManager.beginTransaction();
        clearChioce();
        hideFragments(transaction);
        if(fragment[index]==null){
            fragment[index]=new Dish();
            ((Dish)fragment[index]).setType(type[index]);
            transaction.add(R.id.content,fragment[index]);
        }else
             transaction.show(fragment[index]);
        transaction.commit();
    }

    //隐藏所有的Fragment,避免fragment混乱
    private void hideFragments(FragmentTransaction transaction) {

        if(fragment[currentItem]!=null)
            transaction.hide(fragment[currentItem]);
        ((TextView)findViewById(itemLayout[currentItem])).setBackgroundColor(0);


    }


    //定义一个重置所有选项的方法
    public void clearChioce() {

    }

    @Override
    public void onClick(View view) {
        for(int i=0;i<itemLayout.length;i++){
            if(itemLayout[i]==view.getId()) {
                TextView textView=(TextView)findViewById(view.getId());
                setChioceItem(i);
                currentItem=i;
                textView.setBackgroundColor(getResources().getColor(R.color.inBlue));
            }

        }
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
        if(e1.getX()-e2.getX()>100) {
            onClick(findViewById(itemLayout[(currentItem + 1) % itemLayout.length]));
            return  true;
        }else if(e2.getX()-e1.getX()>100){
            onClick(findViewById(itemLayout[(currentItem -1 +itemLayout.length) % itemLayout.length]));
            return true;
        }
        return false;
    }
}*/

