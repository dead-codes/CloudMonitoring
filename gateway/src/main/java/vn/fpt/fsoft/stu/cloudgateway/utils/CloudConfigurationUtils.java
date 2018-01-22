package vn.fpt.fsoft.stu.cloudgateway.utils;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.Protocol;
import org.springframework.stereotype.Component;

@Component
public class CloudConfigurationUtils {

    private ClientConfiguration clientConfiguration;

    public CloudConfigurationUtils() {
    }

    public ClientConfiguration defaultAWSConfiguration() {
        if (clientConfiguration == null) {
            clientConfiguration = new ClientConfiguration();

            clientConfiguration.setProtocol(Protocol.HTTPS);

        }

        return clientConfiguration;
    }
}
