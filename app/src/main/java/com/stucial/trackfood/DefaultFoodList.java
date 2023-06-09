package com.stucial.trackfood;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.SearchView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class DefaultFoodList extends Fragment {
    private View view;
    private NonScrollListView listViewFood;
    private ArrayAdapter adapter;
    private SearchView searchBar;

    private int mealId;
    private String table;


    public DefaultFoodList() {
    }

    private void makeList() {
        ArrayList<String> foodList = new ArrayList<>();
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, foodList);
        listViewFood.setAdapter(adapter);


        foodList.clear();

        DBAdapter db = new DBAdapter(getActivity());
        db.open();

        String fields[] = new String[]{
                "foodName",
        };

        Cursor foodDbCursor = db.select("foodDB", fields);

        int categoriesCount = foodDbCursor.getCount();
        for (int i = 0; i < categoriesCount; i++) {
            String foodName = foodDbCursor.getString(foodDbCursor.getColumnIndexOrThrow("foodName"));
            foodList.add(foodName);
            foodDbCursor.moveToNext();
        }
        adapter.notifyDataSetChanged();
        db.close();
    }

    public void onListItemSelect() {
        listViewFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String foodName = listViewFood.getItemAtPosition(position).toString();

                Intent intent = new Intent(getActivity(), FoodFeature.class);
                intent.putExtra("foodName", foodName);
                intent.putExtra("mealId", mealId);
                intent.putExtra("tableName", table);
                startActivity(intent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_default_food_list, container, false);

        Intent intent = getActivity().getIntent();
        mealId = intent.getIntExtra("mealId", 1);
        table = intent.getStringExtra("tableName");


        listViewFood = view.findViewById(R.id.listViewFood);
        searchBar = view.findViewById(R.id.searchBar);

        makeList();
        onListItemSelect();

        searchBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                DefaultFoodList.this.adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                DefaultFoodList.this.adapter.getFilter().filter(newText);
                return false;
            }
        });


        return view;
    }
}