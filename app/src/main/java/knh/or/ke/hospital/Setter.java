package knh.or.ke.hospital;

public class Setter {
    private String name;
    private String phone;
    private Long timestamp;

    public Setter(String name, String phone, Long timestamp) {
        this.name = name;
        this.phone = phone;
        this.timestamp = timestamp;
    }
    public Setter() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
