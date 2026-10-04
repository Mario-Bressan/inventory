package com.tb.inventory.application.port.in;

import com.tb.inventory.domain.model.ReservaEstoque;

/**
 * Inbound Port — Caso de uso: Reservar Estoque.
 * Chamado pelo adaptador de entrada (Kafka Consumer).
 */
public interface ReservarEstoqueUseCase {

    /**
     * Processa a reserva de estoque para um pedido recebido via mensageria.
     *
     * @param command dados do pedido recebido
     * @param partition partição Kafka de origem
     * @param offset offset da mensagem
     * @return a reserva gerada
     */
    ReservaEstoque reservar(ReservarEstoqueCommand command, int partition, long offset);
}
