package vn.fpt.fsoft.stu.cloudgateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import vn.fpt.fsoft.stu.cloudgateway.domain.CloudEnvironments;
import vn.fpt.fsoft.stu.cloudgateway.domain.Region;
import vn.fpt.fsoft.stu.cloudgateway.repository.CloudEnvironmentDAO;
import vn.fpt.fsoft.stu.cloudgateway.services.CommonServices;

import java.util.List;

@RestController
@RequestMapping("/api/common")
public class CommonController {

    private static Logger LOGGER = LoggerFactory.getLogger(CommonController.class);

    @Autowired
    private CommonServices commonServices;

    @Autowired
    private CloudEnvironmentDAO dao;

    @RequestMapping(value = "/regions", method = RequestMethod.GET)
    private ResponseEntity<List<Region>> getAllRegions() {
        List<Region> regions = null;
        try {
            regions = commonServices.getAllRegions();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(regions, HttpStatus.OK);
    }

    @RequestMapping(value = "/environments", method = RequestMethod.GET)
    private ResponseEntity<List<CloudEnvironments>> getAllEnvironments() {
        List<CloudEnvironments> environments = null;
        try {
            environments = commonServices.getAllEnvironments();
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(environments, HttpStatus.OK);
    }

    @RequestMapping(value = "/environment/code/{code}", method = RequestMethod.GET)
    private ResponseEntity<CloudEnvironments> getEnvironmentsByCode(@PathVariable String code) {
        CloudEnvironments environment = null;
        try {
            environment = commonServices.getEnvironmentByCode(code);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(environment, HttpStatus.OK);
    }

    @RequestMapping(value = "/environment/id/{id}", method = RequestMethod.GET)
    private ResponseEntity<CloudEnvironments> getEnvironmentsById(@PathVariable long id) {
        CloudEnvironments environment = null;
        try {
            environment = commonServices.getEnvironmentById(id);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(environment, HttpStatus.OK);
    }

    @RequestMapping(value = "/environments/{type}", method = RequestMethod.GET)
    private ResponseEntity<List<CloudEnvironments>> getAllEnvironmentsByType(@PathVariable int type) {
        List<CloudEnvironments> environments = null;
        try {
            environments = commonServices.getAllEnvironmentsByType(type);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(environments, HttpStatus.OK);
    }
}
