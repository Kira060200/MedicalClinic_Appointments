package model;

public class Patient extends Person{
    private String[] disease;

    public Patient(long id, String firstName, String lastName, int age, String sex, String phoneNumber, String[] disease) {
        super(id, firstName, lastName, age, sex, phoneNumber);
        this.disease = disease;
    }

    public String[] getDisease() {
        return disease;
    }

    public void setDisease(String[] disease) {
        this.disease = disease;
    }

    @Override
    public String toString() {
        String d = "";
        for (String itm : disease){
            d += (itm + "|");
        }
        return super.toString() + " / Diseases: " + ((!d.isEmpty()) ? d : "None");
    }
}
