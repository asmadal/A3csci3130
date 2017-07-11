package com.acme.a3csci3130;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Class that defines how the data will be stored in the
 * Firebase databse. This is converted to a JSON format
 */

public class Contact implements Serializable {

    public String uid;
    public String name;
    public String email;
    public String business;
    public Double businessNumber;
    public String address;
    public String province;

    public Contact() {
        // Default constructor required for calls to DataSnapshot.getValue
    }

    public Contact(String uid, String name, String email,
                   String business, String address, String province,double businessNumber) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.business = business;
        this.address = address;
        this.province = province;
        this.businessNumber=businessNumber;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("uid", uid);
        result.put("name", name);
        result.put("email", email);
        result.put("business", business);
        result.put("address", address);
        result.put("province", province);
        result.put("businessNumber",businessNumber);
        return result;
    }
}
