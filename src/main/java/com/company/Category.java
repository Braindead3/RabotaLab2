package com.company;

public class Category
{
    public int id;
    public String catergoryName;

    @Override
    public String toString(){
        return "Category ID:"+id+
               "\nCategory name:"+catergoryName;
    }
}
