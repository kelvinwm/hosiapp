package knh.or.ke.hospital.Setters;

public class ClinicAvailability {

    String availtime;
    String availability;
    String keyvalue;
    String clndate;

    public ClinicAvailability() {
    }


    public ClinicAvailability(String availtime, String keyvalue) {
        this.availtime = availtime;
        this.keyvalue = keyvalue;
    }

    public String getAvailtime() {
        return availtime;
    }

    public void setAvailtime(String availtime) {
        this.availtime = availtime;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getKeyvalue() {
        return keyvalue;
    }

    public void setKeyvalue(String keyvalue) {
        this.keyvalue = keyvalue;
    }

    public String getClndate() {
        return clndate;
    }

    public void setClndate(String clndate) {
        this.clndate = clndate;
    }
}
