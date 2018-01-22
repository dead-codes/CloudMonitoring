package vn.fpt.fsoft.stu.cloudgateway.repository;

import vn.fpt.fsoft.stu.cloudgateway.domain.CloudEnvironments;

import java.util.List;

public interface CloudEnvironmentDAO {
    public List<CloudEnvironments> getAllEnvironments();

    public List<CloudEnvironments> getAllEnvironmentsByType(int type);

    public CloudEnvironments getEnvironmentByCode(String code);

    public CloudEnvironments getEnvironmentById(long id);
}
