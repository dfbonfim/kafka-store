package kafkastore.rest;

import kafkastore.resource.OrderResource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    @PostMapping("/")
    public String createOrder(@RequestBody OrderResource orderResource) {
        return orderResource.toString();
    }

    @GetMapping("/status")
    public String health() {
        return "ok";
    }
}
