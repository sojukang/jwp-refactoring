package kitchenpos.order.dao;

import java.util.List;
import java.util.Optional;

import kitchenpos.order.domain.Order;

public interface OrderDao {

    Order save(Order entity);

    Optional<Order> findById(Long id);

    Order getById(Long id);

    List<Order> findAll();

    boolean existsByOrderTableIdAndOrderStatusIn(Long orderTableId, List<String> orderStatuses);

    boolean existsByOrderTableIdInAndOrderStatusIn(List<Long> orderTableIds, List<String> orderStatuses);
}