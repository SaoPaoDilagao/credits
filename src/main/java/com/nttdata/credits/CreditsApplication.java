package com.nttdata.credits;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Credit application, enable eureka client.
 */
@EnableEurekaClient
@SpringBootApplication
public class CreditsApplication {
  public static void main(String[] args) {
    SpringApplication.run(CreditsApplication.class, args);
  }
}
