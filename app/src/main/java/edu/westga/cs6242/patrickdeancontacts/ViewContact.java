package edu.westga.cs6242.patrickdeancontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import edu.westga.cs6242.patrickdeancontacts.model.Contact;

public class ViewContact extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;

    private Contact c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.c = (Contact) getIntent().getParcelableExtra(MainActivity.CONTACT_KEY);
        setContentView(R.layout.activity_view_contact);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.tvName = (TextView) findViewById(R.id.tvName);
        this.tvEmail = (TextView) findViewById(R.id.tvEmail);
        this.tvPhone = (TextView) findViewById(R.id.tvPhone);

        this.buildDisplay();
    }

    public void clickGoBack(View v) {
        Intent intent = new Intent(v.getContext(), MainActivity.class);
        startActivity(intent);
    }

    private void buildDisplay() {
        String name = "";
        String email = "";
        String phone = "";

        name = this.c.getFirstName() + " " + this.c.getLastName();
        email = this.c.getEmailAddr();
        phone = this.c.getPhoneNumberType() + ": " + this.c.getPhoneNumber();

        this.tvName.setText(name);
        this.tvEmail.setText(email);
        this.tvPhone.setText(phone);
    }

}
