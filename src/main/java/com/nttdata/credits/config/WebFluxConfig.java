package com.nttdata.credits.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * WebFlux configuration.
 */
@Configuration
@EnableWebFlux
public class WebFluxConfig  implements WebFluxConfigurer {
}
