package com.acme.a3csci3130;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateContactAcitivity extends Activity {

    private Button submitButton;
    private EditText nameField, emailField, addressField, businessNumberField;
    private Spinner businessField, provinceField;
    private MyApplicationData appState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_contact_acitivity);
        //Get the app wide shared variables
        appState = ((MyApplicationData) getApplicationContext());

        submitButton = (Button) findViewById(R.id.submitButton);
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

    }


    /**
     * Method for Create Contact Button
     * Create button then finish the activity CreateContactAcitivity
     */
    public void submitInfoButton(View v) {
        String name = nameField.getText().toString();
        String email = emailField.getText().toString();
        Double businessNumber = Double.parseDouble(businessNumberField.getText().toString());
        String business = businessField.getSelectedItem().toString();
        String address = addressField.getText().toString();
        String province = provinceField.getSelectedItem().toString();
        createContact(name, email, businessNumber, business, address, province);
        finish();
    }


    /**
     * Method for Create Contact by send the data
     * Name, email, Business Number, BusinessType, Address, Province
     * to FireBase after check is valid data
     */
    public String createContact(String name, String email, Double businessNumber, String business, String address, String province) {
        String personID = appState.firebaseReference.push().getKey();
        if (validData(name, businessNumber)) {
            //each entry needs a unique ID
            Contact person = new Contact(personID, name, email, business, address, province, businessNumber);
            appState.firebaseReference.child(personID).setValue(person);
        }
        return personID;
    }


    /**
     * Method for Create Contact to check the data is valid
     * to send it to the firebase data base
     */
    public boolean validData(String name, Double businessNumber) {
        if (name.length() < 2 || name.length() > 49) {
            Toast.makeText(CreateContactAcitivity.this, "The name should be more than 2 " +
                    "Character and less than 49", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (businessNumber <= 99999999) {
            Toast.makeText(CreateContactAcitivity.this, "The Business number must be 9 digit",
                    Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
