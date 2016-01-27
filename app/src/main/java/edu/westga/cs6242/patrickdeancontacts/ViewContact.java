package edu.westga.cs6242.patrickdeancontacts;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import edu.westga.cs6242.patrickdeancontacts.model.Contact;
import edu.westga.cs6242.patrickdeancontacts.util.ContactAppUtil;

public class ViewContact extends AppCompatActivity {

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvPhone;

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.contact = (Contact) getIntent().getParcelableExtra(ContactAppUtil.CONTACT_KEY);
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

        name = this.contact.getFirstName() + " " + this.contact.getLastName();
        email = this.contact.getEmailAddr();
        phone = this.contact.getPhoneNumberType() + ": " + this.contact.getPhoneNumber();

        this.tvName.setText(name);
        this.tvEmail.setText(email);
        this.tvPhone.setText(phone);
    }

}
