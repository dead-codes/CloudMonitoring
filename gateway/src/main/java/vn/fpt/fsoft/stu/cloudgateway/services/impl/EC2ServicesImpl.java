package vn.fpt.fsoft.stu.cloudgateway.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.fpt.fsoft.stu.cloudgateway.client.aws.AWSClient;
import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;
import vn.fpt.fsoft.stu.cloudgateway.domain.Region;
import vn.fpt.fsoft.stu.cloudgateway.services.EC2Services;

import java.util.ArrayList;
import java.util.List;

@Component
public class EC2ServicesImpl implements EC2Services {

    private static Logger LOGGER = LoggerFactory.getLogger(EC2ServicesImpl.class);

    @Autowired
    private AWSClient awsClient;

    @Override
    public List<EC2> getAllEC2InstanceInRegion(String accessKey, String secretKey, String region) {
        List<EC2> instances = null;
        try {
            instances = awsClient.getAllInstanceByRegion(accessKey, secretKey, region);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }

        return instances;
    }

    @Override
    public List<EC2> getAllEC2Instance(String accessKey, String secretKey) {
        List<Region> regions = null;
        List<EC2> instances = null;
        List<EC2> regionInstances = null;
        try {
            regions = awsClient.getAllRegions();
            if (regions != null && regions.size() > 0) {
                for (Region region : regions) {
                    if (instances == null) {
                        instances = new ArrayList<>();
                    }

                    regionInstances = awsClient.getAllInstanceByRegion(accessKey, secretKey, region.getCode());
                    if (regionInstances != null) {
                        instances.addAll(regionInstances);
                    }
                    regionInstances = null;

                }
            }
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return instances;
    }
}
