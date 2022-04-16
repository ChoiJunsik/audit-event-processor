package com.junsik.chat;

import com.junsik.audit.entity.producer.AuditEntityProducerConfiguration;
import java.util.TimeZone;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@EnableAsync
@Import({AuditEntityProducerConfiguration.class})
@SpringBootApplication
public class ChatServerApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
		new SpringApplicationBuilder()
				.sources(ChatServerApplication.class)
				.web(WebApplicationType.SERVLET)
				.build()
				.run(args);
	}
}