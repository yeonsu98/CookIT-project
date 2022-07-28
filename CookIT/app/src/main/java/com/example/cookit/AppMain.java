package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class AppMain extends AppCompatActivity {

    private ImageView iv_myprofile, iv_myprofile2;
    private TextView tv_mynickname, tv_mynickname2;
    private String nickname;

    //FoodDatabaseHelper dbHelper;
   //ArrayList<ArrayList<String>> foodList = new ArrayList<ArrayList<String>>();
    public static myDBHelper myDBHelper;
    public static SQLiteDatabase sqlDB;

    ListView listView;
    Cursor cursor;
    ArrayList<String> data;
    String[] result;
    Integer[] alertDate;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_main);

        myDBHelper = new myDBHelper(AppMain.this);
        sqlDB = myDBHelper.getWritableDatabase();

//        myDBHelper.onUpgrade(sqlDB, 1, 2);
//        sqlDB.execSQL("DROP TABLE IF EXISTS food");
//        myDBHelper.onCreate(sqlDB);

        RbPreference pref = new RbPreference(this);

        nickname = pref.getValue("nickname", null);
        tv_mynickname = findViewById(R.id.tv_mynickname);
        tv_mynickname2 = findViewById(R.id.tv_mynickname2);
        tv_mynickname.setText(nickname);
        tv_mynickname2.setText(nickname + "의 냉장고");

        String profileImg = pref.getValue("profileImg", null);
        iv_myprofile = findViewById(R.id.iv_myprofile);
        //iv_myprofile2 = findViewById(R.id.iv_myprofile2);
        Glide.with(this).load(profileImg).into(iv_myprofile);
        //iv_myprofile2.setImageResource(R.drawable.kakao_default_profile_image);

        //재료리스트 출력
        listView = (ListView)findViewById(R.id.lv_foodList);
        printTable();

        //재료 삭제
        //sqlDelete(sqlDB, "사과");


        //addFood에서 값 보낸거 가져와서 DB에 insert하기
        Intent intent = getIntent();
        processedIntent(intent);

        
        Button mBtnInvite = findViewById(R.id.btn_invite);
        mBtnInvite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MemberJoin.class);
                startActivity(intent);
            }
        });

        ImageButton mBtnSetting = findViewById(R.id.btn_setting);
        mBtnSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Setting.class);
                startActivity(intent);
            }
        });

        Button mBtnAddFood = findViewById(R.id.btn_addFood);
        mBtnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddFood.class);
                startActivity(intent);
            }
        });


        ImageButton mBtnRecipe = findViewById(R.id.btn_recipe);
        mBtnRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DefaultRecipe.class);
                startActivity(intent);
            }
        });
    }

    public class myDBHelper extends SQLiteOpenHelper {
        public myDBHelper(Context context) {
            super(context, "foodDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            db = openOrCreateDatabase("foodDB", MODE_PRIVATE, null);
            db.execSQL("CREATE TABLE food ( userId CHAR(30), foodName CHAR(20), quantity CHAR(10), frozen CHAR(3), date CHAR(10));");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS food");
            onCreate(db);

        }
    }

    public void printTable(){
        try{
            sqlDB = myDBHelper.getReadableDatabase();
            cursor = sqlDB.rawQuery("SELECT * FROM food;",null);

            int count = cursor.getCount();
            result = new String[count];
            data = new ArrayList<>(count);

            for(int i=0;i<count;i++){

                cursor.moveToNext();
                String userId = cursor.getString(0);
                //int foodId = cursor.getInt(1);
                String name = cursor.getString(1);
                String quan = cursor.getString(2);
                String frozen = cursor.getString(3);
                String date = cursor.getString(4);

                //유통기한 임박 시 추가
                SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd");
                Date today = new Date();
                Date tomorrow = new Date(today.getTime()+(long)(1000* 60 * 60 * 24 ));
                Date to = fm.parse(date);

                if(to.before(today)){
                    date += " 유통기한 지남!";
                }else if(to.before(tomorrow)){
                    date += " 유통기한 임박!";
                }

                result[i] = name + "   " + quan + "   " + frozen + "  " + date;
                data.add(result[i]);
            }
            System.out.println("select Ok!");
            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, data);

            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view,
                                               int position, long id) {
                    data.remove(position);
                    adapter.notifyDataSetChanged();
                    Toast.makeText(getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_SHORT).show();
                    // 이벤트 처리 종료 , 여기만 리스너 적용시키고 싶으면 true , 아니면 false
                    return true;
                }
            });
            listView.setAdapter(adapter);


            //listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            //cursor.close();
        }catch (Exception e){
            System.out.println("select Error: " + e);
        }
        //sqlDB.close();
    }


    private void processedIntent(Intent intent){
        if(!TextUtils.isEmpty(intent.getStringExtra("foodName"))) {
            String foodName = intent.getExtras().getString("foodName");
            String quantity = intent.getExtras().getString("quantity");
            String frozen = intent.getExtras().getString("frozen");
            String date = intent.getExtras().getString("date");
            //int foodId = 0;

            sqlDB.execSQL("INSERT INTO food VALUES ( '" + nickname + "' , '" +foodName + "' , '" + quantity + "', '" + frozen + "', '" + date + "');");
        }
        printTable();
    }

    private void sqlDelete(SQLiteDatabase sqlDB, String foodName){
        String sql = "Delete from food where foodName='" + foodName + "';";
        sqlDB.execSQL(sql);
     }




}