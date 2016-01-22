package com.example.jasvir.spendingtracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class MyFragment_Two extends Fragment implements AdapterView.OnItemClickListener ,AdapterView.OnItemLongClickListener,MyDialogFragment.DialogClickListener {

    DatabaseHelper dbhDb;
    Button buttonView;
    public ListView list;
    CustomAdapter1 adapter1;
    public  ArrayList<FragmentListModel> Data = new ArrayList<FragmentListModel>();
    public MyFragment_Two() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View showview = inflater.inflate(R.layout.my_fragment__two, container, false);
        list=(ListView)showview.findViewById(R.id.list_daily_Entry);
        // Inflate the layout for this fragment
        return showview;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.e("Tab1Activity", "inside Fragment onCreate");
        dbhDb = new DatabaseHelper(MainActivity.context);
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Log.e("Tab1Activity", "inside Fragment onStart");
        getData();
        adapter1=new CustomAdapter1(getActivity().getBaseContext(), Data);
        list.setAdapter(adapter1);
        list.setOnItemClickListener(this);
        registerForContextMenu(list);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        Log.e("Tab1Activity", "inside onCreateContextMenu");

        if (v.getId()==R.id.list_daily_Entry)
        {
            Log.e("Tab1Activity", "inside onCreateContextMenu list");
            menu.setHeaderTitle("Select Action");
            String[] menuItems = getResources().getStringArray(R.array.menu);
            for (int i = 0; i<menuItems.length; i++) {
                menu.add(Menu.NONE, i, i, menuItems[i]);
            }
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int ContextmenuItemIndex = item.getItemId();

        int ListItemIndex = (int)info.id;
        Log.e("Tab1Activity", "menuitemidex"+ContextmenuItemIndex);
        Log.e("Tab1Activity", "actualitemidex"+ListItemIndex);
        String[] menuItems = getResources().getStringArray(R.array.menu);
        String menuItemName = menuItems[ContextmenuItemIndex];
        switch(ContextmenuItemIndex)
        {
            case 0:
            case 1:

                FragmentListModel f1 = (FragmentListModel) adapter1.getItem(ListItemIndex);

                DialogFragment newFragment = MyDialogFragment.newInstance(Integer.parseInt(f1.getid()));
                newFragment.setTargetFragment(this, 0);
                newFragment.show(getFragmentManager(), "dialog");

                break;
        }
        return true;
    }




    public void alertMessage(String title, String message){
        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setCancelable(true);
        alert.setTitle(title);
        alert.setMessage(message);
        alert.show();

    }


    public void getData()
    {

        Data.clear();
        Cursor output = dbhDb.getData();
        if (output.getCount() == 0) {
            alertMessage("Error", "No Data Found");
            return;
        }
        while (output.moveToNext()) {

            final FragmentListModel sched = new FragmentListModel();
            sched.setId(output.getString(0));
            sched.setCategory(output.getString(1));
            sched.setDate(output.getString(2));
            sched.setAmount(output.getString(3));
            Data.add(sched);
        }
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
        return false;
    }

    @Override
    public void onYesClick(int TxnID) {

       int count =  dbhDb.deleteDailyDetails(TxnID);
        if(count>0)
        {
            getData();
            adapter1.notifyDataSetChanged();
        }


    }

    @Override
    public void onNoClick() {

    }
}

class MyDialogFragment extends DialogFragment {

    private DialogClickListener callback;

    public interface DialogClickListener {
        public void onYesClick(int TxnID);
        public void onNoClick();
    }


    public static MyDialogFragment newInstance(int TxnID) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();
        args.putInt("TXNID", TxnID);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            callback = (DialogClickListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement DialogClickListener interface");
        }
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final int TxnID  = getArguments().getInt("TXNID");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Do You Want to Delete that Transaction?????")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.e("Tab1Activity","inside onclick");
                        callback.onYesClick(TxnID);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                callback.onNoClick();
            }
        });
        return builder.create();
    }

}
