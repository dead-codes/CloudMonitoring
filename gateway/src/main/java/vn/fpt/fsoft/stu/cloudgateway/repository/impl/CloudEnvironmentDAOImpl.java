package vn.fpt.fsoft.stu.cloudgateway.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import vn.fpt.fsoft.stu.cloudgateway.domain.CloudEnvironments;
import vn.fpt.fsoft.stu.cloudgateway.repository.CloudEnvironmentDAO;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@Transactional
public class CloudEnvironmentDAOImpl implements CloudEnvironmentDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<CloudEnvironments> getAllEnvironments() {

        List<CloudEnvironments> environments = null;
        environments = entityManager.createQuery("SELECT u FROM CloudEnvironments u").getResultList();

        return environments;
    }

    @Override
    public List<CloudEnvironments> getAllEnvironmentsByType(int type) {
        List<CloudEnvironments> environments = null;
        environments = entityManager.createQuery("SELECT u FROM CloudEnvironments u WHERE u.type = ?").setParameter(1, type).getResultList();

        return environments;
    }

    @Override
    public CloudEnvironments getEnvironmentByCode(String code) {
        CloudEnvironments environment = null;
        List<CloudEnvironments> environments = entityManager.createQuery("SELECT u FROM CloudEnvironments u WHERE code = ?").setParameter(1, code).getResultList();

        if (environments != null && environments.size() > 0) {
            environment = environments.get(0);
        }

        return environment;
    }

    @Override
    public CloudEnvironments getEnvironmentById(long id) {
        CloudEnvironments environment = null;
        List<CloudEnvironments> environments = entityManager.createQuery("SELECT u FROM CloudEnvironments u WHERE id = ?").setParameter(1, id).getResultList();

        if (environments != null && environments.size() > 0) {
            environment = environments.get(0);
        }

        return environment;
    }
}
