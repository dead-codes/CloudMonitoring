package vn.fpt.fsoft.stu.cloudgateway.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;
import vn.fpt.fsoft.stu.cloudgateway.services.EC2Services;
import vn.fpt.fsoft.stu.cloudgateway.utils.EncryptUtils;

import java.util.List;

@RestController
@RequestMapping("/api/ec2")
public class EC2Controller {

    private static Logger LOGGER = LoggerFactory.getLogger(EC2Controller.class);

    @Autowired
    private EC2Services ec2Services;

    @Autowired
    private EncryptUtils encryptUtils;

    @RequestMapping(value = "/{region}", method = RequestMethod.GET)
    private ResponseEntity<List<EC2>> getAllEC2InstanceByRegion(@RequestParam(value = "a", required = true) String accessKey,
                                                                @RequestParam(value = "s", required = true) String secretKey,
                                                                @PathVariable String region) {
        List<EC2> instances = null;
        try {
            String ak = null;
            String sk = null;
            if (accessKey != null && secretKey != null && (!"".equals(accessKey)) && (!"".equals(secretKey))) {
                try {
                    ak = encryptUtils.base64Decrypt(accessKey);
                    sk = encryptUtils.base64Decrypt(secretKey);
                } catch (Exception ex) {
                    LOGGER.error(ex.getMessage(), ex);
                }
                instances = ec2Services.getAllEC2InstanceInRegion(ak, sk, region);
            } else {
                throw new Exception("Key cannot be null");
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(instances, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET)
    private ResponseEntity<List<EC2>> getAllEC2Instance(@RequestParam(value = "a", required = true) String accessKey,
                                                        @RequestParam(value = "s", required = true) String secretKey) {
        List<EC2> instances = null;
        try {
            String ak = null;
            String sk = null;
            if (accessKey != null && secretKey != null && (!"".equals(accessKey)) && (!"".equals(secretKey))) {
                ak = encryptUtils.base64Decrypt(accessKey);
                sk = encryptUtils.base64Decrypt(secretKey);
                instances = ec2Services.getAllEC2Instance(ak, sk);
            } else {
                throw new Exception("Key cannot be null");
            }

        } catch (Exception ex) {
            LOGGER.error(ex.getMessage());
        }
        return new ResponseEntity<>(instances, HttpStatus.OK);
    }
}
