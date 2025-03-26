package market.retail.shop.repository;

import market.retail.shop.entity.Category;
import market.retail.shop.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
    public Optional<Category> findByName(String name);
}
