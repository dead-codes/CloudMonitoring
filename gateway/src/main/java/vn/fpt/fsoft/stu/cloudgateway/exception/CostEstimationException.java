package vn.fpt.fsoft.stu.cloudgateway.exception;

public class CostEstimationException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public CostEstimationException(String message) {
        super(message);
    }

    public CostEstimationException(String message, Throwable t) {
        super(message, t);
    }

}
