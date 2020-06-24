package martin.sweethair.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.base.OrderProductDtoBase;
import martin.sweethair.dto.full.OrderDtoFull;
import martin.sweethair.exceptions.SpringDataException;
import martin.sweethair.model.*;
import martin.sweethair.repository.OrderProductsRepository;
import martin.sweethair.repository.OrderRepository;
import martin.sweethair.repository.ProductRepository;
import martin.sweethair.repository.SupplierRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class OrderService {

    private final ModelMapper modelMapper;

    private final OrderProductsRepository orderProductsRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    @Transactional
    public OrderDtoFull save(OrderDtoFull orderDtoFull) {
        Supplier supplier = supplierRepository.findById(orderDtoFull.getSupplier().getId())
                .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + orderDtoFull.getSupplier().getId()));

        Order newOrder = Order.builder()
                .supplier(supplier)
                .total(orderDtoFull.getTotal())
                .itemsCount(orderDtoFull.getItemsCount())
                .currency(orderDtoFull.getCurrency())
                .purchasedAt(orderDtoFull.getPurchasedAt())
                .build();
        orderRepository.save(modelMapper.map(newOrder, Order.class));

        Map<Long, Product> products = orderDtoFull
                .getProducts()
                .stream()
                .map(product -> productRepository.findById(product.getProduct().getId())
                        .orElseThrow(() -> new SpringDataException("No product found with ID -> " + product.getProduct().getId())))
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<OrderProducts> orderProducts = new ArrayList<>();

        orderDtoFull.getProducts().forEach(product -> {
            OrderProducts orderProduct = OrderProducts.builder()
                    .orderItem(new OrderProductsId(newOrder.getId(), product.getProduct().getId()))
                    .order(newOrder)
                    .product(products.get(product.getProduct().getId()))
                    .quantity(product.getQuantity())
                    .unitPrice(product.getUnitPrice())
                    .build();
            orderProducts.add(orderProduct);
        });

        if (newOrder.getProducts() == null) {
            newOrder.setProducts(orderProducts);
        } else {
            newOrder.getProducts().addAll(orderProducts);
        }
        products.values().forEach(product -> product.getOrders().addAll(orderProducts));
        orderProductsRepository.saveAll(orderProducts);
//        Order save = orderRepository.save(newOrder);
        orderDtoFull.setId(newOrder.getId());
        return orderDtoFull;
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
