package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Recipe3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe3);

        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("순두부");
        arraylist.add("애호박");
        arraylist.add("대파");
        arraylist.add("양파");
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);

        ListView list = (ListView)findViewById(R.id.lv_recipe3Ingredients);
        list.setAdapter(Adapter);

        ArrayList<String> arraylist2 = new ArrayList<String>();
        arraylist2.add("1팩");
        arraylist2.add("0.5개");
        arraylist2.add("0.5개");
        arraylist2.add("0.5개");
        ArrayAdapter<String> Adapter2;
        Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist2);

        ListView list2 = (ListView)findViewById(R.id.lv_recipe3IngredientsAmount);
        list2.setAdapter(Adapter2);

        ArrayList<String> arraylist3 = new ArrayList<String>();
        arraylist3.add("고춧가루");
        arraylist3.add("다진마늘");
        arraylist3.add("멸치액젓");
        arraylist3.add("소금");
        ArrayAdapter<String> Adapter3;
        Adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist3);

        ListView list3 = (ListView)findViewById(R.id.lv_recipe3Sauce);
        list3.setAdapter(Adapter3);

        ArrayList<String> arraylist4 = new ArrayList<String>();
        arraylist4.add("2스푼");
        arraylist4.add("1스푼");
        arraylist4.add("2스푼");
        arraylist4.add("0.5스푼");
        ArrayAdapter<String> Adapter4;
        Adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist4);

        ListView list4 = (ListView)findViewById(R.id.lv_recipe3SauceAmount);
        list4.setAdapter(Adapter4);
    }
}
