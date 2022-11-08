package kitchenpos.order.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "order_menu")
public class OrderMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "menu_id")
    private Long menuId;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private BigDecimal price;

    protected OrderMenu() {
    }

    public OrderMenu(final Long id, final Long menuId, final LocalDateTime createdTime, final String name,
        final BigDecimal price) {
        this.id = id;
        this.menuId = menuId;
        this.createdTime = createdTime;
        this.name = name;
        this.price = price;
    }

    public OrderMenu(final Long menuId, final String name, final BigDecimal price) {
        this(null, menuId, LocalDateTime.now(), name, price);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
