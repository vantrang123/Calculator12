package com.trangdv.userinterface12;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class CalculationsActivity extends AppCompatActivity {

    public static final String KEY_A = "number a";
    public static final String KEY_B = "number b";


    private Button btn_add;
    private Button btn_sub;
    private Button btn_mul;
    private Button btn_div;
    private Button btn_send_result;
    private TextView tv_numberA;
    private TextView tv_numberB;
    private TextView tv_result;

    private String numberA;
    private String numberB;


    private String math_operations;
    private double value = 0;
    private Expression expression;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculations);
        getSupportActionBar().setTitle("CaculatorActivity");
        initViews();
        clickButton();

        getNumber();


    }

    private void sendResult() {
        Intent intentSend = new Intent(this, MainActivity.class);
        Bundle bundleSend = new Bundle();
        bundleSend.putString(MainActivity.KEY_RESULT, tv_result.getText().toString());
        intentSend.putExtras(bundleSend);
        //startActivityForResult(intentSend, REQUEST_CODE);
        setResult(RESULT_OK, intentSend);
        finish();
    }

    private void getNumber() {
        Intent intentGet = getIntent();
        if (intentGet != null) {
            Bundle bundleGet = intentGet.getExtras();
            if (bundleGet != null) {
                numberA = bundleGet.getString(KEY_A, "");
                numberB = bundleGet.getString(KEY_B, "");

                tv_numberA.setText("Number A " + numberA);
                tv_numberB.setText("Number B " + numberB);
            }
        }
    }

    private void initViews() {

        tv_numberA = findViewById(R.id.tvNumberA);
        tv_numberB = findViewById(R.id.tvNumberB);
        btn_add = findViewById(R.id.add_btn);
        btn_sub = findViewById(R.id.sub_btn);
        btn_mul = findViewById(R.id.mul_btn);
        btn_div = findViewById(R.id.div_btn);
        btn_send_result = findViewById(R.id.send_resutl_btn);
        tv_result = findViewById(R.id.tv_result);

    }

    private void clickButton() {

        setBtn_add(btn_add);
        setBtn_sub(btn_sub);
        setBtn_mul(btn_mul);
        setBtn_div(btn_div);
    }

    public void setBtn_add(Button btn_add) {
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations("+");
            }
        });
    }

    public void setBtn_sub(Button btn_sub) {
        btn_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations("-");
            }
        });
    }

    public void setBtn_mul(Button btn_mul) {
        btn_mul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations("*");
            }
        });
    }

    public void setBtn_div(Button btn_div) {
        btn_div.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculations("/");
            }
        });
    }

    public void setBtn_send_result(Button btn_send_result) {

        btn_send_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendResult();
            }
        });
    }


    public void calculations(String operation) {
        setBtn_send_result(btn_send_result);

        math_operations = numberA + " " + operation + " " + numberB;
        expression = new ExpressionBuilder(math_operations).build();
        try {
            value = expression.evaluate();
            tv_result.setText(math_operations + " = " + Double.toString(value));

        }catch (ArithmeticException e) {
            tv_result.setText(math_operations + " = ∞");
        }
    }
}
