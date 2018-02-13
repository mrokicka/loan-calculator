package com.example.android.loancalculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private EditText carCost;
    private EditText downPayment;
    private EditText aprCost;
    private RadioButton radioLoan;
    private RadioButton radioLease;
    private TextView monthlyPayment;
    private TextView numMonths;
    private SeekBar seek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        carCost = findViewById(R.id.carCost);
        carCost.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")){
                            calculatePayment();
                        }
                        return false;
                    }
                }
        );

        downPayment = findViewById(R.id.downPayment);
        downPayment.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")){
                            calculatePayment();
                        }
                        return false;
                    }
                }
        );

        aprCost = findViewById(R.id.aprCost);
        aprCost.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")){
                            calculatePayment();
                        }
                        return false;
                    }
                }
        );
        radioLoan = findViewById(R.id.radioLoan);
        radioLoan.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")){
                            calculatePayment();
                        }
                    }
                }
        );

        radioLease = findViewById(R.id.radioLease);
        radioLease.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")){
                            calculatePayment();
                        }
                    }
                }
        );

        monthlyPayment = findViewById(R.id.monthlyPayment);
        if(savedInstanceState != null) {
            monthlyPayment.setText(savedInstanceState.getString("MONTHLY"));
        }

        numMonths = findViewById(R.id.numMonths);
        seek = findViewById(R.id.seekBar);
        seek.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        numMonths.setText(progress + "");
                        if(!(aprCost.getText() + "").equals("") && !(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")) {
                            calculatePayment();
                        }
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {

                    }
                }
        );
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("MONTHLY", monthlyPayment.getText().toString());

    }

    private void calculatePayment() {
        // P = mr*L/(1-(1+mr)^-n) mr = apr, L = loanPayment, n = # of payments
        Double m = Double.parseDouble(aprCost.getText() + "") / 1200;
        double dp = Double.parseDouble(downPayment.getText() + ""); // down payment
        Double L = Double.parseDouble(carCost.getText() + "");
        double total = L - dp;
        int n = Integer.parseInt(seek.getProgress() + "");



        if(radioLease.isChecked()) {
            n = 36;
            L /= 3;
        }

        if(m > 0 && total >= 0) {
            Double p = (m * (total)) / (1 - Math.pow(1 + m, -n));
            monthlyPayment.setText("$" + String.format("%.2f", p));
        } else if (m <= 0){
            Toast.makeText(this, "Apr must be bigger than 0!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "The down payment can't be bigger than the cost!", Toast.LENGTH_LONG).show();
        }


    }
}
