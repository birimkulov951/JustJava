package com.example.justjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    String albumDescription;
    int quantity = 0;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.album_description_view);


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                albumDescription = editText.getText().toString();
            }
        });
    }


    public void increment(View view) {
        if (quantity<=100){
            display(quantity++);
        }
    }

    public void decrement(View view) {
        if (quantity>=1){
            display(quantity--);
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBoxView1 = (CheckBox) findViewById(R.id.notify_me_checkbox);
        boolean hasWhippedCream = checkBoxView1.isChecked();
        CheckBox checkBoxView2 = (CheckBox) findViewById(R.id.notify_me_chocolate);
        boolean hasChocolate = checkBoxView2.isChecked();

        price = quantity*5;
        String summary = createOrderSummary(price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java order for " + albumDescription);
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate) {
        String priceMessage = "\nName: " + albumDescription;
        if (hasWhippedCream){
            priceMessage = priceMessage + "\nAdd whipped cream? " + hasWhippedCream;
            price = price + 1;
        }
        if (hasChocolate){
            priceMessage = priceMessage + "\nAdd chocolate? " + hasChocolate;
            price = price + 2;
        }
        priceMessage = priceMessage + "\nQuantity: " + quantity + "\nTotal $: " + price + "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }



}
