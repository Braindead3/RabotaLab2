package com.company;

public class Customer
{
    public int id;
    public String customerLogin;
    public String customerPassword;
    public String customerEmail;

    @Override
    public String toString()
    {
        return ("id:"+id+
                "\nCustomer login:"+customerLogin+
                "\nCustomer password"+customerPassword+
                "\nCustomer email"+ customerEmail);
    }
}
