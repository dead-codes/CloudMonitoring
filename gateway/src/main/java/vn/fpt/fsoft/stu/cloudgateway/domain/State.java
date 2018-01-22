package vn.fpt.fsoft.stu.cloudgateway.domain;


import java.io.Serializable;

public class State implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 2295064871683970646L;
    private int code;
    private String value;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
