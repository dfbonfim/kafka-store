package kafkastore.event.topology.debezium;

public class EventDebezium<T> {

    private T before;
    private T after;
    private Operation op;

    public T getBefore() {
        return before;
    }

    public void setBefore(T before) {
        this.before = before;
    }

    public T getAfter() {
        return after;
    }

    public void setAfter(T after) {
        this.after = after;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    enum Operation {
        CREATED("c"),
        UPDATED("u"),
        DELETED("d");

        private String op;

        Operation(String op){ this.op = op; }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EventDebezium{");
        sb.append("before=").append(before);
        sb.append(", after=").append(after);
        sb.append(", op=").append(op);
        sb.append('}');
        return sb.toString();
    }
}
