package market.retail.shop.repository;

import market.retail.shop.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Optional<Review> findByUserIdAndProductId(Integer userId, Integer productId);
    Optional<Review> findAllByProductId(Integer productId);
}
