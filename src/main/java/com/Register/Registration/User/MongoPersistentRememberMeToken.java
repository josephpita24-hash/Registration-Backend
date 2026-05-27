package com.Register.Registration.User;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "persistent_logins") // This names your MongoDB collection
public class MongoPersistentRememberMeToken {

    @Id
    private String series;
    private String username;
    private String tokenValue;
    private Date date;

    // Default Constructor
    public MongoPersistentRememberMeToken() {}

    // Constructor matching Spring Security's requirements
    public MongoPersistentRememberMeToken(String username, String series, String tokenValue, Date date) {
        this.username = username;
        this.series = series;
        this.tokenValue = tokenValue;
        this.date = date;
    }

    // Getters and Setters
    public String getSeries() { return series; }
    public void setSeries(String series) { this.series = series; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getTokenValue() { return tokenValue; }
    public void setTokenValue(String tokenValue) { this.tokenValue = tokenValue; }

    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}