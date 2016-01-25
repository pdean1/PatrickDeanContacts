package edu.westga.cs6242.patrickdeancontacts;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.westga.cs6242.patrickdeancontacts.model.Contact;

public class MainActivity extends AppCompatActivity {

    private Contact contact;
    private EditText firstNameField = null;
    private EditText lastNameField = null;
    private EditText emailField = null;
    private EditText phoneField = null;
    private RadioGroup phoneRadio = null;
    private TextView tvErrorText = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.addUIListeners(findViewById(R.id.container));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.setUpVariables();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void setUpVariables() {
        this.firstNameField = (EditText) findViewById(R.id.etFirstName);
        this.lastNameField = (EditText) findViewById(R.id.etLastName);
        this.emailField = (EditText) findViewById(R.id.etEmail);
        this.phoneField = (EditText) findViewById(R.id.etPhone);
        this.phoneRadio = (RadioGroup) findViewById(R.id.rgPhoneNumber);
        this.tvErrorText = (TextView) findViewById(R.id.tvErrorText);
        this.hideErrorText();
        this.tvErrorText.setTextColor(Color.RED);
    }

    public void clickSaveContact(View v) {
        if (this.createContact()) {
            this.tvErrorText.setText("Contact added.");
            this.tvErrorText.setTextColor(Color.GREEN);
            this.showErrorText();
        }
    }

    public void clickClearFields(View v) {
        this.firstNameField.setText("");
        this.lastNameField.setText("");
        this.emailField.setText("");
        this.phoneField.setText("");
        RadioButton rbCell = (RadioButton) findViewById(R.id.rbCellPhone);
        rbCell.setChecked(true);
        this.firstNameField.setActivated(true);
        this.hideErrorText();
    }

    private boolean createContact() {
        if (this.firstNameField.getText().toString().equals("") || this.firstNameField.getText() == null) {
            this.tvErrorText.setText("Please provide the contact's first name.");
            this.showErrorText();
            return false;
        }
        if (this.lastNameField.getText().toString().equals("") || this.lastNameField.getText() == null) {
            this.lastNameField.setText("");
        }
        if (this.phoneField.length() < 7 || this.phoneField == null) {
            this.tvErrorText.setText("Please provide the contact's first name.");
            this.showErrorText();
            return false;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(this.emailField.getText()).matches()) {
            this.tvErrorText.setText("Please provide a valid email address.");
            this.showErrorText();
            return false;
        }
        RadioButton rbCheckedRadioButton =
                (RadioButton) findViewById(this.phoneRadio.getCheckedRadioButtonId());
        try {
            this.contact = new Contact(this.firstNameField.getText().toString(),
                    this.lastNameField.getText().toString(),
                    this.phoneField.getText().toString(),
                    this.emailField.getText().toString(),
                    rbCheckedRadioButton.getText().toString());
        } catch (Exception e) {
            this.tvErrorText.setText(e.getMessage());
            return false;
        }
        return true;
    }

    private void showErrorText() {
        this.tvErrorText.setVisibility(View.VISIBLE);
    }

    private void hideErrorText() {
        this.tvErrorText.setVisibility(View.INVISIBLE);
    }

    /**
     * This code allows the user to close the input key board when the user clicks off
     * of the keyboard.
     * @param v a view object, this is the relative layout container that houses the components of
     *          this simple application.
     */
    private void addUIListeners(View v) {
        if (!(v instanceof EditText)) {
            v.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    hideKeyboard(MainActivity.this);
                    return false;
                }
            });
        }
        if (v instanceof ViewGroup) {
            for (int i = 0; i <((ViewGroup) v).getChildCount(); i++) {
                View innerView = ((ViewGroup) v).getChildAt(i);
                this.addUIListeners(innerView);
            }
        }
    }

    /**
     * This is the actual method that physically hides the key board from view.
     * @param a this activity, i.e. the MainActivity.
     */
    private void hideKeyboard(Activity a) {
        InputMethodManager imm =
                (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), 0);
    }

}
