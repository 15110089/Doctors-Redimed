package com.example.doctors_redimed;

public class NewRequest_Profile {
    public String Name;
    public String Birth;
    public String Phone;
    public String Gender;

    public NewRequest_Profile() {
    }

    public NewRequest_Profile(String name, String birth, String phone, String gender) {
        Name = name;
        Birth = birth;
        Phone = phone;
        Gender = gender;
    }
}
