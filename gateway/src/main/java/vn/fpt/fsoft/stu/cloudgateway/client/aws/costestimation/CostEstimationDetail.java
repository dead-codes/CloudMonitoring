package vn.fpt.fsoft.stu.cloudgateway.client.aws.costestimation;

public class CostEstimationDetail {

	private String description;

	private double cost;

	public CostEstimationDetail(String description, double cost) {
		super();
		this.description = description;
		this.cost = cost;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
