package kitchenpos.application;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.application.request.OrderTableCreateRequest;
import kitchenpos.application.request.TableGroupCreateRequest;
import kitchenpos.dao.OrderDao;
import kitchenpos.dao.OrderTableDao;
import kitchenpos.dao.TableGroupDao;
import kitchenpos.domain.OrderStatus;
import kitchenpos.domain.OrderTable;
import kitchenpos.domain.TableGroup;

@Service
@Transactional(readOnly = true)
public class TableGroupService {

    private final OrderDao orderDao;
    private final OrderTableDao orderTableDao;
    private final TableGroupDao tableGroupDao;

    public TableGroupService(final OrderDao orderDao, final OrderTableDao orderTableDao,
        final TableGroupDao tableGroupDao) {
        this.orderDao = orderDao;
        this.orderTableDao = orderTableDao;
        this.tableGroupDao = tableGroupDao;
    }

    @Transactional
    public TableGroup create(final TableGroupCreateRequest request) {
        List<Long> orderTableIds = getOrderTableIds(request);
        List<OrderTable> orderTables = orderTableDao.findAllByIdIn(orderTableIds);

        return tableGroupDao.save(new TableGroup(orderTables));
    }

    @Transactional
    public void ungroup(final Long tableGroupId) {
        TableGroup tableGroup = getTableGroup(tableGroupId);
        List<Long> orderTableIds = getOrderTableIds(tableGroup);
        validateOrdersNotCompletion(orderTableIds);

        tableGroup.ungroupOrderTables();
    }

    private TableGroup getTableGroup(final Long tableGroupId) {
        return tableGroupDao.findById(tableGroupId)
            .orElseThrow(() -> new IllegalArgumentException("table group not found"));
    }

    private List<Long> getOrderTableIds(final TableGroupCreateRequest request) {
        return request.getOrderTables().stream()
            .map(OrderTableCreateRequest::getId)
            .collect(Collectors.toUnmodifiableList());
    }

    private List<Long> getOrderTableIds(final TableGroup tableGroup) {
        return tableGroup.getOrderTables().stream()
            .map(OrderTable::getId)
            .collect(Collectors.toUnmodifiableList());
    }

    private void validateOrdersNotCompletion(final List<Long> orderTableIds) {
        if (orderDao.existsByOrderTableIdInAndOrderStatusIn(orderTableIds,
            Arrays.asList(OrderStatus.COOKING.name(), OrderStatus.MEAL.name()))) {
            throw new IllegalArgumentException();
        }
    }
}
