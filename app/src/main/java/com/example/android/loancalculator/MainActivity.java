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
                        if(!(downPayment.getText() + "").equals("") && !(aprCost.getText() + "").equals("")) {
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
                        if(!(carCost.getText() + "").equals("") && !(aprCost.getText() + "").equals("")) {
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
                        if(!(downPayment.getText() + "").equals("") && !(carCost.getText() + "").equals("")) {
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

    private void calculatePayment() {
        // P = mr*L/(1-(1+mr)^-n) mr = apr, L = loanPayment, n = # of payments
        Double m = Double.parseDouble(aprCost.getText() + "") / 1200;
        double dp = Double.parseDouble(downPayment.getText() + ""); // down payment
        Double L = Double.parseDouble(carCost.getText() + "");
        int n = Integer.parseInt(seek.getProgress() + "");

        if(radioLease.isChecked()) {
            n = 36;
            L /= 3;
        }

        Double p = (m * (L - dp)) / (1 - Math.pow(1 + m, -n));
        monthlyPayment.setText("$" + String.format("%.2f", p));
    }
}
