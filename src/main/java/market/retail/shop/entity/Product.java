package market.retail.shop.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {
    public Product(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false, length = 45)
    private String title;
    @Column(name = "description", nullable = false, length = 300)
    private String description;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "stock", nullable = false)
    private Integer stock;
    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Order> orders;
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Review> reviews;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Product)) return false;
        Product product = (Product) o;
        return title.equals(product.title) && description.equals(product.description) &&
                price.equals(product.price) && stock.equals(product.stock) && category.equals(product.category);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", category=" + category +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, stock, category);
    }
}

