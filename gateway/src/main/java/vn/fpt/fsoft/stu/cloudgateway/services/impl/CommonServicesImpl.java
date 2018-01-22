package vn.fpt.fsoft.stu.cloudgateway.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import vn.fpt.fsoft.stu.cloudgateway.client.aws.AWSClient;
import vn.fpt.fsoft.stu.cloudgateway.domain.CloudEnvironments;
import vn.fpt.fsoft.stu.cloudgateway.domain.Region;
import vn.fpt.fsoft.stu.cloudgateway.repository.CloudEnvironmentDAO;
import vn.fpt.fsoft.stu.cloudgateway.services.CommonServices;

import java.util.List;

@Component
public class CommonServicesImpl implements CommonServices {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonServicesImpl.class);

    @Autowired
    private AWSClient awsClient;

    @Autowired
    private CloudEnvironmentDAO cloudEnvironmentDAO;


    @Override
    public List<Region> getAllRegions() {
        List<Region> regions = awsClient.getAllRegions();
        if (regions == null || regions.size() == 0) {
            LOGGER.error("List Regions empty!!!");
        }
        return regions;
    }

    @Override
    public List<CloudEnvironments> getAllEnvironments() {

        List<CloudEnvironments> environments = null;
        environments = cloudEnvironmentDAO.getAllEnvironments();

        return environments;
    }

    @Override
    public List<CloudEnvironments> getAllEnvironmentsByType(int type) {
        List<CloudEnvironments> environments = null;
        environments = cloudEnvironmentDAO.getAllEnvironmentsByType(type);

        return environments;
    }

    @Override
    public CloudEnvironments getEnvironmentByCode(String code) {
        CloudEnvironments environment = null;
        environment = cloudEnvironmentDAO.getEnvironmentByCode(code);

        return environment;
    }

    @Override
    public CloudEnvironments getEnvironmentById(long id) {
        CloudEnvironments environment = null;
        environment = cloudEnvironmentDAO.getEnvironmentById(id);

        return environment;
    }

}
