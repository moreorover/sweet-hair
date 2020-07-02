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
                .operationDate(orderDtoFull.getOperationDate())
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

            Product p = productRepository.findById(product.getProduct().getId())
                    .orElseThrow(() -> new SpringDataException("No product found with ID -> " + product.getProduct().getId()));

            p.stockCountChangedFromOrder(0 , product.getQuantity());
            productRepository.save(p);
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

        Map<Long, OrderProductDtoBase> payloadProducts = orderDto
                .getProducts()
                .stream()
                .collect(Collectors.toMap(OrderProductDtoBase::getProductId, Function.identity()));

        order.getProducts().forEach(orderProduct -> {
            OrderProductDtoBase o = payloadProducts.get(orderProduct.getProduct().getId());
            if (o == null) {
                orderProduct.getProduct().stockCountChangedFromOrder(orderProduct.getQuantity(), 0);
                productRepository.save(orderProduct.getProduct());
                orderProductsRepository.delete(orderProduct);
            } else {
                if (o.getUnitPrice() != orderProduct.getUnitPrice()) {
                    orderProduct.setUnitPrice(o.getUnitPrice());
                }
                if (o.getQuantity() != orderProduct.getQuantity()) {
                    orderProduct.getProduct().stockCountChangedFromOrder(orderProduct.getQuantity(), o.getQuantity());
                    orderProduct.setQuantity(o.getQuantity());
                    productRepository.save(orderProduct.getProduct());
                }
                payloadProducts.remove(orderProduct.getProduct().getId());
            }
        });

        if (payloadProducts.size() > 0) {
            payloadProducts.values().forEach(payloadProduct -> this.addProductToOrder(order, payloadProduct));
        }

        if (!order.getSupplier().getId().equals(orderDto.getSupplier().getId())) {
            Supplier supplier = supplierRepository.findById(orderDto.getSupplier().getId())
                    .orElseThrow(() -> new SpringDataException("No supplier found with ID -> " + orderDto.getId()));

            order.setSupplier(supplier);
        }

        if (order.getOperationDate() != orderDto.getOperationDate()) {
            order.setOperationDate(orderDto.getOperationDate());
        }

        this.updateOrderTotals(order, orderDto.getItemsCount(), orderDto.getTotal());

        Order save = orderRepository.getOne(order.getId());
        return modelMapper.map(save, OrderDtoFull.class);
    }

    public void deleteOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new SpringDataException("No order found with ID -> " + id));

        order.getProducts().forEach(orderProduct -> {
            Product product = productRepository.findById(orderProduct.getProduct().getId())
                    .orElseThrow(() -> new SpringDataException("No product found with ID -> " + orderProduct.getProduct().getId()));
            product.stockCountChangedFromOrder(orderProduct.getQuantity(), 0);
            orderProductsRepository.delete(orderProduct);
        });

        orderRepository.deleteById(id);
    }

    @Transactional
    public void addProductToOrder(Order order, OrderProductDtoBase orderProductDto) {
        Product product = productRepository.findById(orderProductDto.getProduct().getId())
                .orElseThrow(() -> new SpringDataException("No product found with ID -> " + orderProductDto.getProduct().getId()));

        product.stockCountChangedFromOrder(0 , orderProductDto.getQuantity());
        productRepository.save(product);

        OrderProducts newOrderProducts = OrderProducts.builder()
                .orderItem(new OrderProductsId(order.getId(), orderProductDto.getProduct().getId()))
                .order(order)
                .product(product)
                .quantity(orderProductDto.getQuantity())
                .unitPrice(orderProductDto.getUnitPrice())
                .build();

        if (order.getProducts() == null) {
            order.setProducts(new ArrayList<>());
        }

        if (product.getOrders() == null) {
            product.setOrders(new ArrayList<>());
        }

        order.getProducts().add(newOrderProducts);
        product.getOrders().add(newOrderProducts);

        orderProductsRepository.save(newOrderProducts);
    }

    @Transactional
    public void updateOrderTotals(Order order, int quantity, double total) {
        order.setItemsCount(quantity);
        order.setTotal(total);
        orderRepository.save(order);
    }
}
