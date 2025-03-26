package market.retail.shop.service;

import lombok.RequiredArgsConstructor;
import market.retail.shop.entity.Product;
import market.retail.shop.entity.User;
import market.retail.shop.mapper.ProductMapper;
import market.retail.shop.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    @Transactional(readOnly = true)
    public Product returnEntityById(Integer id){
        return productRepository.findById(id).
                orElseThrow(() -> new RuntimeException("Product " + id + " is not found"));
    }
}
