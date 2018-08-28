package knh.or.ke.hospital.Setters;

public class Chosenclinic {
    String clinicname;
    String clinicdate;
    String clinictime;
    String keytouse;

    public Chosenclinic() {
    }

    public Chosenclinic(String clinicname, String clinicdate, String clinictime, String keytouse) {
        this.clinicname = clinicname;
        this.clinicdate = clinicdate;
        this.clinictime = clinictime;
        this.keytouse = keytouse;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getClinicdate() {
        return clinicdate;
    }

    public void setClinicdate(String clinicdate) {
        this.clinicdate = clinicdate;
    }

    public String getClinictime() {
        return clinictime;
    }

    public void setClinictime(String clinictime) {
        this.clinictime = clinictime;
    }

    public String getKeytouse() {
        return keytouse;
    }

    public void setKeytouse(String keytouse) {
        this.keytouse = keytouse;
    }
}
