package kafkastore.domain.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
public class Payment {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private long id;

    private Date date;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private BigDecimal total;

    @ManyToOne
    @JoinColumn(name="order_id")
    private Order order;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public enum PaymentStatus {
        CREATED, APROVED, FRAUD, CANCELED;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Payment{");
        sb.append("id=").append(id);
        sb.append(", date=").append(date);
        sb.append(", status=").append(status);
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
