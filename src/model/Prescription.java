package model;

public class Prescription {
    private long id;
    private Drug[] drug;

    public Prescription(long id, Drug[] drug) {
        this.id = id;
        this.drug = drug;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Drug[] getDrug() {
        return drug;
    }

    public void setDrug(Drug[] drug) {
        this.drug = drug;
    }
}
