package com.example.jasvir.spendingtracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class MyFragment_Three extends Fragment implements View.OnClickListener {

    Button submit;
    DatabaseHelper dbhDb;
    EditText entercategory,enterdate,enteramount;
    int Eamount;
    public MyFragment_Three() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbhDb = new DatabaseHelper(MainActivity.context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View showview = inflater.inflate(R.layout.my_fragment__three, container, false);

        submit = (Button) showview.findViewById(R.id.submit);
        entercategory = (EditText) showview.findViewById(R.id.enterCategory);
        enterdate = (EditText) showview.findViewById(R.id.enterDate);
        enteramount = (EditText) showview.findViewById(R.id.enterAmount);



        submit.setOnClickListener(this);
        return showview;
    }

    @Override
    public void onStart() {
        super.onStart();
        getData();
    }

    public void getData()
    {
        Eamount = dbhDb.expenseAmount();
        entercategory.setText(Integer.toString(Eamount));
    }


    @Override
    public void onClick(View view)
    {
        getData();
        int salary = Integer.parseInt(enterdate.getText().toString());
        enteramount.setText(Integer.toString(salary-Eamount));
    }

    @Override
    public void onPause() {
        super.onPause();
        entercategory.setText("");
        enterdate.setText("");
        enteramount.setText("");
    }
}