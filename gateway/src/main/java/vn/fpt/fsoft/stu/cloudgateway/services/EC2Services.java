package vn.fpt.fsoft.stu.cloudgateway.services;

import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;

import java.util.List;

public interface EC2Services {
	public List<EC2> getAllEC2InstanceInRegion(String accessKey, String secretKey, String region);

	public List<EC2> getAllEC2Instance(String accessKey, String secretKey);
}
