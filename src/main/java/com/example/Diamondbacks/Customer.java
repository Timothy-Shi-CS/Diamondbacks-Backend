package com.example.Diamondbacks;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="Customer")
public class Customer implements Serializable{

    private int id;

    private String firstName;

    private String lastName;

    @Id
    @Column(name="customerID", unique = true)
    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id=id;
    }

    @Column(name="firstName", nullable = false)
    public String getFirstName(){
        return this.firstName;
    }

    public void setFirstName(String firstName){
        this.firstName=firstName;
    }

    @Column(name="lastName", nullable = false)
    public String getLastName(){
        return this.lastName;
    }

    public void setLastName(String lastName){
        this.lastName=lastName;
    }
}
