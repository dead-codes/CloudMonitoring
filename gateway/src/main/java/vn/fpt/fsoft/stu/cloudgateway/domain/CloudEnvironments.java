package vn.fpt.fsoft.stu.cloudgateway.domain;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cloud_environments")
public class CloudEnvironments implements Serializable {

    private static final long serialVersionUID = 6261988691153674095L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private long id;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "type")
    private int type;

    @Column(name = "access_key")
    private String accessKey;

    @Column(name = "secret_key")
    private String secretKey;

    @Column(name = "active")
    private boolean active;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
