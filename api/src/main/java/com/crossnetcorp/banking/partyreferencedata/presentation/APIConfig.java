package com.crossnetcorp.banking.partyreferencedata.presentation;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 *
 * @author ianache
 */
@Configuration
@ComponentScan(basePackages = "com.crossnetcorp.banking.partyreferencedata.application.*,com.crossnetcorp.banking.partyreferencedata.domain.*,com.crossnetcorp.banking.partyreferencedata.infrastructure.*")
@EnableJpaRepositories(basePackages = {"com.crossnetcorp.banking.partyreferencedata.infrastructure.*"})
@EntityScan("com.crossnetcorp.banking.partyreferencedata.infrastructure.*")
public class APIConfig {
    
}
