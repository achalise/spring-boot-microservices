package com.example;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableZuulProxy
public class UserServiceClientApplication {

	@Bean
	CommandLineRunner runner(DiscoveryClient dc) {
		return args ->
				dc.getInstances("user-service")
						.forEach(si -> System.out.println(String.format(
								"%s %s:%s", si.getServiceId(), si.getHost(), si.getPort())));
	}

	public static void main(String[] args) {
		SpringApplication.run(UserServiceClientApplication.class, args);
	}

	@FeignClient(name = "user-service")
	interface UserServiceClient {

		@RequestMapping(method = RequestMethod.GET, value = "/user")
		public List<User> userNames();
	}



	@RestController
	class UserServiceAPIGateway {
		@Autowired
		private UserServiceClient userServiceClient;

		public List<String> getUserNamesFallBack() {
			return Arrays.asList("User 1", "User 2", "User 3");
		}

		@HystrixCommand(fallbackMethod = "getUserNamesFallBack")
		@RequestMapping(method = RequestMethod.GET, value = "/usernames")
		public List<String> userNames() {
			return userServiceClient.userNames()
					.stream()
					.map(a->a.firstName)
					.collect(Collectors.toList());
		}
	}

}
