package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Array;

public class DetailViewActivity extends Activity {

    private EditText nameField, emailField, addressField, businessNumberField;
    private Spinner businessField, provinceField;
    Contact receivedPersonInfo;
    private DatabaseReference appState;


    /**
     * Method Create the activity DetailViewActivity
     * and fill the data of the contact in the views
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        receivedPersonInfo = (Contact) getIntent().getSerializableExtra("Contact");
        appState = FirebaseDatabase.getInstance().getReference();
        nameField = (EditText) findViewById(R.id.name);
        emailField = (EditText) findViewById(R.id.email);
        businessNumberField = (EditText) findViewById(R.id.businessNumber);
        businessField = (Spinner) findViewById(R.id.business);
        provinceField = (Spinner) findViewById(R.id.province);
        String[] listBusiness = getResources().getStringArray(R.array.business_type);
        String[] listProvince = getResources().getStringArray(R.array.province_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.spinnerItemText, listBusiness);
        ArrayAdapter<String> adapterProvince = new ArrayAdapter<>(this, R.layout.spinner_item, R.id.spinnerItemText, listProvince);
        businessField.setAdapter(adapter);
        provinceField.setAdapter(adapterProvince);
        addressField = (EditText) findViewById(R.id.address);
        if (receivedPersonInfo != null) {
            nameField.setText(receivedPersonInfo.name);
            emailField.setText(receivedPersonInfo.email);
            businessNumberField.setText(String.format("%.0f", receivedPersonInfo.businessNumber));
            addressField.setText(receivedPersonInfo.address);
            businessField.setSelection(indexOf(listBusiness, receivedPersonInfo.business));
            provinceField.setSelection(indexOf(listProvince, receivedPersonInfo.province));
        }
    }


    /**
     * Method read String List and String value
     * return index of the string value in the list
     */

    private int indexOf(String[] list, String item) {
        for (int i = 0; i < list.length; i++) {
            if (list[i].equals(item)) {
                return i;
            }
        }
        return 0;
    }

    /**
     * Method update the value of the data in the fireBase database
     */
    public void updateContact(View v) {
        //TODO: Update contact funcionality
        DatabaseReference ref = appState.child("contacts").child(receivedPersonInfo.uid);
        if (validData()) {
            //each entry needs a unique ID
            Log.d("refId", ref.getKey());
            String name = nameField.getText().toString();
            ref.child("name").setValue(name);
            String email = emailField.getText().toString();
            ref.child("email").setValue(email);
            Double businessNumber = Double.parseDouble(businessNumberField.getText().toString());
            ref.child("businessNumber").setValue(businessNumber);
            String business = businessField.getSelectedItem().toString();
            ref.child("business").setValue(business);
            String address = addressField.getText().toString();
            ref.child("address").setValue(address);
            String province = provinceField.getSelectedItem().toString();
            ref.child("province").setValue(province);
            finish();
        }
    }

    public boolean validData() {
        if (nameField.getText().length() < 2 || nameField.getText().length() > 49) {
            Toast.makeText(DetailViewActivity.this, "The name should be more than 2 " +
                    "Character and less than 49", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessNumberField.getText().length() == 9) {
            Toast.makeText(DetailViewActivity.this, "The Business number must be 9 digit",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /**
     * Method for Create Contact to check the data is valid
     * to send it to the firebase data base
     */
    public void eraseContact(View v) {
        //TODO: Erase contact functionality
        DatabaseReference ref = appState.child("contacts");
        DatabaseReference c = ref.child(receivedPersonInfo.uid);
        Log.d("refId", c.getKey());
        ref.child(c.getKey()).removeValue();

        finish();
    }
}
