package kitchenpos.order.domain;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class OrderTest {

    private final Long noId = null;

    @Test
    @DisplayName("주문을 생성한다.")
    void create() {
        // when, then
        assertDoesNotThrow(this::createOrder);
    }

    @Test
    @DisplayName("OrderLineItem 이 empty 인 경우 예외를 반환한다.")
    void validateOrderLineItems() {
        // given
        OrderTable orderTable = new OrderTable(1, false);

        // when, then
        assertThatThrownBy(() -> new Order(orderTable, List.of()))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MEAL", "COMPLETION"})
    @DisplayName("주문 상태를 변경한다.")
    void changeOrderStatus_meal(String orderStatus) {
        // given
        Order order = createOrder();

        // when
        order.changeStatus(orderStatus);

        // then
        assertThat(order.getOrderStatus()).isEqualTo(orderStatus);
    }

    private Order createOrder() {
        OrderTable orderTable = new OrderTable(1, false);
        OrderLineItem orderLineItem = new OrderLineItem(noId, noId, noId, 1);
        return new Order(orderTable, List.of(orderLineItem));
    }
}