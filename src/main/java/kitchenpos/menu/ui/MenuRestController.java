package kitchenpos.menu.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.menu.application.MenuService;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.ui.request.MenuRequest;

@RestController
@RequestMapping("/api/menus")
public class MenuRestController {

    private final MenuService menuService;

    public MenuRestController(final MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public ResponseEntity<Menu> create(@RequestBody final MenuRequest request) {
        final Menu created = menuService.create(request);
        final URI uri = URI.create("/api/menus/" + created.getId());
        return ResponseEntity.created(uri)
            .body(created);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> list() {
        return ResponseEntity.ok()
            .body(menuService.list());
    }
}