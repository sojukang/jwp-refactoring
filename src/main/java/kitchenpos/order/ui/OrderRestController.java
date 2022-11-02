package kitchenpos.order.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.order.application.OrderService;
import kitchenpos.order.application.response.OrderResponse;
import kitchenpos.order.domain.Order;
import kitchenpos.order.application.request.OrderRequest;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    private final OrderService orderService;

    public OrderRestController(final OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponse> create(@RequestBody final OrderRequest request) {
        final OrderResponse created = orderService.create(request);
        final URI uri = URI.create("/api/orders/" + created.getId());
        return ResponseEntity.created(uri)
            .body(created);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> list() {
        return ResponseEntity.ok()
            .body(orderService.list());
    }

    @PutMapping("/{orderId}/order-status")
    public ResponseEntity<OrderResponse> changeOrderStatus(
        @PathVariable final Long orderId,
        @RequestBody final OrderRequest request) {
        return ResponseEntity.ok(orderService.changeOrderStatus(orderId, request));
    }
}
