package market.retail.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Integer id;
    private Double rating;
    private String text;
    private Integer idUser;
    private Integer idProduct;
}
