package vn.fpt.fsoft.stu.cloudgateway.domain;


import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class EC2 implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 8922439787987562642L;
    private String id;
    private String name;
    private String amiId;
    private Date startDate;
    private String publicIp;
    private String privateIp;
    private String publicDNS;
    private String subnetId;
    private String vpcId;
    private String region;
    private String description;
    private String instanceType;
    private List<Tag> tags;
    private State state;
    private String os;
    private Double rate;
    private List<EBS> ebs;
    private List<String> volumeIds;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmiId() {
        return amiId;
    }

    public void setAmiId(String amiId) {
        this.amiId = amiId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getPublicIp() {
        return publicIp;
    }

    public void setPublicIp(String publicIp) {
        this.publicIp = publicIp;
    }

    public String getPrivateIp() {
        return privateIp;
    }

    public void setPrivateIp(String privateIp) {
        this.privateIp = privateIp;
    }

    public String getPublicDNS() {
        return publicDNS;
    }

    public void setPublicDNS(String publicDNS) {
        this.publicDNS = publicDNS;
    }

    public String getSubnetId() {
        return subnetId;
    }

    public void setSubnetId(String subnetId) {
        this.subnetId = subnetId;
    }

    public String getVpcId() {
        return vpcId;
    }

    public void setVpcId(String vpcId) {
        this.vpcId = vpcId;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(String instanceType) {
        this.instanceType = instanceType;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    public List<EBS> getEbs() {
        return ebs;
    }

    public void setEbs(List<EBS> ebs) {
        this.ebs = ebs;
    }

    public List<String> getVolumeIds() {
        return volumeIds;
    }

    public void setVolumeIds(List<String> volumeIds) {
        this.volumeIds = volumeIds;
    }
}
