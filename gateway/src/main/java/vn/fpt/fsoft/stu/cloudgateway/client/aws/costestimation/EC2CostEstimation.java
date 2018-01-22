package vn.fpt.fsoft.stu.cloudgateway.client.aws.costestimation;

import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.*;

import vn.fpt.fsoft.stu.cloudgateway.domain.EBS;
import vn.fpt.fsoft.stu.cloudgateway.domain.EC2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class EC2CostEstimation extends AWSCostEstimation {

	public static final String EC2_ON_DEMAND_LINUX_URL = "http://a0.awsstatic.com/pricing/1/ec2/linux-od.min.js";
	public static final String EC2_ON_DEMAND_RHEL_URL = "http://a0.awsstatic.com/pricing/1/ec2/rhel-od.min.js";
	public static final String EC2_ON_DEMAND_SLES_URL = "http://a0.awsstatic.com/pricing/1/ec2/sles-od.min.js";
	public static final String EC2_ON_DEMAND_WINDOWS_URL = "http://a0.awsstatic.com/pricing/1/ec2/mswin-od.min.js";
	public static final String EC2_ON_DEMAND_WINSQL_URL = "http://a0.awsstatic.com/pricing/1/ec2/mswinSQL-od.min.js";
	public static final String EC2_ON_DEMAND_WINSQLWEB_URL = "http://a0.awsstatic.com/pricing/1/ec2/mswinSQLWeb-od.min.js";
	public static final String EC2_ON_DEMAND_WINSQLENT_URL = "http://a0.awsstatic.com/pricing/1/ec2/mswinSQLEnterprise-od.min.js";
	public static final String EC2_EBS_URL = "http://a0.awsstatic.com/pricing/1/ebs/pricing-ebs.min.js";
	public static final String EC2_CLOUD_WATCH_URL = "http://a0.awsstatic.com/pricing/1/cloudwatch/pricing-cloudwatch.min.js";
	public static final String EC2_ELB_URL = "http://a0.awsstatic.com/pricing/1/ec2/pricing-elb.min.js";
	private static final String SERVICE_NAME = "EC2";
	private static final String EC2_ON_DEMAND_PATTERN = "$..regions[?(@.region==''{0}'' || @.region==''{1}'')]..sizes[?(@.size==''{2}'')]..prices.USD";
	private static final String EC2_EBS_PATTERN = "$..regions[?(@.region==''{0}'' || @.region==''{1}'')]..types[?(@.name=='ebsGPSSD')]..prices.USD";
	private static final String EC2_CLOUD_WATCH_DETAILED_MONITORING_PATTERN = "$..regions[?(@.region==''{0}'' || @.region==''{1}'')]..types[?(@.name=='ec2Monitoring')]..prices.USD";
	private static final String EC2_ELB_HOURLY_PATTERN = "$..regions[?(@.region==''{0}'' || @.region==''{1}'')]..values[?(@.rate=='perELBHour')]..prices.USD";
	private static Logger LOGGER = LoggerFactory.getLogger(EC2CostEstimation.class);
	@Autowired
	private AWSCostEstimation awsCostEstimation;

	public double ec2CostEstimation(EC2 ec2) throws IOException {
		return getCostLink(ec2.getOs()) != null ? awsCostEstimation.getCost(getCostLink(ec2.getOs()),
				MessageFormat.format(EC2_ON_DEMAND_PATTERN, ec2.getRegion(), ec2.getRegion(), ec2.getInstanceType()))
				: 0;
	}

	public double ebsCostEstimation(EC2 ec2, EBS ebs) throws Exception {
		return awsCostEstimation.getCost(EC2_EBS_URL,
				MessageFormat.format(EC2_EBS_PATTERN, ec2.getRegion(), ec2.getRegion())) * ebs.getSize();
	}

	public Map<String, Image> getAMIs(AmazonEC2 amazonEC2, List<String> amiIds) {

		Map<String, Image> images = null;
		DescribeImagesRequest request = new DescribeImagesRequest();

		request.setImageIds(amiIds);

		DescribeImagesResult result = amazonEC2.describeImages(request);

		if (result != null) {
			List<Image> lstImage = result.getImages();

			images = lstImage.stream().collect(Collectors.toMap(ami -> ami.getImageId(), ami -> ami));

		} else {
			LOGGER.warn("No images found");
		}

		return images;
	}

	public Map<String, Volume> getVolumes(AmazonEC2 amazonEC2, List<String> volumeIds) {
		Map<String, Volume> volumes = null;
		DescribeVolumesRequest request = new DescribeVolumesRequest();
		request.setVolumeIds(volumeIds);
		DescribeVolumesResult result = amazonEC2.describeVolumes(request);

		if (result != null) {
			List<Volume> lstVols = result.getVolumes();
			volumes = lstVols.stream().collect(Collectors.toMap(vol -> vol.getVolumeId(), vol -> vol));
		} else {
			LOGGER.warn("No volume found");
		}
		return volumes;
	}

	public String getCostLink(String os) {
		switch (os) {
		case "linux":
			return EC2_ON_DEMAND_LINUX_URL;
		case "rhel":
			return EC2_ON_DEMAND_RHEL_URL;
		case "sles":
			return EC2_ON_DEMAND_SLES_URL;
		case "mswin":
			return EC2_ON_DEMAND_WINDOWS_URL;
		case "mswinSQL":
			return EC2_ON_DEMAND_WINSQL_URL;
		case "mswinSQLWeb":
			return EC2_ON_DEMAND_WINSQLWEB_URL;
		case "mswinSQLEnterprise":
			return EC2_ON_DEMAND_WINSQLENT_URL;
		default:
			return EC2_ON_DEMAND_LINUX_URL;
		}
	}
}
