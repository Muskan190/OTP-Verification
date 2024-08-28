package com.example.liveasy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment1;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.liveasy.Verification.PhoneFragment;

public class MainActivity extends AppCompatActivity {
    private AppCompatButton nextbtn;
    private Spinner spinner;
    private String lang[]={"English","Punjabi","Hindi","French","Spanish","Chinese"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findid();

        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,lang);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        nextbtn.setOnClickListener(view -> {
          PhoneFragment fragment=new PhoneFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.iv,fragment).addToBackStack(null).commit();
        });

    }
    private void findid() {
        spinner=findViewById(R.id.spinner1);
        nextbtn=findViewById(R.id.nextbtn);
    }
}
