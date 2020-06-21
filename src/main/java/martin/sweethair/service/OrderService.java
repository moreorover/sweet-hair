package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.OrderDtoBase;
import martin.sweethair.dto.base.OrderProductDtoBase;
import martin.sweethair.dto.full.OrderDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.Order;
import martin.sweethair.model.OrderProducts;
import martin.sweethair.model.OrderProductsId;
import martin.sweethair.model.Product;
import martin.sweethair.repository.OrderProductsRepository;
import martin.sweethair.repository.OrderRepository;
import martin.sweethair.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;

    private final OrderProductsRepository orderProductsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    @Transactional
    public OrderDtoBase save(OrderDtoBase orderDtoBase) {
        Order save = orderRepository.save(modelMapper.map(orderDtoBase, Order.class));
        orderDtoBase.setId(save.getId());
        return orderDtoBase;
    }

    @Transactional(readOnly = true)
    public Set<OrderDtoFull> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(order -> modelMapper.map(order, OrderDtoFull.class))
                .collect(Collectors.toSet());
    }

    public OrderDtoFull getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No order found with ID -> " + id));
        return modelMapper.map(order, OrderDtoFull.class);
    }

    @Transactional
    public OrderDtoFull updateOrder(OrderDtoFull orderDto) {
        Order order = orderRepository.findById(orderDto.getId())
                .orElseThrow(() -> new SpringDataException("No order found with ID -> " + orderDto.getId()));
        // TODO implement update logic

        Order save = orderRepository.save(order);
        return modelMapper.map(save, OrderDtoFull.class);
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No order found with ID -> " + id));

        order.getProducts().forEach(orderProductsRepository::delete);

        orderRepository.deleteById(id);
    }

    @Transactional
    public OrderDtoFull addProduct(Long orderId, OrderProductDtoBase orderProductDto) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new SpringDataException("No order found with ID -> " + orderId));

        Product product = productRepository.findById(orderProductDto.getProduct().getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + orderProductDto.getProduct().getId()));

        OrderProducts newOrderProducts = OrderProducts.builder()
                .orderItem(new OrderProductsId(orderId, orderProductDto.getProduct().getId()))
                .order(order)
                .product(product)
                .quantity(orderProductDto.getQuantity())
                .unitPrice(orderProductDto.getUnitPrice())
                .build();

        order.getProducts().add(newOrderProducts);
        product.getOrders().add(newOrderProducts);
        order.setTotal(order.getProducts().stream().mapToDouble(OrderProducts::getTotal).sum());
        order.setItemsCount(order.getProducts().stream().mapToInt(OrderProducts::getQuantity).sum());

        orderProductsRepository.save(newOrderProducts);
        Order save = orderRepository.save(order);
        return modelMapper.map(save, OrderDtoFull.class);
    }
}
