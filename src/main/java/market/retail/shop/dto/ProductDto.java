package market.retail.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String title;
    private String description;
    private Double price;
    private Integer stock;
    private Integer idCategory;
}
