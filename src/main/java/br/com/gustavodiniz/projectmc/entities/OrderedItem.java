package br.com.gustavodiniz.projectmc.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "t_ordered_item")
public class OrderedItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    @EmbeddedId
    private OrderedItemPK id = new OrderedItemPK();

    private Double discount;
    private Integer quantity;
    private Double price;

    public OrderedItem() {

    }

    public OrderedItem(Order order, Product product, Double discount, Integer quantity, Double price) {
        id.setOrder(order);
        id.setProduct(product);
        this.discount = discount;
        this.quantity = quantity;
        this.price = price;
    }

    public double getSubTotal() {
        return (price - discount) * quantity;
    }

    @JsonIgnore
    public Order getOrder() {
        return id.getOrder();
    }

    public void setOrder(Order order) {
        id.setOrder(order);
    }

    public Product getProduct() {
        return id.getProduct();
    }

    public void setProduct(Product product) {
        id.setProduct(product);
    }

    public OrderedItemPK getId() {
        return id;
    }

    public void setId(OrderedItemPK id) {
        this.id = id;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderedItem that = (OrderedItem) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
