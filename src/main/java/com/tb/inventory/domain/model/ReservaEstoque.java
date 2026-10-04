package com.tb.inventory.domain.model;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Entidade de domínio — Reserva de estoque gerada pelo serviço de inventory.
 * Pura, sem anotações de framework.
 */
public class ReservaEstoque {

    private final PedidoId pedidoId;
    private final List<ItemReservado> itens;
    private final Instant dataReserva;
    private final StatusReserva status;

    private ReservaEstoque(PedidoId pedidoId, List<ItemReservado> itens, Instant dataReserva, StatusReserva status) {
        this.pedidoId = pedidoId;
        this.itens = List.copyOf(itens);
        this.dataReserva = dataReserva;
        this.status = status;
    }

    // --- Factory method ---

    public static ReservaEstoque reservar(PedidoId pedidoId, List<ItemReservado> itens) {
        if (itens == null || itens.isEmpty()) {
            throw new IllegalArgumentException("Itens da reserva não podem ser vazios");
        }
        return new ReservaEstoque(pedidoId, itens, Instant.now(), StatusReserva.RESERVADO);
    }

    public static ReservaEstoque comErro(PedidoId pedidoId) {
        return new ReservaEstoque(pedidoId, List.of(), Instant.now(), StatusReserva.ERRO);
    }

    // --- Getters ---

    public PedidoId pedidoId() { return pedidoId; }
    public List<ItemReservado> itens() { return itens; }
    public Instant dataReserva() { return dataReserva; }
    public StatusReserva status() { return status; }

    public int quantidadeTotalItens() {
        return itens.stream().mapToInt(ItemReservado::quantidade).sum();
    }
}
