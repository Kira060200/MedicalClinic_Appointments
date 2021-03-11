package model;

public abstract class Appointment {
    protected long id;
    protected String date;
    protected float price;
    protected Doctor doc;
    protected Patient pat;

    public Appointment(long id, String date, float price, Doctor doc, Patient pat) {
        this.id = id;
        this.date = date;
        this.price = price;
        this.doc = doc;
        this.pat = pat;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Doctor getDoc() {
        return doc;
    }

    public void setDoc(Doctor doc) {
        this.doc = doc;
    }

    public Patient getPat() {
        return pat;
    }

    public void setPat(Patient pat) {
        this.pat = pat;
    }
}
