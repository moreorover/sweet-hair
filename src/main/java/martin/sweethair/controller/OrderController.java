package martin.sweethair.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import martin.sweethair.dto.full.OrderDtoFull;
import martin.sweethair.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderDtoFull> createOrder(@RequestBody OrderDtoFull orderDtoFull) {
        OrderDtoFull savedOrder = orderService.save(orderDtoFull);
        return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Set<OrderDtoFull>> getAllOrders() {
        Set<OrderDtoFull> orderDtos = orderService.getAll();
        return new ResponseEntity<>(orderDtos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDtoFull> getOrderById(@PathVariable("id") Long id) {
        OrderDtoFull orderDto = orderService.getOrderById(id);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDtoFull> updateOrder(@PathVariable("id") Long id, @RequestBody OrderDtoFull orderDto) {
        if (!Objects.equals(id, orderDto.getId())){
            // if different
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        OrderDtoFull updatedOrder = orderService.updateOrder(orderDto);
        return new ResponseEntity<>(updatedOrder, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable("id") Long id) {
        orderService.deleteOrderById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
