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

    private int cantidad;

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Order(LocalDateTime orderDate, String orderStatus, int supplierId, Double totalCost, String remarks) {
        this.id=++autoId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.supplierId = supplierId;
        this.totalCost = totalCost;
        this.remarks = remarks;
    }

    public int getId() {
        return id;
    }
}
