package de.phoenix.wgtest.model.management;

import de.phoenix.wgtest.model.security.User;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity( name = "asd")
@Table( name = "asd")
public class Asd extends Person {

    private String youthoffice;

    public Asd() {
    }

    public Asd(String gender, String name, String phone, String fax, String email, Date birthday, Address address,
               LivingGroup livingGroup, List<AppointmentParticipants> appointmentParticipants, User user,
               String youthoffice) {
        super(gender, name, phone, fax, email, birthday, address, livingGroup, appointmentParticipants, user);
        this.youthoffice = youthoffice;
    }

    public String getYouthoffice() {
        return youthoffice;
    }

    public void setYouthoffice(String youthoffice) {
        this.youthoffice = youthoffice;
    }
}
