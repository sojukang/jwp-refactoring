package kitchenpos.menu.application;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menu.dao.MenuDao;
import kitchenpos.menu.dao.MenuGroupDao;
import kitchenpos.menu.dao.MenuProductDao;
import kitchenpos.menu.domain.Menu;
import kitchenpos.menu.domain.MenuProduct;
import kitchenpos.menu.ui.request.MenuRequest;
import kitchenpos.product.dao.ProductDao;
import kitchenpos.product.domain.Product;

@Service
@Transactional(readOnly = true)
public class MenuService {

    private final MenuDao menuDao;
    private final MenuGroupDao menuGroupDao;
    private final MenuProductDao menuProductDao;
    private final ProductDao productDao;

    public MenuService(
        final MenuDao menuDao,
        final MenuGroupDao menuGroupDao,
        final MenuProductDao menuProductDao,
        final ProductDao productDao
    ) {
        this.menuDao = menuDao;
        this.menuGroupDao = menuGroupDao;
        this.menuProductDao = menuProductDao;
        this.productDao = productDao;
    }

    @Transactional
    public Menu create(final MenuRequest request) {
        validateMenuGroupExists(request.getMenuGroupId());
        List<MenuProduct> menuProducts = request.getMenuProducts();
        Menu menu = new Menu(request.getName(), request.getPrice(), request.getMenuGroupId());

        Menu savedMenu = menuDao.save(menu);
        savedMenu.addMenuProducts(menuProducts);
        menu.validatePriceUnderProductsSum(getProducts(menuProducts));

        for (final MenuProduct menuProduct : menuProducts) {
            menuProductDao.save(menuProduct);
        }

        return savedMenu;
    }

    private void validateMenuGroupExists(final Long menuGroupId) {
        if (!menuGroupDao.existsById(menuGroupId)) {
            throw new IllegalArgumentException();
        }
    }

    private List<Product> getProducts(final List<MenuProduct> menuProducts) {
        return menuProducts.stream()
            .map(menuProduct -> productDao.getById(menuProduct.getProductId()))
            .collect(Collectors.toUnmodifiableList());
    }

    public List<Menu> list() {
        return menuDao.findAll();
    }
}