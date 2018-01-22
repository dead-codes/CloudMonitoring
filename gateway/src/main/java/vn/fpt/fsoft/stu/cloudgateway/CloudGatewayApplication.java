package vn.fpt.fsoft.stu.cloudgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@EnableAutoConfiguration
@ComponentScan("vn.fpt.fsoft.stu")
@SpringBootApplication
public class CloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudGatewayApplication.class, args);
	}
}
