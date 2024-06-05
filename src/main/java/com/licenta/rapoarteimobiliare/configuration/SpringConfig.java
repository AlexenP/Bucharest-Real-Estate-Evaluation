package com.licenta.rapoarteimobiliare.configuration;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan("com.licenta.rapoarteimobiliare")
@EntityScan("com.licenta.rapoarteimobiliare.entities")
@EnableJpaRepositories("com.licenta.rapoarteimobiliare.repositories")
public class SpringConfig {
}
