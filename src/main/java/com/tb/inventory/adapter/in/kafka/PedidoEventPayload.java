package com.tb.inventory.adapter.in.kafka;

import com.tb.inventory.application.port.in.ReservarEstoqueCommand;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * DTO de evento Kafka — contrato externo da mensageria.
 * Desserializado pelo Jackson. Totalmente separado do domínio.
 */
record PedidoEventPayload(
    UUID id,
    String clienteId,
    List<ItemPayload> itens,
    Instant dataCriacao,
    String status,
    BigDecimal valorTotal
) {

    record ItemPayload(
        String produto,
        int quantidade,
        BigDecimal precoUnitario,
        BigDecimal subtotal
    ) {}

    /**
     * Mapeia o DTO de evento para o Command do caso de uso.
     */
    ReservarEstoqueCommand toCommand() {
        List<ReservarEstoqueCommand.ItemCommand> itensCommand = itens.stream()
            .map(i -> new ReservarEstoqueCommand.ItemCommand(i.produto(), i.quantidade(), i.precoUnitario()))
            .toList();

        return new ReservarEstoqueCommand(id.toString(), itensCommand);
    }
}
