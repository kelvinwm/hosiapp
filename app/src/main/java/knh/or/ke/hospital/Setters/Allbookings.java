package knh.or.ke.hospital.Setters;

public class Allbookings {

    String clinicname;
    String clinicdate;
    String clinictime;
    String username;
    String status;

    public Allbookings() {
    }

    public Allbookings(String clinicname, String clinicdate, String clinictime, String username, String status) {
        this.clinicname = clinicname;
        this.clinicdate = clinicdate;
        this.clinictime = clinictime;
        this.username = username;
        this.status = status;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
