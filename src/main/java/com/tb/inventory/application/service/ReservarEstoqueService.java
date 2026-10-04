package com.tb.inventory.application.service;

import com.tb.inventory.application.port.in.ReservarEstoqueCommand;
import com.tb.inventory.application.port.in.ReservarEstoqueUseCase;
import com.tb.inventory.domain.model.ItemReservado;
import com.tb.inventory.domain.model.PedidoId;
import com.tb.inventory.domain.model.ReservaEstoque;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Implementação do caso de uso ReservarEstoque.
 * Regras de negócio puras, sem dependência de framework.
 * Mantém contador de itens reservados em memória (thread-safe).
 */
public class ReservarEstoqueService implements ReservarEstoqueUseCase {

    private static final Logger log = LoggerFactory.getLogger(ReservarEstoqueService.class);

    private final AtomicInteger totalItensReservados = new AtomicInteger(0);

    @Override
    public ReservaEstoque reservar(ReservarEstoqueCommand command, int partition, long offset) {
        // 1. Mapear command → value objects de domínio
        PedidoId pedidoId = PedidoId.de(command.pedidoId());

        List<ItemReservado> itens = command.itens().stream()
            .map(item -> new ItemReservado(item.produto(), item.quantidade(), item.precoUnitario()))
            .toList();

        // 2. Criar entidade de domínio
        ReservaEstoque reserva = ReservaEstoque.reservar(pedidoId, itens);

        // 3. Acumular total de itens reservados (thread-safe)
        int acumulado = totalItensReservados.addAndGet(reserva.quantidadeTotalItens());

        // 4. Log estruturado
        log.info("RESERVA | pedidoId={} | itens={} | qtdReservada={} | partition={} | offset={} | totalItensAcumulado={}",
            reserva.pedidoId().valor(),
            reserva.itens().stream().map(i -> i.produto() + " x" + i.quantidade()).toList(),
            reserva.quantidadeTotalItens(),
            partition,
            offset,
            acumulado
        );

        return reserva;
    }
}
