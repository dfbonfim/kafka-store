package kafkastore.event.mapper;

import kafkastore.domain.model.Order;
import kafkastore.domain.repository.OrderRepository;
import kafkastore.event.OrderAvro;
import kafkastore.event.resource.debezium.EventDebezium;
import kafkastore.event.resource.debezium.OrderDebezium;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class OrderEventMapper {

    private Logger log = LoggerFactory.getLogger(OrderEventMapper.class);

    @Autowired
    private OrderRepository orderRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Transactional
    public OrderAvro map(EventDebezium<OrderDebezium> event){
        log.info("Mapping Order [{}]", event.getAfter().getId());

        Order order = orderRepository.findOne(event.getAfter().getId());

        log.info("Order [{}]", order);

        return modelMapper.map(order, OrderAvro.class);
    }
}
