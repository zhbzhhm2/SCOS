package code.source.es.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import code.source.es.R;
import code.source.es.model.Food;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodDetailedFragment extends Fragment {

    Food food;

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public FoodDetailedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_food_detailed, container, false);
        ((TextView)view.findViewById(R.id.costDetail)).setText(""+food.getCost());
        ((TextView)view.findViewById(R.id.IdDetail)).setText(food.getName());
        return view;
    }

}
