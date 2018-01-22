package vn.fpt.fsoft.stu.cloudgateway.client;

import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;
import vn.fpt.fsoft.stu.cloudgateway.domain.Region;

import java.util.List;

public interface CloudClient {
    public List<Region> getAllRegions();

    public List<EC2> getAllInstanceByRegion(String accessKey, String privateKey, String region);
}
