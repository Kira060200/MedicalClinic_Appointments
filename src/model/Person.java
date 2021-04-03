package model;

public abstract class Person implements Comparable <Person>{
    protected long id;
    protected String firstName;
    protected String lastName;
    protected int age;
    protected String sex;
    protected String phoneNumber;

    public Person(long id, String firstName, String lastName, int age, String sex, String phoneNumber) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.sex = sex;
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override //annotation
    public String toString() {
        return firstName + " / " + lastName + " / " + age + " / " + sex + " / " + phoneNumber;
    }

    @Override
    public int compareTo(Person o){
        if(this.lastName == null && ((Person) o).getLastName() == null)
            return 0;
        if(this.lastName == null)
            return 1;
        if (((Person) o).getLastName() == null)
            return -1;

        if (this.lastName.compareTo(((Person) o).getLastName()) == 0) {
            if(this.firstName == null && ((Person) o).getFirstName() == null)
                return 0;
            if(this.firstName == null)
                return 1;
            if (((Person) o).getFirstName() == null)
                return -1;
            return this.firstName.compareTo(((Person) o).getFirstName());
        }
        return this.lastName.compareTo(((Person) o).getLastName());
    }
}
