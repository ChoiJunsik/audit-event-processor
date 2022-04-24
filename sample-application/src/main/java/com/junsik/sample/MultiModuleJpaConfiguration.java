package com.junsik.sample;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"com.junsik.sample", "com.junsik.audit.processor"})
@EntityScan(basePackages = {"com.junsik.sample", "com.junsik.audit.processor"})
@Configuration
public class MultiModuleJpaConfiguration {}
