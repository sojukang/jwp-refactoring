package kitchenpos.order.application;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import kitchenpos.common.ServiceTest;
import kitchenpos.menu.ui.request.MenuGroupRequest;
import kitchenpos.menu.ui.request.MenuProductRequest;
import kitchenpos.menu.ui.request.MenuRequest;
import kitchenpos.order.domain.Order;
import kitchenpos.order.domain.OrderStatus;
import kitchenpos.order.ui.request.OrderLineItemRequest;
import kitchenpos.order.ui.request.OrderRequest;
import kitchenpos.order.ui.request.OrderTableRequest;
import kitchenpos.product.ui.request.ProductCreateRequest;

public class OrderServiceTest extends ServiceTest {

    @Test
    @DisplayName("주문을 생성한다.")
    void create() {
        // given
        OrderRequest request = createOrderRequest();

        // when
        Order savedOrder = orderService.create(request);

        // then
        assertThat(savedOrder.getId()).isNotNull();
        assertThat(savedOrder.getOrderStatus()).isEqualTo(OrderStatus.COOKING.name());
        assertThat(savedOrder.getOrderLineItems()).isNotNull();
    }

    @Test
    @DisplayName("전체 주문을 조회한다.")
    void list() {
        // given
        Order savedOrder = orderService.create(createOrderRequest());

        // when
        List<Order> result = orderService.list();

        // then
        assertThat(result).contains(savedOrder);
        assertThat(result).hasSize(1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"MEAL", "COMPLETION"})
    @DisplayName("주문 상태를 변경한다.")
    void changeOrderStatus_meal(String orderStatus) {
        // given
        Order order = orderService.create(createOrderRequest());
        OrderRequest changeRequest = new OrderRequest(NO_ID, NO_ID, orderStatus, null);

        // when
        orderService.changeOrderStatus(order.getId(), changeRequest);
        Order changedOrder = orderDao.findById(order.getId()).orElseThrow();

        // then
        assertThat(changedOrder.getOrderStatus()).isEqualTo(orderStatus);
    }

    private OrderRequest createOrderRequest() {
        MenuGroupRequest menuGroup = new MenuGroupRequest(NO_ID, "세마리메뉴");
        long menuGroupId = menuGroupService.create(menuGroup).getId();

        ProductCreateRequest product = new ProductCreateRequest("후라이드", BigDecimal.valueOf(16000));
        long productId = productService.create(product).getId();

        MenuRequest menu = new MenuRequest(NO_ID, "후라이드+후라이드+후라이드", new BigDecimal(48000), menuGroupId,
            List.of(new MenuProductRequest(NO_ID, NO_ID, productId, 3)));
        long menuId = menuService.create(menu).getId();

        OrderTableRequest orderTable = new OrderTableRequest(NO_ID, NO_ID, 1, false);
        long tableId = tableService.create(orderTable).getId();

        OrderLineItemRequest orderLineItem = new OrderLineItemRequest(1L, NO_ID, menuId, 1);
        return new OrderRequest(NO_ID, tableId, OrderStatus.COOKING.name(), List.of(orderLineItem));
    }
}