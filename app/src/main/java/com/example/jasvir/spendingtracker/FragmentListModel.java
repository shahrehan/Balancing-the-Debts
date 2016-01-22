package com.example.jasvir.spendingtracker;


public class FragmentListModel {

    public String category;
    public String date;
    public String id;
    public String amount;

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getid() {
        return id;
    }

    public String getAmount() {
        return amount;
    }

    public void setCategory(String category)
    {
        this.category = category;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public void setId(String Id)
    {
        this.id = Id;
    }

    public void setAmount(String value)
    {
        this.amount = value;
    }

}
