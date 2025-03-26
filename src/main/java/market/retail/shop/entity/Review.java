package market.retail.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "rating", nullable = false)
    private Double rating;
    @Column(name = "text", nullable = false, length = 300)
    private String text;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_product")
    private Product product;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Review)) return false;
        Review review = (Review) o;
        return  rating.equals(review.rating) && text.equals(review.text) &&
                user.equals(review.user) && product.equals(review.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rating, text, user, product);
    }
}
