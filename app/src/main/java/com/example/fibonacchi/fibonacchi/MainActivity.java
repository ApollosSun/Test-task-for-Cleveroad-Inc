package com.example.fibonacchi.fibonacchi;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerOptions;
    private Spinner spinnerCalcType;
    private ArrayAdapter<CharSequence> adapterOptions;
    private ArrayAdapter<CharSequence> adapterCalc;
    private FragmentTransaction transaction;
    private FragmentManager manager;
    private FragmentEditText fragmentEditText;
    private TextView resultTextView;
    private int indexOption;
    private int indexFunc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultTextView = findViewById(R.id.result);

        initSpinnerOptions();
        initSpinnerCalc();

        fragmentEditText = new FragmentEditText();
        manager = getSupportFragmentManager();
    }

    public void initSpinnerOptions (){

        spinnerOptions = findViewById(R.id.spinner_output_options);
        adapterOptions = ArrayAdapter.createFromResource(this, R.array.output_options,
                android.R.layout.simple_spinner_item);
        adapterOptions.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapterOptions);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                transaction = manager.beginTransaction();

                switch (position){
                    case 0:
                        indexOption = 1;

                        if (manager.findFragmentByTag(fragmentEditText.TAG) != null)
                            transaction.remove(fragmentEditText);
                        resultTextView.setText("");
                        break;

                    case 1:
                        indexOption = 2;

                        if(manager.findFragmentByTag(fragmentEditText.TAG) == null)
                            transaction.add(R.id.edit_text, fragmentEditText, fragmentEditText.TAG);
                        resultTextView.setText("");
                        break;
                }
                transaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void initSpinnerCalc (){

        spinnerCalcType = findViewById(R.id.spinner_calc_type);
        adapterCalc = ArrayAdapter.createFromResource(this, R.array.calc_type,
                android.R.layout.simple_spinner_item);
        adapterCalc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCalcType.setAdapter(adapterCalc);

        spinnerCalcType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                switch (position){
                    case 0:
                        indexFunc = 1;
                        resultTextView.setText("");
                        break;

                    case 1:
                        indexFunc = 2;
                        resultTextView.setText("");
                        break;

                    case 2:
                        indexFunc = 3;
                        resultTextView.setText("");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void calc (View view){

        long result = -1;
        int number = checkNumber();
        String resultStr="";

        if (indexOption == 1){

            if (indexFunc == 1){

                for (int i = 0; i < 30; i++){
                    result = recursion(i);
                    if (i < 29)
                        resultStr += Long.toString(result) + ", ";
                    else
                        resultStr += Long.toString(result) + ".";
                }

            } else if (indexFunc == 2){

                for (int i = 0; i < 30; i++){
                    result = linear(i);
                    if (i < 29)
                        resultStr += Long.toString(result) + ", ";
                    else
                        resultStr += Long.toString(result) + ".";
                }

            } else if (indexFunc == 3) {

                for (int i = 0; i < 30; i++){
                    result = binesFormula(i);
                    if (i < 29)
                        resultStr += Long.toString(result) + ", ";
                    else
                        resultStr += Long.toString(result) + ".";
                }
            }
        } else if (indexOption == 2){

            if (number != 0) {

                if (indexFunc == 1){

                    result = recursion(number);

                } else if (indexFunc == 2){

                    result = linear(number);

                } else if (indexFunc == 3) {

                    result = binesFormula(number);
                }
            }
            resultStr = Long.toString(result);
        }

        if (result != -1) {
            resultTextView.setText(resultStr);
        } else
            resultTextView.setText("");
    }

    private int checkNumber() {

        int number = 0;

        if (manager.findFragmentByTag(fragmentEditText.TAG) != null) {

            EditText editTextNum = (EditText) findViewById(R.id.number);
            String num = editTextNum.getText().toString();
            if (num.matches(""))
                Toast.makeText(this, "Введите число", Toast.LENGTH_LONG).show();
            else {

                number = Integer.parseInt(editTextNum.getText().toString());

                if (number < 1) {
                    Toast.makeText(this, "Вы ввели слишком маленькое число", Toast.LENGTH_LONG).show();
                    return 0;
                } else if (number > 333) {
                    Toast.makeText(this, "Вы ввели слишком большое число", Toast.LENGTH_LONG).show();
                    return 0;
                } else
                    return number;
            }
        }

        return 0;
    }

    private int recursion (int num){

        if (num == 0)
            return 0;
        else if (num == 1)
            return 1;
        else
            return recursion(num - 2) + recursion(num - 1);

    }

    private int linear (int num){

        if (num == 0)
            return 0;
        else if (num <= 2)
            return 1;
        else {
            int num1 = 1;
            int num2 = 1;
            int res = 0;

            for (int i = 2; i < num; i++){
                res = num1 + num2;
                num1 = num2;
                num2 = res;
            }
            return res;
        }
    }

    private long binesFormula (int num){

        double div = Math.pow(5, 0.5);
        double leftPart = (1 + div) / 2;
        double rightPart = (1 - div) / 2;

        return Math.round((Math.pow(leftPart, num) - Math.pow(rightPart, num))/div);
    }

}
