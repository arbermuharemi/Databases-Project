package main.java.model;

/**
 * Created by Arber on 4/22/2017.
 */
public class CityOfficial {
    private String username;
    private String email;
    private String title;
    private Boolean approved;
    private String city;
    private String state;

    public Boolean getApproved () {
        return approved;
    }

    public String getEmail () {
        return email;
    }

    public String getCity () {
        return city;
    }

    public String getState () {
        return state;
    }

    public String getTitle () {
        return title;
    }

    public String getUsername () {
        return username;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public void setApproved (Boolean approved) {
        this.approved = approved;
    }

    public void setCity (String city) {
        this.city = city;
    }

    public void setState (String state) {
        this.state = state;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public void setUsername (String username) {
        this.username = username;
    }
}
