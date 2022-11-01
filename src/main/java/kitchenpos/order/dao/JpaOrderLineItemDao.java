package kitchenpos.order.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import kitchenpos.order.dao.repository.JpaOrderLineItemRepository;
import kitchenpos.order.domain.OrderLineItem;

@Primary
@Repository
public class JpaOrderLineItemDao implements OrderLineItemDao {

    private final JpaOrderLineItemRepository orderLineItemRepository;

    public JpaOrderLineItemDao(final JpaOrderLineItemRepository orderLineItemRepository) {
        this.orderLineItemRepository = orderLineItemRepository;
    }

    @Override
    public OrderLineItem save(final OrderLineItem entity) {
        return orderLineItemRepository.save(entity);
    }

    @Override
    public Optional<OrderLineItem> findById(final Long id) {
        return orderLineItemRepository.findById(id);
    }

    @Override
    public List<OrderLineItem> findAll() {
        return orderLineItemRepository.findAll();
    }

    @Override
    public List<OrderLineItem> findAllByOrderId(final Long orderId) {
        return orderLineItemRepository.findAllByOrderId(orderId);
    }
}