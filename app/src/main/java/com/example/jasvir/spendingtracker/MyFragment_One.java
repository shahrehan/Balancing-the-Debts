package com.example.jasvir.spendingtracker;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


@SuppressLint("NewApi")
public class MyFragment_One extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    DatabaseHelper dhDb;
    static EditText date,amount;
    Button buttonAdd;
    Spinner category;
    String selected_name;
    static  String selectedDate;
    String categorys[] = {"Food","Clothes", "Grocery","Entertainment","Travel"};
    List<String> wordList = new ArrayList<String>(Arrays.asList(categorys));
    //List<String> wordList = Arrays.<String>asList(categorys);
    //ArrayList<String> al = new ArrayList<String>(wordList);
    public  MyFragment_One(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.my_fragment_one, container, false);

        dhDb = new DatabaseHelper(MainActivity.context);

        category = (Spinner)view.findViewById(R.id.enterCategory);
        date = (EditText)view.findViewById(R.id.enterDate);
        amount = (EditText)view.findViewById(R.id.enterAmount);
        buttonAdd = (Button)view.findViewById(R.id.addButton);

        date.setOnClickListener(this);

        ArrayAdapter adapter = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,wordList);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        category.setAdapter(adapter);
        category.setOnItemSelectedListener(this);

        AddData();

        return view;
    }

    public void AddData(){

        buttonAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

              boolean datainserted = dhDb.insertData(selected_name, selectedDate, amount.getText().toString());
                        if (datainserted = true) {
                            Toast.makeText(getContext(), "Data is Inserted", Toast.LENGTH_LONG).show();
                            //category.setText("");
                            date.setText("");
                            amount.setText("");
                        }
                        else
                           Toast.makeText(getContext(),"Data is not Inserted",Toast.LENGTH_LONG).show();

                    }
                }
        );
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        selected_name = (String) adapterView.getItemAtPosition(i);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onClick(View view) {

        DialogFragment newFragment = new DatePickerFragment();
        //newFragment.show(this(), "datePicker");
        newFragment.show(getFragmentManager(),"datePicker");
    }


    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        int mYear,mMonth,mDay;

        public DatePickerFragment(){
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker arg0, int year, int month, int day) {
            // TODO Auto-generated method stub

            mYear=year;
            mMonth=month+1;
            mDay=day;
            Log.e("Daily_spending", "Time selected by user " + "year=" + mYear + "Month=" + mMonth + "Day="
                    + mDay);
            selectedDate = mDay+"/"+mMonth+"/"+mYear;
            date.setText(selectedDate);
            Toast.makeText(getContext(),"Selected Date="+selectedDate, Toast.LENGTH_LONG).show();
        }
    }
}