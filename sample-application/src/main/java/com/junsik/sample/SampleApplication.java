package com.junsik.sample;

import com.junsik.audit.processor.consumer.AuditEventConsumerConfiguration;
import java.util.TimeZone;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@Import(AuditEventConsumerConfiguration.class)
@EnableJpaAuditing
@EnableAsync
@SpringBootApplication
public class SampleApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		new SpringApplicationBuilder()
				.sources(SampleApplication.class)
				.web(WebApplicationType.SERVLET)
				.build()
				.run(args);
	}
}
