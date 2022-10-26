package kitchenpos.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 통합 계산을 위해 개별 OrderTable 을 그룹화하는 객체
 */
public class TableGroup {

    private Long id;
    private LocalDateTime createdDate;
    private List<OrderTable> orderTables = new ArrayList<>();

    public TableGroup() {
    }

    public TableGroup(final List<OrderTable> orderTables) {
        this.orderTables = List.copyOf(orderTables);
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(final LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<OrderTable> getOrderTables() {
        return List.copyOf(orderTables);
    }

    public void setOrderTables(final List<OrderTable> orderTables) {
        this.orderTables = List.copyOf(orderTables);
    }
}
