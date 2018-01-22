package vn.fpt.fsoft.stu.cloudgateway.client.aws;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import vn.fpt.fsoft.stu.cloudgateway.client.CloudClient;
import vn.fpt.fsoft.stu.cloudgateway.client.aws.costestimation.EC2CostEstimation;
import vn.fpt.fsoft.stu.cloudgateway.domain.EBS;
import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;
import vn.fpt.fsoft.stu.cloudgateway.domain.State;
import vn.fpt.fsoft.stu.cloudgateway.utils.CloudConfigurationUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class AWSClient implements CloudClient {

    private static Logger LOGGER = LoggerFactory.getLogger(AWSClient.class);

    @Autowired
    private CloudConfigurationUtils clientConfiguration;

    @Autowired
    private EC2CostEstimation ec2CostEstimation;

    @Override
    public List<vn.fpt.fsoft.stu.cloudgateway.domain.Region> getAllRegions() {
        List<vn.fpt.fsoft.stu.cloudgateway.domain.Region> regions = null;
        try {
            List<Region> AWSRegions = RegionUtils.getRegions();
            if (AWSRegions != null && AWSRegions.size() > 0) {
                regions = new ArrayList<>();
                vn.fpt.fsoft.stu.cloudgateway.domain.Region r = null;
                for (Region region : AWSRegions) {
                    if ((!region.getName().toLowerCase().contains("gov")) && (!region.getName().toLowerCase().contains("sa")) && (!region.getName().toLowerCase().contains("cn"))) {
                        r = new vn.fpt.fsoft.stu.cloudgateway.domain.Region(region.getName(), region.getPartition());
                        regions.add(r);
                    }
                }
            }
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
        }
        return regions;
    }

    @Override
    public List<EC2> getAllInstanceByRegion(String accessKey, String privateKey, String region) {
        List<EC2> ec2s = null;
        List<String> amiIds = null;
        List<String> volumeIds = null;
        List<String> lstVolumeIdByEC2 = null;
        if ("".equals(region.trim())) {
            LOGGER.error("Region cannot empty");
            return null;
        }

        try {
            AWSCredentials credentials = new BasicAWSCredentials(accessKey, privateKey);

            AmazonEC2 amazonEC2 = AmazonEC2Client.builder().withRegion(region).withClientConfiguration(clientConfiguration.defaultAWSConfiguration()).withCredentials(new AWSStaticCredentialsProvider(credentials)).build();
            DescribeInstancesRequest request = new DescribeInstancesRequest();
            DescribeInstancesResult describeInstancesResult = amazonEC2.describeInstances(request);
            try {
                List<Reservation> reservations = describeInstancesResult.getReservations();
                EC2 ec2 = null;
                State state = null;
                List<InstanceBlockDeviceMapping> lstEBS = null;

                for (Reservation reservation : reservations) {
                    for (com.amazonaws.services.ec2.model.Instance instance : reservation.getInstances()) {
                        ec2 = new EC2();
                        state = new State();
                        lstEBS = instance.getBlockDeviceMappings();
                        if (lstEBS != null && lstEBS.size() > 0) {
                            if (volumeIds == null) {
                                volumeIds = new ArrayList<>();
                            }
                            lstVolumeIdByEC2 = new ArrayList<>();
                            for (InstanceBlockDeviceMapping ebs : lstEBS) {
                                volumeIds.add(ebs.getEbs().getVolumeId());
                                lstVolumeIdByEC2.add(ebs.getEbs().getVolumeId());
                            }
                        }
                        ec2.setVolumeIds(lstVolumeIdByEC2);
                        ec2.setAmiId(instance.getImageId());
                        ec2.setId(instance.getInstanceId());
                        ec2.setStartDate(instance.getLaunchTime());
                        ec2.setPublicIp(instance.getPublicIpAddress());
                        ec2.setPublicDNS(instance.getPublicDnsName());
                        ec2.setSubnetId(instance.getSubnetId());
                        ec2.setVpcId(instance.getVpcId());
                        ec2.setRegion(region);
                        ec2.setInstanceType(instance.getInstanceType());
                        state.setCode(instance.getState().getCode());
                        state.setValue(instance.getState().getName());
                        ec2.setState(state);
                        if (ec2s == null) {
                            ec2s = new ArrayList<>();
                        }
                        if (amiIds == null) {
                            amiIds = new ArrayList<>();
                        }
                        amiIds.add(instance.getImageId());
                        ec2s.add(ec2);
                    }
                }
            } catch (Exception e) {
                if (e.getCause() instanceof AmazonServiceException) {
                    LOGGER.error("Unable to get instances for region " + region);
                } else {
                    throw e;
                }
            }

            if (amiIds != null && amiIds.size() > 0) {
                Map<String, Image> imagesInfo = ec2CostEstimation.getAMIs(amazonEC2, amiIds);
                if (imagesInfo != null && imagesInfo.size() > 0) {
                    Image img = null;
                    for (EC2 ec2 : ec2s) {
                        img = imagesInfo.get(ec2.getAmiId());
                        if (img != null) {
                            if (img.getPlatform() != null && img.getPlatform().equalsIgnoreCase("windows")) {
                                if (img.getName().toLowerCase().contains("sql")) {
                                    if (img.getName().toLowerCase().contains("web")) {
                                        ec2.setOs("mswinSQLWeb");
                                    } else if (img.getName().toLowerCase().contains("standard")) {
                                        ec2.setOs("mswinSQL");
                                    } else if (img.getName().toLowerCase().contains("enterprise")) {
                                        ec2.setOs("mswinSQLEnterprise");
                                    }
                                } else {
                                    ec2.setOs("mswin");
                                }
                            } else {
                                if (img.getName().toLowerCase().contains("ubuntu") || (img.getName().toLowerCase().contains("amzn") && (img.getName().toLowerCase().contains("ami")))) {
                                    ec2.setOs("linux");
                                } else if (img.getName().toLowerCase().contains("suse") || img.getName().toLowerCase().contains("sles")) {
                                    ec2.setOs("sles");
                                } else if (img.getName().toLowerCase().contains("rhel")) {
                                    ec2.setOs("rhel");
                                } else {
                                    ec2.setOs("unknown");
                                }
                            }
                        } else {
                            ec2.setOs("unknown");
                        }
                        ec2.setRate(ec2CostEstimation.ec2CostEstimation(ec2));
                    }
                }
            }
            Double cost = 0d;
            if (volumeIds != null && volumeIds.size() > 0) {
                Map<String, Volume> volumesInfo = ec2CostEstimation.getVolumes(amazonEC2, volumeIds);
                List<EBS> EBSs = null;
                Volume volume = null;
                EBS ebs = null;
                if (volumesInfo != null && volumesInfo.size() > 0) {
                    for (EC2 ec2 : ec2s) {
                        if (ec2.getVolumeIds() != null && ec2.getVolumeIds().size() > 0) {

                            EBSs = new ArrayList<>();

                            for (String volumeId : ec2.getVolumeIds()) {
                                volume = volumesInfo.get(volumeId);
                                ebs = new EBS();
                                ebs.setId(volumeId);
                                ebs.setAz(volume.getAvailabilityZone());
                                ebs.setIops(volume.getIops());
                                ebs.setSize(volume.getSize());
                                ebs.setState(volume.getState());
                                ebs.setType(volume.getVolumeType());
                                ebs.setPrice(ec2CostEstimation.ebsCostEstimation(ec2, ebs));
                                EBSs.add(ebs);
                            }
                        }
                        ec2.setEbs(EBSs);
                    }
                }
            }
        } catch (AmazonClientException ae) {
            LOGGER.error(ae.getMessage(), ae);
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        }
        return ec2s;
    }
}