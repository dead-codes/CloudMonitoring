package vn.fpt.fsoft.stu.cloudgateway.domain;


import java.io.Serializable;

public class Tag implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -7724370597232011451L;
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
