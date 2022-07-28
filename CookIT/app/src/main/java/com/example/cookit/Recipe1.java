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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.example.cookit.AppMain.sqlDB;

public class Recipe1 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe1);

        ArrayList<String> arraylist = new ArrayList<String>();
        arraylist.add("대패삼겹살");
        arraylist.add("무");
        arraylist.add("대파");
        arraylist.add("청양고추");

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

        ListView list = (ListView)findViewById(R.id.lv_recipe1Ingredients);
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
                        i = arraylist.indexOf(food);
                        if (position == i){
                            tv.setTextColor(Color.GREEN);
                        }else{
                            tv.setTextColor(Color.RED);
                        }
                    }

                return view;
            }
        };
        list.setAdapter(Adapter);

//        String result = null, result2 = null;
//        for (String food : setFoods) {
//            result += food;
//        }
//        arraylist.set(0, result);
//
//        for(int i=0;i<arraylist.size();i++){
//            result2 += arraylist.get(i);
//        }
//        arraylist.set(1, result2);
//
//        if(arraylist.get(2).equals("대파")){
//            arraylist.set(2, arraylist.get(2)+"\t 있음");
//        }else{
//            arraylist.set(2, arraylist.get(2)+" 없음");
       // }




        ArrayList<String> arraylist2 = new ArrayList<String>();
        arraylist2.add("200g");
        arraylist2.add("2컵");
        arraylist2.add("0.5개");
        arraylist2.add("2개");
        ArrayAdapter<String> Adapter2;
        Adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist2);

        ListView list2 = (ListView)findViewById(R.id.lv_recipe1IngredientsAmount);
        list2.setAdapter(Adapter2);

        ArrayList<String> arraylist3 = new ArrayList<String>();
        arraylist3.add("된장");
        arraylist3.add("고추장");
        arraylist3.add("다진마늘");
        ArrayAdapter<String> Adapter3;
        Adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist3);

        ListView list3 = (ListView)findViewById(R.id.lv_recipe1Sauce);
        list3.setAdapter(Adapter3);

        ArrayList<String> arraylist4 = new ArrayList<String>();
        arraylist4.add("3스푼");
        arraylist4.add("1스푼");
        arraylist4.add("1스푼");
        ArrayAdapter<String> Adapter4;
        Adapter4 = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arraylist4);

        ListView list4 = (ListView)findViewById(R.id.lv_recipe1SauceAmount);
        list4.setAdapter(Adapter4);

    }
}
