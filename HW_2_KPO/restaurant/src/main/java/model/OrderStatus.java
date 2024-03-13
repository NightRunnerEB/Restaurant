package model;

public enum OrderStatus {
    READY("ready"),
    PREPARING("preparing"),
    ACCEPTED("accepted");

    private final String status;

    OrderStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }

    public static OrderStatus fromString(String text) {
        OrderStatus output = null; 
        for (OrderStatus order : OrderStatus.values()) {
            if (order.status.equalsIgnoreCase(text)) {
                output = order;
            }
        }
        return output;
    }
}

