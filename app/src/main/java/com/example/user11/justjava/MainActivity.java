package com.example.user11.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void submitOrder(View v)
    {
        EditText edtName = (EditText) findViewById(R.id.name_field);
       String name = edtName.getText().toString();


        CheckBox whippedCreamChk = (CheckBox) findViewById(R.id.whipped_cream_chk);
        boolean hasWhippedCream = whippedCreamChk.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_chk);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream,hasChocolate);

        String priceMessage = createOrderSummary(name,price,hasWhippedCream,hasChocolate);


        Intent intent  = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Coffee order for" +
                " " + name);
        intent.putExtra(Intent.EXTRA_TEXT,priceMessage);

        if(intent.resolveActivity(getPackageManager())!= null)
        {
            startActivity(intent);
        }
        displayMessage(priceMessage);




    }
    private void displayMessage(String priceMessage) {

        TextView txtPriceMessage = (TextView) findViewById(R.id.txt_Price);
        txtPriceMessage.setText(priceMessage);
    }
    private String createOrderSummary(String name,int price, boolean addWhippedCream,boolean addChocolate) {

        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd Whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage += "\nQuantity:" + quantity;
        priceMessage +="\nTotal: $" + price;
        priceMessage +="\nThank You!";

        return priceMessage;

    }


    public  void increment(View v)
    {

        if(quantity == 100)
        {
            Toast.makeText(this, "You cannot have more 100 coffees",Toast.LENGTH_LONG);
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }
    public void decrement(View v)
    {
        if(quantity == 1)
        {
            Toast.makeText(this, "You cannot have less than 1 coffees",Toast.LENGTH_LONG);
            return;
        }
       quantity = quantity -  1;
        displayQuantity(quantity);

    }
    private  int calculatePrice(boolean addWhippedCream,boolean addChocolate)
    {
        int basePrice = 5;

        if(addWhippedCream)
        {
            basePrice = basePrice + 1;
        }

        if(addChocolate)
        {
            basePrice = basePrice + 2;
        }
        return quantity * basePrice;
    }
    private void displayQuantity(int number)
    {
        TextView tvQuantity = (TextView) findViewById(R.id.txt_Qty);
        tvQuantity.setText("" + number);
    }
   // private void displayPrice(int price)
   // {

       // TextView tvPrice = (TextView) findViewById(R.id.txt_Price);
       // tvPrice.setText(NumberFormat.getCurrencyInstance().format(price));
    //}
}
