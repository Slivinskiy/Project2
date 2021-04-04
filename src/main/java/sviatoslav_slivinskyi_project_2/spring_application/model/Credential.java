package sviatoslav_slivinskyi_project_2.spring_application.model;

import javax.persistence.*;

@Entity
@Table(name = ("credentials"))
public class Credential {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long credentialId;
    private String url;
    private String username;
    private String password;
    private String secretKey;
    @ManyToOne
    private User user;

    public Credential( String url, String username, String password, String key, User user) {
        this.url = url;
        this.username = username;
        this.password = password;
        this.secretKey = key;
        this.user = user;
    }

    public Credential() {
    }

    public Long getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Long credentialId) {
        this.credentialId = credentialId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public User getUsers() {
        return user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
