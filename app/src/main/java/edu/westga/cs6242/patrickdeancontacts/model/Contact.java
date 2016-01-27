package edu.westga.cs6242.patrickdeancontacts.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Patterns;

/**
 * Created by Patrick on 1/24/2016.
 * This file contains the code for the Contact class. The contact class represents just what you
 * think it would, a contact. A contact has a name (composed of a first and last name), an email
 * address, and a phone number (be it home or cell).
 * <p/>
 * This class contains methods for creating, getting, and setting a contacts values and it's state.
 */
public class Contact implements Parcelable {
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String emailAddr;
    private String phoneNumberType;

    public Contact() {

    }

    /**
     * This constructor accepts a First Name (fn), Last Name (ln), Phone Number (pn), Email Address
     * (ea), and the contact type (pnt), all of which are Strings.
     *
     * @param fn  The first name of the contact
     * @param ln  The last name of the contact
     * @param pn  The phone number of the contact
     * @param ea  The email address of the contact
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("First Name of a Contact must contain"
                    + " a string at least one character");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = (lastName == null) ? "" : lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (this.auditPhoneNumber(phoneNumber) == false) {
            throw new IllegalArgumentException("Contact's phone number must be a valid 7"
                    + " digit or more number");
        }
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        if (this.auditEmailAddress(emailAddr) == false) {
            throw new IllegalArgumentException("Email Address must resemble, example@email.com");
        }
        this.emailAddr = emailAddr;
    }

    public String getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(String phoneNumberType) {
        if (phoneNumberType.equals("Home") || phoneNumberType.equals("Cell")) {
            this.phoneNumberType = phoneNumberType;
        } else {
            throw new IllegalArgumentException("The type of phone number must be Home or Cell");
        }
    }

    /**
     * This method audits the phone number for validity. A phone number must be at least 7 digits
     * long and be only digits, not alpha characters. If the passed parameter violates the
     * previously stated contract then the function will return false, else return true.
     *
     * @param pn A contact's phone number
     * @return true for valid phone number, false otherwise
     */
    private boolean auditPhoneNumber(String pn) {
        if (pn == null || pn.length() < 7) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Contact)) return false;

        Contact contact = (Contact) o;

        if (getFirstName() != null ? !getFirstName().equals(contact.getFirstName()) : contact.getFirstName() != null)
            return false;
        if (getLastName() != null ? !getLastName().equals(contact.getLastName()) : contact.getLastName() != null)
            return false;
        if (getPhoneNumber() != null ? !getPhoneNumber().equals(contact.getPhoneNumber()) : contact.getPhoneNumber() != null)
            return false;
        if (getEmailAddr() != null ? !getEmailAddr().equals(contact.getEmailAddr()) : contact.getEmailAddr() != null)
            return false;
        return !(getPhoneNumberType() != null ? !getPhoneNumberType().equals(contact.getPhoneNumberType()) : contact.getPhoneNumberType() != null);

    }

    @Override
    public int hashCode() {
        int result = getFirstName() != null ? getFirstName().hashCode() : 0;
        result = 31 * result + (getLastName() != null ? getLastName().hashCode() : 0);
        result = 31 * result + (getPhoneNumber() != null ? getPhoneNumber().hashCode() : 0);
        result = 31 * result + (getEmailAddr() != null ? getEmailAddr().hashCode() : 0);
        result = 31 * result + (getPhoneNumberType() != null ? getPhoneNumberType().hashCode() : 0);
        return result;
    }

    public static final Parcelable.Creator<Contact> CREATOR = new Creator<Contact>() {
        @Override
        public Contact createFromParcel(Parcel source) {
            Contact c = new Contact();
            c.firstName = source.readString();
            c.lastName = source.readString();
            c.emailAddr = source.readString();
            c.phoneNumber = source.readString();
            c.phoneNumberType = source.readString();
            return c;
        }

        @Override
        public Contact[] newArray(int size) {
            return new Contact[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.firstName);
        dest.writeString(this.lastName);
        dest.writeString(this.emailAddr);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.phoneNumberType);
    }
}
