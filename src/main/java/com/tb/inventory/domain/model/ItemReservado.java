package com.tb.inventory.domain.model;

import java.math.BigDecimal;

/**
 * Value Object — Item reservado do pedido.
 * Puro, sem anotações de framework.
 */
public record ItemReservado(
    String produto,
    int quantidade,
    BigDecimal precoUnitario
) {

    public ItemReservado {
        if (produto == null || produto.isBlank()) {
            throw new IllegalArgumentException("produto não pode ser nulo ou vazio");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser positiva");
        }
        if (precoUnitario == null || precoUnitario.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("precoUnitario deve ser positivo");
        }
    }

    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}
