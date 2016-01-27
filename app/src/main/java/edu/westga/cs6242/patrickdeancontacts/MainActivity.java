package edu.westga.cs6242.patrickdeancontacts;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import edu.westga.cs6242.patrickdeancontacts.model.Contact;
import edu.westga.cs6242.patrickdeancontacts.util.ContactAppUtil;

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

    /**
     * sets up the classes instance variables
     */
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

    /**
     * Event that is fired when the save button is clicked
     *
     * @param v NOT USED
     */
    public void clickSaveContact(View v) {
        if (this.createContact()) {
            Intent intent = new Intent(v.getContext(), ViewContact.class);
            Bundle bundleOfJoy = new Bundle();
            bundleOfJoy.putParcelable(ContactAppUtil.CONTACT_KEY, this.contact);
            intent.putExtras(bundleOfJoy);
            startActivity(intent);
        }
    }

    /**
     * Clears the form fields.
     *
     * @param v NOT USED
     */
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

    /**
     * Attempts to create the contact by auditing the form data then building the contact's data,
     * then creating the contact object
     *
     * @return true if all good.
     */
    private boolean createContact() {
        if (!this.auditFormData())
            return false;
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

    /**
     * This function audits the data passed in the contact phone.
     *
     * @return true if data valid, false otherwise
     */
    private boolean auditFormData() {
        if (this.firstNameField.getText().toString().equals("") || this.firstNameField.getText() == null) {
            this.tvErrorText.setText("Please provide the contact's first name.");
            this.showErrorText();
            return false;
        }
        if (this.lastNameField.getText().toString().equals("") || this.lastNameField.getText() == null) {
            this.lastNameField.setText("");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(this.emailField.getText()).matches()) {
            this.tvErrorText.setText("Please provide a valid email address.");
            this.showErrorText();
            return false;
        }
        if (this.phoneField.length() < 7 || this.phoneField == null) {
            this.tvErrorText.setText("Please provide the contact's phone number.");
            this.showErrorText();
            return false;
        }
        return true;
    }

    /**
     * Sets the error text property to visible
     */
    private void showErrorText() {
        this.tvErrorText.setVisibility(View.VISIBLE);
    }

    /**
     * Sets the error text property to invisible
     */
    private void hideErrorText() {
        this.tvErrorText.setVisibility(View.INVISIBLE);
    }

    /**
     * This code allows the user to close the input key board when the user clicks off
     * of the keyboard.
     *
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
            for (int i = 0; i < ((ViewGroup) v).getChildCount(); i++) {
                View innerView = ((ViewGroup) v).getChildAt(i);
                this.addUIListeners(innerView);
            }
        }
    }

    /**
     * This is the actual method that physically hides the key board from view.
     *
     * @param a this activity, i.e. the MainActivity.
     */
    private void hideKeyboard(Activity a) {
        InputMethodManager imm =
                (InputMethodManager) a.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            imm.hideSoftInputFromWindow(a.getCurrentFocus().getWindowToken(), 0);
        } catch (NullPointerException npe) {
            this.tvErrorText.setText(npe.getMessage());
        }
    }
}
