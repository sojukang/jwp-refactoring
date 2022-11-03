package kitchenpos.menu.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import kitchenpos.menu.dao.repository.JpaMenuProductRepository;
import kitchenpos.menu.domain.MenuProduct;

@Component
public class JpaMenuProductDao implements MenuProductDao {

    private final JpaMenuProductRepository menuProductRepository;

    public JpaMenuProductDao(final JpaMenuProductRepository menuProductRepository) {
        this.menuProductRepository = menuProductRepository;
    }

    @Override
    public MenuProduct save(final MenuProduct entity) {
        return menuProductRepository.save(entity);
    }

    @Override
    public Optional<MenuProduct> findById(final Long id) {
        return menuProductRepository.findById(id);
    }

    @Override
    public List<MenuProduct> findAll() {
        return menuProductRepository.findAll();
    }

    @Override
    public List<MenuProduct> findAllByMenuId(final Long menuId) {
        return menuProductRepository.findAllByMenuId(menuId);
    }
}
