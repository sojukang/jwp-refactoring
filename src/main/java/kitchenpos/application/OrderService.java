package kitchenpos.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.domain.Order;
import kitchenpos.domain.OrderLineItem;
import kitchenpos.domain.OrderTable;

@Service
public class OrderService {

    private final OrderDao orderDao;
    private final OrderTableDao orderTableDao;

    public OrderService(
        final OrderDao orderDao,
        final OrderTableDao orderTableDao
    ) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
    }

    @Transactional
    public Order create(final Order request) {
        final List<OrderLineItem> orderLineItems = request.getOrderLineItems();

        final OrderTable orderTable = orderTableDao.findById(request.getOrderTableId())
            .orElseThrow(IllegalArgumentException::new);

        if (orderTable.isEmpty()) {
            throw new IllegalArgumentException();
        }

        final Order savedOrder = orderDao.save(new Order(orderTable.getId(), orderLineItems));

        savedOrder.setIdToOrderLineItems();
        return savedOrder;
    }

    public List<Order> list() {
        return orderDao.findAll();
    }

    @Transactional
    public Order changeOrderStatus(final Long orderId, final Order order) {
        final Order savedOrder = orderDao.findById(orderId)
            .orElseThrow(IllegalArgumentException::new);

        savedOrder.changeStatus(order.getOrderStatus());
        return savedOrder;
    }
}
