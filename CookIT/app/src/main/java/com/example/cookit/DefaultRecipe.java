package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class DefaultRecipe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_default_recipe);

        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = (ListView) findViewById(R.id.lv_dRecipe);
        listview.setAdapter(adapter);

        // 첫 번째 아이템 추가_차돌된장찌개
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image_recipe1),
                "차돌된장찌개", "재료 : 대패삼겹살, 무, 대파..") ;
        // 두 번째 아이템 추가_어묵탕
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image_recipe2),
                "어묵탕", "재료 : 어묵, 양파, 대파..") ;
        // 세 번째 아이템 추가_순두부찌개
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image_recipe3),
                "순두부찌개", "재료 : 순두부, 양파, 대파..") ;
        // 네 번째 아이템 추가_배추된장국
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image_recipe4),
                "배추된장국", "재료 : 배추, 대파, 소고기..") ;
        // 다섯번째 아이템 추가_고추장찌개
        adapter.addItem(ContextCompat.getDrawable(this, R.drawable.image_recipe5),
                "고추장찌개", "재료 : 돼지고기, 감자, 양파..") ;

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                // get item
                ListViewItem item = (ListViewItem) parent.getItemAtPosition(position) ;

                String titleStr = item.getTitle() ;
                String descStr = item.getDesc() ;
                Drawable iconDrawable = item.getIcon() ;

                // 리스트뷰 클릭 시 상세페이지로 이동
                if(position ==0){
                    Intent intent0 = new Intent(DefaultRecipe.this, Recipe1.class);
                    startActivity(intent0);
                }
                if(position ==1){
                    Intent intent1 = new Intent(DefaultRecipe.this, Recipe2.class);
                    startActivity(intent1);
                }
                if(position ==2){
                    Intent intent2 = new Intent(DefaultRecipe.this, Recipe3.class);
                    startActivity(intent2);
                }


            }
        }) ;


    }
}