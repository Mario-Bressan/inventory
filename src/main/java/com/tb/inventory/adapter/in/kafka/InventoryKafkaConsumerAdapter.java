package com.tb.inventory.adapter.in.kafka;

import com.tb.inventory.application.port.in.ReservarEstoqueCommand;
import com.tb.inventory.application.port.in.ReservarEstoqueUseCase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Adaptador Inbound — Kafka Consumer.
 * Recebe eventos do Kafka e os converte para chamadas ao caso de uso.
 * Único ponto da aplicação com anotações de framework (@KafkaListener, @Component).
 */
@Component
public class InventoryKafkaConsumerAdapter {

    private static final Logger log = LoggerFactory.getLogger(InventoryKafkaConsumerAdapter.class);

    private final ReservarEstoqueUseCase reservarEstoqueUseCase;

    public InventoryKafkaConsumerAdapter(ReservarEstoqueUseCase reservarEstoqueUseCase) {
        this.reservarEstoqueUseCase = reservarEstoqueUseCase;
    }

    @KafkaListener(topics = "tb-pedidos-v1", groupId = "tb-inventory-group")
    public void consumir(ConsumerRecord<String, PedidoEventPayload> record) {
        PedidoEventPayload payload = record.value();

        log.debug("Evento recebido: pedidoId={}, partition={}, offset={}",
            payload.id(), record.partition(), record.offset());

        // Mapeia DTO Kafka → Command → Use Case
        ReservarEstoqueCommand command = payload.toCommand();
        reservarEstoqueUseCase.reservar(command, record.partition(), record.offset());
    }
}
