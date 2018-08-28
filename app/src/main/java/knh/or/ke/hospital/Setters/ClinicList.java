package knh.or.ke.hospital.Setters;

public class ClinicList {
    String clinicname;
    String clinicdescription;

    public ClinicList(String clinicname, String clinicdescription) {
        this.clinicname = clinicname;
        this.clinicdescription = clinicdescription;
    }

    public String getClinicname() {
        return clinicname;
    }

    public void setClinicname(String clinicname) {
        this.clinicname = clinicname;
    }

    public String getClinicdescription() {
        return clinicdescription;
    }

    public void setClinicdescription(String clinicdescription) {
        this.clinicdescription = clinicdescription;
    }
}
