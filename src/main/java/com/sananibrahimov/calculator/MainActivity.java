package com.sananibrahimov.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Script;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sananibrahimov.calculator.databinding.ActivityMainBinding;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    EditText workings;
    TextView result;
    String working="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        View view=binding.getRoot();
        setContentView(view);
        workings=binding.editTextTextPersonName;
        result=binding.textView;

    }
    public void equal(View view){
        Double result2=null;
        ScriptEngine scriptEngine=new ScriptEngineManager().getEngineByName("rhino");
        try {
            result2= (Double) scriptEngine.eval(working);
        }catch (Exception e){

        }
        if (result2!=null){
            result.setText(String.valueOf(result2));

        }
    }
    private void setWorkings(String givenvalue){
        working=working+givenvalue;
        workings.setText(working);


    }
    public void add(View view){
       setWorkings("+");
    }
    public void multiply(View view){
        setWorkings("*");
    }
    public void substract(View view){
        setWorkings("-");
    }
    public void dvide(View view){
setWorkings("/");
    }
    public void nine(View view){
      setWorkings("9");
        System.out.println(working);
    }
    public void eigth(View view){
        setWorkings("8");
    }
    public void seven(View view){

    }
    public void six(View view){

    }
    public void five(View view){

    }
    public void fackt(View view ){
        setWorkings("!");
    }
    public void four(View view){

    }
    public void three(View view){

    }
    public void two(View view){

    }
    public void one(View view){

    }
    public void zero(View view){
        System.out.println(working);
    }


}