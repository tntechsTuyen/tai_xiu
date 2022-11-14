package com.mnt.tx;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.mnt.tx.adapter.TableAdapter;
import com.mnt.tx.data.Point;
import com.mnt.tx.data.Table;
import com.mnt.tx.widget.IconView;
import com.mnt.tx.widget.SicboLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@SuppressLint({"NewApi","DefaultLocale"})
public class MainActivity extends AppCompatActivity {

    private SicboLayout sl, sl1;
    private TextView btnAdd, btnBack, btnClear, btnAddTable, btnClearTable;
    private IconView btn1, btn2, btn3, btn4, btn5, btn6;
    private List<IconView> btns = new ArrayList<>();
    private EditText etVal;
    private TextView tvValNumber, tvValTx, tvValLh;
    private ListView lvTable;
    private TableAdapter adapter;
    private List<Table> tables;
    private Integer tableIndex = 0;
    Point p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        actionView();
    }

    private void initView(){
        tables = new ArrayList<>();
        lvTable = findViewById(R.id.lv_table);
        adapter = new TableAdapter(tables, this);
        lvTable.setAdapter(adapter);
        initData();
        sl = findViewById(R.id.sb_layout);
        sl1 = findViewById(R.id.sb_layout_1);
        btnAdd = findViewById(R.id.btn_add);
        btnClear = findViewById(R.id.btn_clear);
        btnBack = findViewById(R.id.btn_back);
        btnClearTable = findViewById(R.id.btn_clear_table);
        btn1 = findViewById(R.id.btn_1);
        btn2 = findViewById(R.id.btn_2);
        btn3 = findViewById(R.id.btn_3);
        btn4 = findViewById(R.id.btn_4);
        btn5 = findViewById(R.id.btn_5);
        btn6 = findViewById(R.id.btn_6);
        btns = Arrays.asList(btn1, btn2, btn3, btn4, btn5, btn6);
        btnAddTable = findViewById(R.id.btn_add_table);
        etVal = findViewById(R.id.et_val_1);
        tvValNumber = findViewById(R.id.tv_value_number);
        tvValTx = findViewById(R.id.tv_val_tx);
        tvValLh = findViewById(R.id.tv_val_lh);
//        tvCurrentNumber = findViewById(R.id.tv_current_number);
        sl.initData();
        sl1.initData();
    }

    private void initData(){
        tables.add(new Table("Bàn 1", true));
        adapter.notifyDataSetChanged();
    }

    private void actionView(){
        IntStream.range(0, btns.size()).forEach((i) -> {
            IconView btn = btns.get(i);
            int val = i + 1;
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String currTxt = etVal.getText().toString();
                    if(currTxt.trim().length() >= 5){
                        return;
                    }

                    if(currTxt.trim().length() == 0){
                        etVal.setText(String.valueOf(val));
                    }else{
                        etVal.setText(String.format("%s+%d", currTxt, val));
                    }
                    if(etVal.getText().toString().trim().length() == 5){
                        String[] v = currTxt.split("\\+");
                        p = new Point(new Point.Data(Integer.valueOf(v[0]), Integer.valueOf(v[1]), val));
                        tvValNumber.setText(String.format("%d",p.getData().getTotal()));
                        tvValTx.setText(String.format("%s",p.getTitle(1)));
                        tvValLh.setText(String.format("%s",p.getTitle(2)));
                    }
                    clearBtn();
                    btn.setTextColor(getResources().getColor(R.color.red));
                }
            });
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(p != null){
                    tables.get(tableIndex).getData().add(new Point(p.getData()));
                    tables.get(tableIndex).getData1().add(new Point(p.getData()));
                    sl.buildData(tables.get(tableIndex).getData());
                    sl1.buildData(tables.get(tableIndex).getData1());
                    clear();
                }
            }
        });

        btnAddTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tables.add(new Table(String.format("Bàn %d", tables.size() + 1)));
                adapter.notifyDataSetChanged();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clear();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tables.get(tableIndex).getData().size() > 0){
                    tables.get(tableIndex).getData().remove(tables.get(tableIndex).getData().size() - 1);
                    tables.get(tableIndex).getData1().remove(tables.get(tableIndex).getData1().size() - 1);
                    sl.buildData(tables.get(tableIndex).getData());
                    sl1.buildData(tables.get(tableIndex).getData1());
                }
            }
        });

        btnClearTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tables.get(tableIndex).getData().clear();
                tables.get(tableIndex).getData1().clear();
                sl.buildData(tables.get(tableIndex).getData());
                sl1.buildData(tables.get(tableIndex).getData1());
            }
        });
    }

    private void clear(){
        this.p = null;
        tvValNumber.setText("");
        tvValTx.setText("");
        tvValLh.setText("");
        etVal.setText("");
        clearBtn();
    }

    private void clearBtn(){
        btns.forEach((btn) -> {
            btn.setTextColor(getResources().getColor(R.color.white));
        });
    }

    public void changeTableIndex(Integer index){
        tables.get(this.tableIndex).setData(sl.getData());
        tables.get(this.tableIndex).setData1(sl1.getData());
        this.tableIndex = index;
        sl.buildData(tables.get(this.tableIndex).getData());
        sl1.buildData(tables.get(this.tableIndex).getData1());
    }
}