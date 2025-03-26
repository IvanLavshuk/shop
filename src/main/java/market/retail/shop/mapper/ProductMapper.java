package market.retail.shop.mapper;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateProductDto;
import market.retail.shop.dto.ProductDto;
import market.retail.shop.entity.Category;
import market.retail.shop.entity.Product;
import market.retail.shop.repository.CategoryRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private  final CategoryRepository categoryRepository;

    public ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setDescription(product.getDescription());
        productDto.setIdCategory(product.getCategory().getId());
        productDto.setPrice(product.getPrice());
        productDto.setStock(product.getStock());
        productDto.setTitle(product.getTitle());
        return productDto;
    }

    public Product toEntity(CreateProductDto createProductDto) {
        Product product = new Product();
        Integer idCategory = createProductDto.getIdCategory();
        Category category = categoryRepository.findById(idCategory).
                orElseThrow(()-> new IllegalStateException("Product not found with id: " + idCategory));
        product.setCategory(category);
        product.setDescription(createProductDto.getDescription());
        product.setPrice(createProductDto.getPrice());
        product.setStock(createProductDto.getStock());
        product.setTitle(createProductDto.getTitle());
        return product;
    }
}
