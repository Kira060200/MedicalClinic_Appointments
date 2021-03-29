package model;

public class MedicalSurgery extends Appointment{
    private String type;
    private Assistant as;

    public MedicalSurgery(long id, String date, float price, Doctor doc, Patient pat, String type, Assistant as) {
        super(id, date, price, doc, pat);
        this.type = type;
        this.as = as;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Assistant getAs() {
        return as;
    }

    public void setAs(Assistant as) {
        this.as = as;
    }

    @Override
    public String toString() {
        return super.toString() + "\nSurgery type: " + type ;
    }
}
