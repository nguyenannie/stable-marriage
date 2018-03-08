package com.example.matchmakinglocal.models;

import javax.persistence.*;

@Entity
@Table(name = "partner")
public class Partner extends User {

    @Column(name = "company_name", nullable = false)
    private String companyName;

    public Partner() {
        super();
    }

    public Partner(String companyName) {
        super();
        this.companyName = companyName;
    }

    public Partner(String email,  String companyName) {
        super(email);
        this.companyName = companyName;
    }

    public Partner(String email, String phoneNumber, String companyName) {
        super(email, phoneNumber);
        this.companyName = companyName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    @Override
    public String toString() {
        return  "Partner{ id : " + this.id
                + ", Company name : " + this.companyName
                + ", Email : " + this.email
                + ", Phone Number : " + this.phoneNumber
                + " }";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Partner)) {
            return false;
        }

        Partner comparePartner = (Partner) obj;

        return comparePartner.id.equals(this.id);
    }
}

