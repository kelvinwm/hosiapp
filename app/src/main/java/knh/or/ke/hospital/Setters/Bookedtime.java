package knh.or.ke.hospital.Setters;

public class Bookedtime {
    String keyfrmtime;
    String timedate;

    public Bookedtime() {
    }

    public Bookedtime(String keyfrmtime, String timedate) {
        this.keyfrmtime = keyfrmtime;
        this.timedate = timedate;
    }

    public String getKeyfrmtime() {
        return keyfrmtime;
    }

    public void setKeyfrmtime(String keyfrmtime) {
        this.keyfrmtime = keyfrmtime;
    }

    public String getTimedate() {
        return timedate;
    }

    public void setTimedate(String timedate) {
        this.timedate = timedate;
    }
}
