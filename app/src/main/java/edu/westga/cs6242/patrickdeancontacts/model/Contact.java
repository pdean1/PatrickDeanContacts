package edu.westga.cs6242.patrickdeancontacts.model;

import android.util.Patterns;

/**
 * Created by Patrick on 1/24/2016.
 * This file contains the code for the Contact class. The contact class represents just what you
 * think it would, a contact. A contact has a name (composed of a first and last name), an email
 * address, and a phone number (be it home or cell).
 *
 * This class contains methods for creating, getting, and setting a contacts values and it's state.
 */
public class Contact {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddr;
    private String phoneNumberType;

    /**
     * This constructor accepts a First Name (fn), Last Name (ln), Phone Number (pn), Email Address
     * (ea), and the contact type (pnt), all of which are Strings.
     * @param fn The first name of the contact
     * @param ln The last name of the contact
     * @param pn The phone number of the contact
     * @param ea The email address of the contact
     * @param pnt The phone number type of the of the phone number, home or cell
     */
    public Contact(String fn, String ln, String pn, String ea, String pnt) {
        if (fn == null || fn.equals("")) {
            throw new IllegalArgumentException("First Name of a Contact must contain"
                      + " a string at least one character");
        } else {
            this.firstName = fn;
        }
        this.lastName = (ln == null) ? "" : ln;
        if (this.auditPhoneNumber(pn) == false) {
            throw new IllegalArgumentException("Contact's phone number must be a valid 7"
                    + " digit or more number");
        } else {
            this.phoneNumber = pn;
        }
        if (this.auditEmailAddress(ea) == false) {
            throw new IllegalArgumentException("Email Address must resemble, example@email.com");
        } else {
            this.emailAddr = ea;
        }
        if (pnt.equals("Home") || pnt.equals("Cell")) {
            this.phoneNumberType = pnt;
        } else {
            throw new IllegalArgumentException("The type of phone number must be Home or Cell");
        }
    }

    /**
     * This method audits the phone number for validity. A phone number must be at least 7 digits
     * long and be only digits, not alpha characters. If the passed parameter violates the
     * previously stated contract then the function will return false, else return true.
     * @param pn A contact's phone number
     * @return true for valid phone number, false otherwise
     */
    private boolean auditPhoneNumber(String pn) {
        if (pn == null || pn.length() < 7)
        {
            return false;
        }
        for (int i = 0; i < pn.length(); i++) {
            if (!Character.isDigit(pn.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private boolean auditEmailAddress(CharSequence ea) {
        if (ea == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(ea).matches();
    }
}
