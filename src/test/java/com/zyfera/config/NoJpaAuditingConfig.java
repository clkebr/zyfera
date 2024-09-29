package com.zyfera.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@TestConfiguration
public class NoJpaAuditingConfig {

  // Overriding the AuditorAware bean
  @Bean
  @Primary
  public AuditorAware<Long> auditorAware() {
    return () -> java.util.Optional.of(1L);
  }

  // disable Auditing
  @Bean
  @Primary
  public AuditingEntityListener auditingEntityListener() {
    return new AuditingEntityListener();
  }
}
