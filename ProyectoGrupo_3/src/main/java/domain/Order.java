package domain;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private static int autoId;// id autogenerado
    private java.time.LocalDateTime orderDate;
    private String orderStatus;
    private int supplierId;
    private Double totalCost;
    private String remarks;

    public Order(LocalDateTime orderDate, String orderStatus, int supplierId, Double totalCost, String remarks) {
        this.id=++autoId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.supplierId = supplierId;
        this.totalCost = totalCost;
        this.remarks = remarks;
    }

}
