package kafkastore.resource;

import java.math.BigDecimal;

public class OrderResource {

    private String description;
    private BigDecimal total;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderResource{");
        sb.append("description='").append(description).append('\'');
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}

