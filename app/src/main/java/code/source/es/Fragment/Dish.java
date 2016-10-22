package code.source.es.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import code.source.es.FoodDetailed;
import code.source.es.R;
import code.source.es.model.Food;

import static android.content.ContentValues.TAG;

public class Dish extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String Type;
    ListView listView;
    List<HashMap<String,Object>> list;
    SimpleAdapter simpleAdapter;
    ArrayList<Food> foods;



    public Dish() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Dish.
     */
    // TODO: Rename and change types and number of parameters
    public static Dish newInstance(String param1, String param2) {
        Dish fragment = new Dish();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.dish_item_list,container,false);

        initDate(view);

        return view;
    }
    private void initDate(final View view){
        listView=(ListView) view.findViewById(R.id.dishList);
        list= new LinkedList<>();
        String []from=new String[]{"image","dishId","dishCost","dishButton"};
        int []to=new int[]{R.id.dishImage,R.id.dishId,R.id.dishCost,R.id.dishButton};
        int []image=new int[]{R.color.black,R.color.black,R.color.black};
        String []dishId=new String[]{"第一个"+getType(),"第二个"+getType(),"第三个"+getType()};
        String []dishCost=new String[]{"10","20","30"};
        String []dishButton=new String[]{getText(R.string.getFood).toString(),getText(R.string.getFood).toString(),getText(R.string.getFood).toString()};
        foods=new ArrayList<>();
        for (int i=0;i<dishId.length;i++){

            foods.add(new Food(dishId[i],Integer.parseInt(dishCost[i])));
            HashMap<String,Object> hashMap=new HashMap<>();
            hashMap.put("image",image[i]);
            hashMap.put("dishId",dishId[i]);
            hashMap.put("dishCost",dishCost[i]);
            hashMap.put("dishButton",dishButton[i]);
            list.add(hashMap);
        }
        simpleAdapter=new SimpleAdapter(view.getContext(),list,R.layout.dish_item,from,to){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v=super.getView(position,convertView,parent);
                final TextView text=(TextView) v.findViewById(R.id.dishButton);
              //  text.setTag(position);
                text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(((TextView)v).getText().toString().equals(getText(R.string.getFood).toString())) {
                            //点击了订餐
                            ((TextView) v).setText(getText(R.string.returnFood));
                            Toast.makeText(v.getContext(),getText(R.string.getSuccess),Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //点击了退订
                            ((TextView) v).setText(getText(R.string.getFood));
                            Toast.makeText(v.getContext(),getText(R.string.returnSuccess),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                return v;
            }
        };
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener());

    }
    private class  OnItemClickListener implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Log.i(TAG, "onItemClick: "+position);
            Bundle bundle=new Bundle();
            bundle.putSerializable("foods",foods);
            Intent intent=new Intent();
            intent.putExtras(bundle);
            intent.setClass(view.getContext(),FoodDetailed.class);
            intent.putExtra("Item",position);
            startActivityForResult(intent,0);

        }
    }

    public ArrayList<Food> getFoods() {
        return foods;
    }

    public void setFoods(ArrayList<Food> foods) {
        this.foods = foods;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }



}
