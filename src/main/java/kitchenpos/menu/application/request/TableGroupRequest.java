package kitchenpos.menu.application.request;

import java.util.List;

import kitchenpos.order.application.request.OrderTableRequest;

public class TableGroupRequest {

    private List<OrderTableRequest> orderTables;

    private TableGroupRequest() {
    }

    public TableGroupRequest(List<OrderTableRequest> orderTables) {
        this.orderTables = orderTables;
    }

    public List<OrderTableRequest> getOrderTables() {
        return orderTables;
    }
}
