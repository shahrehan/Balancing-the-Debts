package com.example.jasvir.spendingtracker;

/**
 * Created by g1.sachin on 11/26/2015.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter1 extends BaseAdapter {

    private Context activity;
    ArrayList<FragmentListModel> data;
    FragmentListModel lm ;
    private static LayoutInflater inflater=null;
    public CustomAdapter1(Context context, ArrayList<FragmentListModel> d) {

        activity = context;
        data=d;
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    public int getCount()
    {
        //Log.e("sachin","Data size==="+data.size());
        return data.size();
    }

    public Object getItem(int position) {
        //Log.e("sachin",""+data.get(position));
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        public TextView Id;
        public TextView Category;
        public TextView Date;
        public TextView Amount;
    }

    /*********** Depends upon data size called for each row , Create each ListView row ***********/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        ViewHolder holder=new ViewHolder();;

        if(convertView==null){

            vi = inflater.inflate(R.layout.fragment_tabitem, null);
            holder.Id = (TextView)vi.findViewById(R.id.id_value);
            holder.Category = (TextView)vi.findViewById(R.id.categoryValue);
            holder.Date = (TextView)vi.findViewById(R.id.date_value);
            holder.Amount = (TextView)vi.findViewById(R.id.amountValue);
            vi.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)vi.getTag();
        }

        lm=null;
        lm = (FragmentListModel) data.get(position);

        holder.Id.setText(lm.getid().toString());
        holder.Category.setText(lm.getCategory().toString());
        holder.Date.setText(lm.getDate().toString());
        holder.Amount.setText(lm.getAmount());

        return vi;
    }
}