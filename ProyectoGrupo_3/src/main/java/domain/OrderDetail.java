package domain;

public class OrderDetail {
    private int id;
    private static int autoId;// id autogenerado
    private int orderId;
    private int productId;
    private int quantity;
    private Double unitPrice;

    public OrderDetail(int orderId, int productId, int quantity, Double unitPrice) {
        this.id=++autoId;
        this.orderId = orderId;
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }
}
