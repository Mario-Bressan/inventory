package com.tb.inventory.config;

import com.tb.inventory.application.port.in.ReservarEstoqueUseCase;
import com.tb.inventory.application.service.ReservarEstoqueService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração de beans da Arquitetura Hexagonal.
 * Único ponto de wiring — o resto do código não sabe que Spring existe.
 */
@Configuration
public class InventoryBeanConfig {

    @Bean
    public ReservarEstoqueUseCase reservarEstoqueUseCase() {
        return new ReservarEstoqueService();
    }
}
