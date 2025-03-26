package market.retail.shop.mapper;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateOrderDto;
import market.retail.shop.dto.OrderDto;
import market.retail.shop.entity.Order;
import market.retail.shop.entity.Product;
import market.retail.shop.entity.User;
import market.retail.shop.repository.ProductRepository;
import market.retail.shop.repository.UserRepository;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OrderMapper {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderDto toDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setAmount(order.getAmount());
        orderDto.setIdProduct(order.getProduct().getId());
        orderDto.setIdUser(order.getUser().getId());
        return orderDto;
    }


    public Order toEntity(CreateOrderDto createOrderDto) {
        Order order = new Order();
        order.setAmount(createOrderDto.getAmount());
        Integer idProduct = createOrderDto.getIdProduct();
        Product product = productRepository.findById(idProduct).
                orElseThrow(() -> new IllegalStateException("Product not found with id:" + idProduct));
        order.setProduct(product);
        Integer idUser = createOrderDto.getIdUser();
        User user = userRepository.findById(idUser).
                orElseThrow(() -> new IllegalArgumentException("User not found with id: " + idUser));
        order.setUser(user);
        return order;
    }
}
