package com.tb.inventory.application.port.in;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command de entrada — dados necessários para reservar estoque.
 * Record puro, sem validação de framework.
 */
public record ReservarEstoqueCommand(
    String pedidoId,
    List<ItemCommand> itens
) {

    public record ItemCommand(
        String produto,
        int quantidade,
        BigDecimal precoUnitario
    ) {}
}
