package com.mnt.tx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mnt.tx.data.Point;
import com.mnt.tx.widget.SicboLayout;

import java.util.Arrays;
import java.util.List;

@SuppressLint("NewApi")
public class MainActivity extends AppCompatActivity {

    private SicboLayout sl, sl1;
    private Button btnAdd, btnClear;
    private Button btn1, btn2, btn3, btn4, btn5, btn6;
    private EditText etVal;
    private TextView tvValNumber, tvValTitle;
    Point p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        actionView();
    }

    private void initView(){

        sl = findViewById(R.id.sb_layout);
        sl1 = findViewById(R.id.sb_layout_1);
        btnAdd = findViewById(R.id.btn_add);
        btnClear = findViewById(R.id.btn_delete);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        etVal = findViewById(R.id.et_val_1);
        tvValNumber = findViewById(R.id.tv_value_number);
        tvValTitle = findViewById(R.id.tv_value_title);
        sl.initData();
        sl1.initData();
    }

    private void actionView(){
        List<Button> btns = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6);
        btns.forEach((btn) -> {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currTxt = etVal.getText().toString();
                    if(currTxt.trim().length() >= 5){
                        Toast.makeText(MainActivity.this, "Số lượng đã đủ", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(currTxt.trim().length() == 0){
                        etVal.setText(btn.getText().toString());
                    }else{
                        etVal.setText(String.format("%s+%s", currTxt, btn.getText().toString()));
                    }
                    if(etVal.getText().toString().trim().length() == 5){
                        String[] v = currTxt.split("\\+");
                        p = new Point(new Point.Data(Integer.valueOf(v[0]), Integer.valueOf(v[1]), Integer.valueOf(btn.getText().toString())));
                        tvValNumber.setText(String.format(" = %d", p.getData().getTotal()));
                        tvValTitle.setText(String.format("(%s)",p.getTitle()));
                    }
                }
            });
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sl.addValueItem(p);
                Toast.makeText(MainActivity.this, p.getTitle(), Toast.LENGTH_SHORT).show();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}