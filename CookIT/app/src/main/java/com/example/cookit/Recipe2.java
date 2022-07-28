package com.example.cookit;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import static com.example.cookit.AppMain.sqlDB;

public class Recipe2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe2);

        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("양파");
        arraylist.add("대파");
        arraylist.add("어묵");
        arraylist.add("청양고추");
        //ArrayAdapter<String> Adapter;
        //Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist);

        //DB 재료랑 비교
        sqlDB = AppMain.myDBHelper.getReadableDatabase();
        Cursor cursor = sqlDB.rawQuery("SELECT foodName FROM food;",null);

        int count = cursor.getCount();
        String[] foods = new String[count];

        for(int i=0;i<count;i++){
            cursor.moveToNext();
            String name = cursor.getString(0);

            foods[i] = name;
        }
        HashSet<String> hashSet = new HashSet<>(Arrays.asList(foods));
        String[] setFoods = hashSet.toArray(new String[0]);

        ListView list = (ListView)findViewById(R.id.lv_recipe2Ingredients);

        ArrayAdapter<String> Adapter;
        //재료가 있을 때 초록색으로 없으면 빨간색으로 출력
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView tv = (TextView) view.findViewById(android.R.id.text1);
                int i = 0;
                for (String food : setFoods) {
                    if (arraylist.get(position).equals(food)){
                        tv.setTextColor(Color.GREEN);
                    }else{
                        tv.setTextColor(Color.RED);
                    }
                }

                return view;
            }
        };
        list.setAdapter(Adapter);

        ArrayList<String> arraylist2 = new ArrayList<String>();
        arraylist2.add("300g");
        arraylist2.add("0.25개");
        arraylist2.add("0.3개");
        arraylist2.add("1개");
        ArrayAdapter<String> Adapter2;
        Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist2);

        ListView list2 = (ListView)findViewById(R.id.lv_recipe2IngredientsAmount);
        list2.setAdapter(Adapter2);

        ArrayList<String> arraylist3 = new ArrayList<String>();
        arraylist3.add("멸치육수");
        arraylist3.add("진간장");
        arraylist3.add("맛술");
        ArrayAdapter<String> Adapter3;
        Adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist3);

        ListView list3 = (ListView)findViewById(R.id.lv_recipe2Sauce);
        list3.setAdapter(Adapter3);

        ArrayList<String> arraylist4 = new ArrayList<String>();
        arraylist4.add("6컵");
        arraylist4.add("2스푼");
        arraylist4.add("1스푼");
        ArrayAdapter<String> Adapter4;
        Adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist4);

        ListView list4 = (ListView)findViewById(R.id.lv_recipe2SauceAmount);
        list4.setAdapter(Adapter4);


    }
}