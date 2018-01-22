package vn.fpt.fsoft.stu.cloudgateway.domain;


import java.io.Serializable;

public class Region implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -8792268477019717011L;
    private String code;
    private String name;

    public Region() {
        super();
    }

    public Region(String code, String name) {
        super();
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
