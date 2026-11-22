package com.z4greed.shared.core.autoconfigure;

import static org.assertj.core.api.Assertions.assertThat;

import com.z4greed.shared.core.exception.Z4GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.context.runner.WebApplicationContextRunner;

class Z4CoreAutoConfigurationTests {

    @Test
    void shouldRegisterTheExceptionHandlerInWebServices() {
        new WebApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(Z4CoreAutoConfiguration.class))
                .run(context -> assertThat(context).hasSingleBean(Z4GlobalExceptionHandler.class));
    }

    @Test
    void shouldSkipTheExceptionHandlerOutsideWebServices() {
        new ApplicationContextRunner()
                .withConfiguration(AutoConfigurations.of(Z4CoreAutoConfiguration.class))
                .run(context -> assertThat(context).doesNotHaveBean(Z4GlobalExceptionHandler.class));
    }
}
