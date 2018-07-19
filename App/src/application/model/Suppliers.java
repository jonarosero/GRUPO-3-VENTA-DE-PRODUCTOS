/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author bruno
 */
@Entity
@Table(name = "suppliers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Suppliers.findAll", query = "SELECT s FROM Suppliers s")
    , @NamedQuery(name = "Suppliers.findBySupplierID", query = "SELECT s FROM Suppliers s WHERE s.supplierID = :supplierID")
    , @NamedQuery(name = "Suppliers.findByName", query = "SELECT s FROM Suppliers s WHERE s.name = :name")
    , @NamedQuery(name = "Suppliers.findByAddress", query = "SELECT s FROM Suppliers s WHERE s.address = :address")
    , @NamedQuery(name = "Suppliers.findByCity", query = "SELECT s FROM Suppliers s WHERE s.city = :city")
    , @NamedQuery(name = "Suppliers.findByCountry", query = "SELECT s FROM Suppliers s WHERE s.country = :country")
    , @NamedQuery(name = "Suppliers.findByPhone", query = "SELECT s FROM Suppliers s WHERE s.phone = :phone")})
public class Suppliers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "SupplierID")
    private Short supplierID;
    @Basic(optional = false)
    @Column(name = "Name")
    private String name;
    @Column(name = "Address")
    private String address;
    @Column(name = "City")
    private String city;
    @Column(name = "Country")
    private String country;
    @Column(name = "Phone")
    private String phone;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "supplierID")
    private Collection<Products> productsCollection;

    public Suppliers() {
    }

    public Suppliers(Short supplierID) {
        this.supplierID = supplierID;
    }

    public Suppliers(Short supplierID, String name) {
        this.supplierID = supplierID;
        this.name = name;
    }

    public Short getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(Short supplierID) {
        this.supplierID = supplierID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @XmlTransient
    public Collection<Products> getProductsCollection() {
        return productsCollection;
    }

    public void setProductsCollection(Collection<Products> productsCollection) {
        this.productsCollection = productsCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (supplierID != null ? supplierID.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Suppliers)) {
            return false;
        }
        Suppliers other = (Suppliers) object;
        if ((this.supplierID == null && other.supplierID != null) || (this.supplierID != null && !this.supplierID.equals(other.supplierID))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "application.model.Suppliers[ supplierID=" + supplierID + " ]";
    }
    
}
