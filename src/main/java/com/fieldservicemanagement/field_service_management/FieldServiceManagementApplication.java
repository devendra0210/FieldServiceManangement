package com.fieldservicemanagement.field_service_management;

import com.fieldservicemanagement.field_service_management.config.prop.JwtProp;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = JwtProp.class)
public class FieldServiceManagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(FieldServiceManagementApplication.class, args);
	}

}
