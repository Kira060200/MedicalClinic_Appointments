package service;

import model.Appointment;

public class AppointmentService {
    public void updateDate(Appointment appointment, String date){
        appointment.setDate(date);
    }
}
