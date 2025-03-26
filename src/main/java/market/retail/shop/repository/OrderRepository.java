package market.retail.shop.repository;

import market.retail.shop.entity.Order;
import market.retail.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Optional<Review> findByUserIdAndProductId(Integer userId, Integer productId);
}
