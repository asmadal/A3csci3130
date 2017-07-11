package com.acme.a3csci3130;

import com.google.firebase.database.DatabaseReference;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by zaher on 7/11/2017.
 */
public class CreateContactAcitivityTest {
    CreateContactAcitivity acitivity = new CreateContactAcitivity();
    DatabaseReference ref;

    @Test
    public void createContact() throws Exception {

    }

    @Test
    public void validData() throws Exception {
        String name = "asma";
        String email = "asma@gmail.com";
        Double businessNumber = Double.parseDouble("123456789");
        String business = "Fisher";
        String address = "Bedrose";
        String province = "NS";
        assertEquals(true, acitivity.validData(name, businessNumber));
    }

}