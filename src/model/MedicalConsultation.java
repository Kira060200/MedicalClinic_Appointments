package model;

public class MedicalConsultation extends Appointment{
    private String disease;
    private Prescription pr;

    public MedicalConsultation(long id, String date, float price, Doctor doc, Patient pat, String disease, Prescription pr) {
        super(id, date, price, doc, pat);
        this.disease = disease;
        this.pr = pr;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public Prescription getPr() {
        return pr;
    }

    public void setPr(Prescription pr) {
        this.pr = pr;
    }
}
