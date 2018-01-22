package vn.fpt.fsoft.stu.cloudgateway.client.aws.costestimation;

import vn.fpt.fsoft.stu.cloudgateway.domain.Region;

import java.io.IOException;
import java.util.List;

public interface CostEstimation {

	String getServiceName();

	public List<CostEstimationDetail> estimateDetailHourlyCost(Region region) throws IOException;

}
