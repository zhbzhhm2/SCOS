package code.source.es.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import code.source.es.FoodDetailed;
import code.source.es.R;
import code.source.es.model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class order_pay extends Fragment {
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String type;
    private int numberSum=0,costSum=0;


    public order_pay() {
        // Required empty public constructor
    }

    ListView listView;
    SimpleAdapter simpleAdapter;
    List<Map<String,Object>> list;
    String []from=new String[]{"name","cost","number","intention"};
    ArrayList<Food> foods;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.food_order_view_frgment,container,false);
        listView=(ListView)view.findViewById(R.id.foodOrderFrgmentList);
        foods=new ArrayList<>();
        int []to=new int[]{R.id.orderDishID,R.id.orderDishCost,R.id.orderDishNumber,R.id.orderDishIntention};
        initList();
        simpleAdapter=new SimpleAdapter(view.getContext(),list,R.layout.food_order_view_item,from,to);
        listView.setAdapter(simpleAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("foods",foods);
                Intent intent=new Intent(view.getContext(), FoodDetailed.class);
                intent.putExtras(bundle);
                intent.putExtra("Item",position);
                startActivityForResult(intent,0);
            }
        });

        return view;
    }
    private void initList(){
        list=new LinkedList<>();

        String []name=new String[]{"first","sec","th"};
        String []cost=new String[]{"5","10","15"};
        int []number=new int[]{1,1,0};
        String []inten=new String[]{"q","w","e"};
        for (int i=0;i<name.length;i++){
            Food food=new Food(name[i]+getType(),Integer.parseInt(cost[i]));
            food.setIntention(inten[i]);
            if(number[i]>0)
                food.setOrder(true);
            else food.setOrder(false);
            foods.add(food);
            numberSum+=number[i];
            costSum+=Integer.parseInt(cost[i]);
            Map<String,Object> k=new HashMap<>();
            k.put(from[0],name[i]+getType());
            k.put(from[1],cost[i]);
            k.put(from[2],Integer.valueOf(number[i]).toString());
            k.put(from[3],inten[i]);
            list.add(k);

        }
    }

    public int getNumberSum() {
        return numberSum;
    }

    public int getCostSum() {
        return costSum;
    }
}
