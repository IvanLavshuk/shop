package market.retail.shop.service;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateReviewDto;
import market.retail.shop.dto.ReviewDto;
import market.retail.shop.entity.Product;
import market.retail.shop.entity.Review;
import market.retail.shop.entity.User;
import market.retail.shop.mapper.ReviewMapper;
import market.retail.shop.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserService userService;
    private final ProductService productService;

    private final ReviewMapper reviewMapper;

    @Transactional(readOnly = true)
    public List<ReviewDto> findAll() {
        return reviewRepository.
                findAll()
                .stream()
                .map(reviewMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ReviewDto findById(Integer id) {
        return reviewRepository.findById(id).
                map(reviewMapper::toDto).
                orElseThrow(() -> new RuntimeException("Review " + id + " is not found"));
    }

    @Transactional(readOnly = true)
    public List<ReviewDto> findAllByProduct(Integer productId) {
        return reviewRepository.
                findAllByProductId(productId).
                stream().
                map(reviewMapper::toDto).
                collect(Collectors.toList());
    }

    @Transactional
    public ReviewDto update(Integer id, CreateReviewDto createReviewDto) {
        Review review = reviewRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Review " + id + " is not found"));

        Integer idUser = createReviewDto.getIdUser();
        User user = userService.returnEntityById(idUser);
        review.setUser(user);

        Integer idProduct = createReviewDto.getIdProduct();
        Product product = productService.returnEntityById(idProduct);
        review.setProduct(product);

        String text = createReviewDto.getText();
        review.setText(text);
        Double rating = createReviewDto.getRating();
        review.setRating(rating);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    @Transactional
    public ReviewDto create(CreateReviewDto createReviewDto) {
        Integer userId = createReviewDto.getIdUser();
        Integer productId = createReviewDto.getIdProduct();

        User user = userService.returnEntityById(userId);
        Product product = productService.returnEntityById(productId);

        Optional<Review> optionalReview = reviewRepository.findByUserIdAndProductId(userId, productId);
        if (optionalReview.isPresent()) {
            throw new IllegalStateException("Review by this user and product is already exists");
        }
        Review review = reviewMapper.toEntity(createReviewDto);
        review.setProduct(product);
        review.setUser(user);
        Review savedReview = reviewRepository.save(review);
        return reviewMapper.toDto(savedReview);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Review> optionalReviewDto = reviewRepository.findById(id);
        if (optionalReviewDto.isEmpty()) {
            throw new IllegalStateException("Review  does not exist");
        }
        reviewRepository.deleteById(id);
    }


}
