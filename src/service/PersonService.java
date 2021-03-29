package service;

import model.Person;

public class PersonService {
    public void updateAge (Person person, int age) {
        person.setAge(age);
    }
    public void updatePhone (Person person, String phone) {
        person.setPhoneNumber(phone);
    }
}
