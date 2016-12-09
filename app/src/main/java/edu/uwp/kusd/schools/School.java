package edu.uwp.kusd.schools;



//a school object to be displayed in a cardview
class School {
    String schoolName;
    String image;
    String schoolType;
    String address;
    String city;
    String zip;
    String phone;
    String principal;
    String website;


    School(String schoolName, String image, String schoolType, String address, String city, String zip, String phone, String principal, String website) {
        this.schoolName = schoolName;
        this.image = image;
        this.schoolType = schoolType;
        this.address = address;
        this.city = city;
        this.zip = zip;
        this.phone = phone;
        this.principal = principal;
        this.website = website;

    }
}


