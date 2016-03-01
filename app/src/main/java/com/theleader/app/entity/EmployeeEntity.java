package com.theleader.app.entity;

import R.helper.BindField;
import R.helper.EntityX;

/**
 * Created by duynk on 3/2/16.
 */
public class EmployeeEntity extends EntityX {
    public EmployeeEntity() {
        super();
    }
    @BindField("firstName") String firstName;
    @BindField("lastName") String lastName;
    @BindField("email") String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
