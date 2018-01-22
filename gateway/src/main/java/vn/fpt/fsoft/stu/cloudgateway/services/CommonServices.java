package vn.fpt.fsoft.stu.cloudgateway.services;

import vn.fpt.fsoft.stu.cloudgateway.domain.CloudEnvironments;
import vn.fpt.fsoft.stu.cloudgateway.domain.Region;

import java.util.List;

public interface CommonServices {
    public List<Region> getAllRegions();

    public List<CloudEnvironments> getAllEnvironments();

    public List<CloudEnvironments> getAllEnvironmentsByType(int type);

    public CloudEnvironments getEnvironmentByCode(String code);

    public CloudEnvironments getEnvironmentById(long id);
}
