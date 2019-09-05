package com.trangdv.userinterface12;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static final String KEY_RESULT = "key result";
    public static final int REQUEST_CODE = 2019;

    private EditText edt_numberA;
    private EditText edt_numberB;

    private String textNumberA;
    private String textNumberB;
    private String textResult;
    private Button btn_send;
    private RecyclerView recyclerView;

    RvAdapter rvAdapter;

    private List<String> results = new ArrayList<String>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

    }

    private void sendNumber() {
        Intent intentSend = new Intent(this, CalculationsActivity.class);
        Bundle bundleSend = new Bundle();

        bundleSend.putString(CalculationsActivity.KEY_A, String.valueOf(edt_numberA.getText()));
        bundleSend.putString(CalculationsActivity.KEY_B, String.valueOf(edt_numberB.getText()));

        intentSend.putExtras(bundleSend);
        startActivityForResult(intentSend, REQUEST_CODE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            textResult = data.getExtras().getString(KEY_RESULT, "");
            results.add(textResult);
            rvAdapter = new RvAdapter(results);
            recyclerView.setAdapter(rvAdapter);
        }
    }


    private void initViews() {
        edt_numberA = findViewById(R.id.number_a);
        edt_numberB = findViewById(R.id.number_b);
        btn_send = findViewById(R.id.send_btn);

        recyclerView = findViewById(R.id.rv_result);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        setBtn_send(btn_send);
    }

    private void getTextfromEdt(EditText edt_numberA, EditText edt_numberB) {
        textNumberA = edt_numberA.getText().toString();
        textNumberB = edt_numberB.getText().toString();
    }

    public void setBtn_send(Button btn_send) {
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getTextfromEdt(edt_numberA, edt_numberB);
                if (textNumberA.equals("")==false && textNumberB.equals("")==false) {
                    sendNumber();
                }
            }
        });
    }


    //
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}
