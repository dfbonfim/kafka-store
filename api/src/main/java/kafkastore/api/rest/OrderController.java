package kafkastore.api.rest;

import kafkastore.api.resource.OrderResource;
import kafkastore.domain.model.Order;
import kafkastore.domain.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/")
    public String createOrder(@RequestBody OrderResource orderResource) {
        Order order = new Order();
        order.setDescription(orderResource.getDescription());
        order.setStatus(Order.Status.CREATED);
        order.setTotal(orderResource.getTotal());

        orderRepository.save(order);

        return orderResource.toString();
    }

    @GetMapping("/status")
    public String health() {
        return "ok";
    }
}
