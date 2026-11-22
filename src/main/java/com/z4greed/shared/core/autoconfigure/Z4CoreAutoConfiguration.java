package com.z4greed.shared.core.autoconfigure;

import com.z4greed.shared.core.exception.Z4GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;

// Spring Boot la carga sola al arrancar el servicio (ver META-INF/spring/...AutoConfiguration.imports).
// El servicio no necesita @ComponentScan ni @Import para usar el manejador de errores.
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class Z4CoreAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public Z4GlobalExceptionHandler z4GlobalExceptionHandler() {
        return new Z4GlobalExceptionHandler();
    }
}
