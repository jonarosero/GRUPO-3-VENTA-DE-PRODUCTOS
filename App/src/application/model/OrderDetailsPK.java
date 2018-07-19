/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author bruno
 */
@Embeddable
public class OrderDetailsPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "OrderID")
    private int orderID;
    @Basic(optional = false)
    @Column(name = "ProductID")
    private short productID;

    public OrderDetailsPK() {
    }

    public OrderDetailsPK(int orderID, short productID) {
        this.orderID = orderID;
        this.productID = productID;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public short getProductID() {
        return productID;
    }

    public void setProductID(short productID) {
        this.productID = productID;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) orderID;
        hash += (int) productID;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderDetailsPK)) {
            return false;
        }
        OrderDetailsPK other = (OrderDetailsPK) object;
        if (this.orderID != other.orderID) {
            return false;
        }
        if (this.productID != other.productID) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "application.model.OrderDetailsPK[ orderID=" + orderID + ", productID=" + productID + " ]";
    }
    
}
