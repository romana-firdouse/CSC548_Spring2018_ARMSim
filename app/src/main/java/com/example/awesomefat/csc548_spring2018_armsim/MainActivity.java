package com.example.awesomefat.csc548_spring2018_armsim;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    private EditText inputET;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.inputET = (EditText)this.findViewById(R.id.inputET);
    }

    public void onRegistersButtonClicked(View v)
    {
        Intent i = new Intent(this, RegisterActivity.class);
        this.startActivity(i);
    }

    public void onRamButtonClicked(View v)
    {
        Intent i = new Intent(this, RamActivity.class);
        this.startActivity(i);
    }


    private int getRegisterIndex(String str)
    {
        int num = 0;
        int len = str.length();
        int i = 0;

        while (i < len)
        {
            char ch = str.charAt(i);
            if (ch >= '0' && ch <= '9') {
                num = num * 10 + ch - '0';
            }
            i++;
        }
        return num;
    }

    private int addInstruction(String str)
    {
        int dstReg = 0, reg1 = 0, reg2 = 0;


        String array[] =  str.split(",");


        dstReg = getRegisterIndex(array[0]);
        reg1 = getRegisterIndex(array[1]);
        reg2 = getRegisterIndex(array[2]);

        if (dstReg >= CORE.maxRegisters  || reg1 >= CORE.maxRegisters   ||  reg2 >= CORE.maxRegisters )
        {

            return 0;
        }
        CORE.registers[dstReg] = "" + (Integer.parseInt(CORE.registers[reg1]) + Integer.parseInt(CORE.registers[reg2]));

        return 1;
    }

    private int addiInstruction(String str) {

        int dstReg = 0, reg1 = 0, constant = 0;


        String array[] =  str.split(",");


        dstReg = getRegisterIndex(array[0]);
        reg1 = getRegisterIndex(array[1]);
        constant = getRegisterIndex(array[2]);

        if (dstReg >= CORE.maxRegisters  || reg1 >= CORE.maxRegisters  )
        {
            return 0;
        }
        CORE.registers[dstReg] = "" + (Integer.parseInt(CORE.registers[reg1]) + constant);

        return 1;
    }

    private boolean myContains(String str, String pattern)
    {

        if (!str.contains(pattern))
            return false;
        if (str.charAt(str.indexOf(pattern) + pattern.length()) != ' ')
        {
            return false;
        }
        return true;
    }

    private int parsefunction(String str)
    {
        String uStr = str.toUpperCase();
        int i = 0;

        if (myContains(uStr, "ADDI"))
        {
            return addiInstruction(uStr);
        }
        if (myContains(uStr, "ADD"))
        {
            return addInstruction(uStr);
        }

        return 0;

    }

    public void onRunButtonClicked(View v)
    {

        if (this.inputET.getText().toString().equals(""))
        {
            Toast.makeText(MainActivity.this, "Enter an instruction",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String str = inputET.getText().toString();

        int ret =  parsefunction(str);

        if (ret == 1)
        {
            Toast.makeText(MainActivity.this, "instruction evaluated",
                    Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(MainActivity.this, "invalid instruction",
                    Toast.LENGTH_SHORT).show();
        }


    }
}