package market.retail.shop.service;

import lombok.RequiredArgsConstructor;
import market.retail.shop.dto.CreateOrderDto;
import market.retail.shop.dto.OrderDto;
import market.retail.shop.entity.Order;
import market.retail.shop.entity.Product;
import market.retail.shop.entity.Review;
import market.retail.shop.entity.User;
import market.retail.shop.mapper.OrderMapper;
import market.retail.shop.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final UserService userService;
    private final ProductService productService;
    private final OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public List<OrderDto> findAll() {
        return orderRepository.findAll().
                stream().
                map(orderMapper::toDto).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderDto findById(Integer id) {
        return orderRepository.findById(id).
                map(orderMapper::toDto).
                orElseThrow(() -> new RuntimeException("Order " + id + " is not found"));
    }

    @Transactional
    public OrderDto update(Integer id, CreateOrderDto createOrderDto) {
        Order order = orderRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("User " + id + " is not found"));

        Integer idUser = createOrderDto.getIdUser();
        User user = userService.returnEntityById(idUser);
        order.setUser(user);

        Integer idProduct = createOrderDto.getIdProduct();
        Product product = productService.returnEntityById(idProduct);
        order.setProduct(product);
        Integer amount = createOrderDto.getAmount();
        order.setAmount(amount);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional(readOnly = true)
    public Order returnEntityById(Integer id) {
        return orderRepository.findById(id).
                orElseThrow(() -> new IllegalStateException("Order" + id + " is not found"));
    }

    @Transactional
    public OrderDto create(CreateOrderDto createOrderDto) {
        Integer userId = createOrderDto.getIdUser();
        Integer productId = createOrderDto.getIdProduct();

        User user = userService.returnEntityById(userId);
        Product product = productService.returnEntityById(productId);

        Optional<Review> optionalOrder = orderRepository.findByUserIdAndProductId(userId, productId);
        if (optionalOrder.isPresent()) {
            throw new IllegalStateException("Review by this user and product is already exists");
        }
        Order order = orderMapper.toEntity(createOrderDto);
        order.setProduct(product);
        order.setUser(user);
        Order savedOrder = orderRepository.save(order);
        return orderMapper.toDto(savedOrder);
    }

    @Transactional
    public void deleteById(Integer id) {
        Optional<Order> optionalOrderDto = orderRepository.findById(id);
        if (optionalOrderDto.isEmpty()) {
            throw new IllegalStateException("Order does not exist");
        }
        orderRepository.deleteById(id);
    }
}
