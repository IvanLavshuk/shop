package market.retail.shop.mapper;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateReviewDto;
import market.retail.shop.dto.ReviewDto;
import market.retail.shop.entity.Product;
import market.retail.shop.entity.Review;
import market.retail.shop.entity.User;
import market.retail.shop.repository.ProductRepository;
import market.retail.shop.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewMapper {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public ReviewDto toDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setIdProduct(review.getProduct().getId());
        reviewDto.setIdUser(review.getUser().getId());
        reviewDto.setRating(review.getRating());
        reviewDto.setText(review.getText());
        return reviewDto;
    }

    public Review toEntity(CreateReviewDto createReviewDto) {
        Review review = new Review();
        review.setRating(createReviewDto.getRating());
        review.setText(createReviewDto.getText());
        Integer idProduct = createReviewDto.getIdProduct();
        Product product = productRepository.findById(idProduct).
                orElseThrow(() -> new IllegalArgumentException("Product not found with id: " + idProduct));
        review.setProduct(product);
        Integer idUser = createReviewDto.getIdUser();
        User user = userRepository.findById(idUser).
                orElseThrow(() -> new IllegalArgumentException("User not found with id: " + idUser));
        review.setUser(user);
        return review;

    }
}
