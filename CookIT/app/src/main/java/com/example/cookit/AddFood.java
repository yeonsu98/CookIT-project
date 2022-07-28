package com.example.cookit;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;


public class AddFood extends AppCompatActivity {

    private EditText et_foodName, et_quantity;
    private TextView tv_date;
    private ToggleButton tbtn_frozen;
    private RadioGroup mRgQuantity;
    private Button btn_addDone;
    private String frozen = "냉장", date;
    private  DatePickerDialog.OnDateSetListener callbackMethod;

    private int mYear =0, mMonth=0, mDay=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        RbPreference pref = new RbPreference(this);
        String nickname = pref.getValue("nickname", null);


        Button mBtnBacktoMain = findViewById(R.id.btn_backToMain);
        mBtnBacktoMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AppMain.class);
                startActivity(intent);
                //printTable();
            }
        });

        et_foodName = findViewById(R.id.et_foodName);
        et_quantity = findViewById(R.id.et_quantity);
        tbtn_frozen = findViewById(R.id.tbtn_frozen);
        mRgQuantity = findViewById(R.id.rg_quantity);
        tv_date = findViewById(R.id.tv_date);


        // 토글 버튼 - 냉동 여부
        tbtn_frozen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    frozen = "냉동";
                }else {
                    frozen = "냉장";
                }
            }
        });

        tv_date = findViewById(R.id.tv_date);
        Calendar cal = Calendar.getInstance();
        tv_date.setText(cal.get(Calendar.YEAR) +"-"+ (cal.get(Calendar.MONTH)+1) +"-"+ cal.get(Calendar.DATE));
//
//        if(sqlDB != null){
//           printTable();}

        btn_addDone = findViewById(R.id.btn_addDone);
        btn_addDone.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {

                int id = mRgQuantity.getCheckedRadioButtonId();
                RadioButton rb = (RadioButton) findViewById(id);


                String foodName = et_foodName.getText().toString();
                String quantity = et_quantity.getText().toString();
                String rbtn_quan = rb.getText().toString();


                String ptr = "";
                if(foodName.equals(ptr) || quantity.equals(ptr)) {
                    Toast.makeText(AddFood.this,"빈 항목이 있습니다!", Toast.LENGTH_SHORT).show();
                }else{

                    Toast.makeText(getApplicationContext(),"재료가 추가되었습니다!",0).show();

                    //AppMain으로 값 보내기
                    Intent intent = new Intent(getApplicationContext(), AppMain.class);

                    intent.putExtra("nickname", nickname);
                    intent.putExtra("foodName", foodName);
                    intent.putExtra("quantity", quantity+rbtn_quan);
                    intent.putExtra("frozen", frozen);
                    intent.putExtra("date", date);

                    startActivity(intent);

                }
            }
        });
    }

    DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int yy, int mm, int dd) {
                    // Date Picker에서 선택한 날짜를 TextView에 설정
                    tv_date = findViewById(R.id.tv_date);
                    tv_date.setText(String.format("%d-%d-%d", yy,mm+1,dd));
                    date = String.format("%d-%d-%d", yy,mm+1,dd);

                }

            };

    public void mOnClick_DatePick(View view){
        // DATE Picker가 처음 떴을 때, 오늘 날짜가 보이도록 설정.
        Calendar cal = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, mDateSetListener, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE));
        // 오늘 이후만 뜨도록
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();
    }




//    public void printTable(){
//        sqlDB = myDBHelper.getReadableDatabase();
//        Cursor cursor;
//        cursor = sqlDB.rawQuery("SELECT * FROM food;",null);
//
//        String result = "";
//
//        result += "row 개수 : " + cursor.getCount() + "\n";
//        while(cursor.moveToNext()){
//            String userId = cursor.getString(0);
//            String name = cursor.getString(1);
//            String quan = cursor.getString(2);
//            String frozen = cursor.getString(3);
//            String date = cursor.getString(4);
//
//            result += userId + " " + name + " " + quan + " " + frozen + " " + date + "\n";
//        }
//        tv_result = findViewById(R.id.tv_result);
//        tv_result.setText(result);
//
//        cursor.close();
//        sqlDB.close();
//    }

//    public void showDatePicker(View view) {
//        DialogFragment newFragment = new DatePickerFragment();
//        newFragment.show(getSupportFragmentManager(),"datePicker");
//    }
//
//    public void processDatePickerResult(int year, int month, int day){
//        String month_string = Integer.toString(month+1);
//        String day_string = Integer.toString(day);
//        String year_string = Integer.toString(year);
//        dateMessage = (month_string + "/" + day_string + "/" + year_string);
//
//        tv_date = findViewById(R.id.tv_date);
//        tv_date.setText(dateMessage);
//
//    }
}
