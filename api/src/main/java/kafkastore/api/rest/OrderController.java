package kafkastore.api.rest;

import kafkastore.api.resource.OrderResource;
import kafkastore.domain.model.Order;
import kafkastore.domain.model.Payment;
import kafkastore.domain.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
public class OrderController {

    private Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderRepository orderRepository;

    @PostMapping("/orders")
    public String createOrder(@RequestBody OrderResource orderResource) {
        Order order = new Order();
        order.setDescription(orderResource.getDescription());
        order.setStatus(Order.OrderStatus.CREATED);
        order.setTotal(orderResource.getTotal());

        Payment payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(Payment.PaymentStatus.APROVED);
        payment.setDate(new Date());
        payment.setTotal(order.getTotal());
        order.addPayment(payment);

        payment = new Payment();
        payment.setOrder(order);
        payment.setStatus(Payment.PaymentStatus.CANCELED);
        payment.setDate(new Date());
        payment.setTotal(order.getTotal());
        order.addPayment(payment);

        orderRepository.save(order);

        log.info("Order created");

        return orderResource.toString();
    }

    @GetMapping("/status")
    public String health() {
        return "ok";
    }
}
