/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller.jpa;

import application.controller.jpa.exceptions.IllegalOrphanException;
import application.controller.jpa.exceptions.NonexistentEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import application.model.Categories;
import application.model.Suppliers;
import application.model.OrderDetails;
import application.model.Products;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bruno
 */
public class ProductsJpaController implements Serializable {

    public ProductsJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Products products) {
        if (products.getOrderDetailsCollection() == null) {
            products.setOrderDetailsCollection(new ArrayList<OrderDetails>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Categories categoryID = products.getCategoryID();
            if (categoryID != null) {
                categoryID = em.getReference(categoryID.getClass(), categoryID.getCategoryID());
                products.setCategoryID(categoryID);
            }
            Suppliers supplierID = products.getSupplierID();
            if (supplierID != null) {
                supplierID = em.getReference(supplierID.getClass(), supplierID.getSupplierID());
                products.setSupplierID(supplierID);
            }
            Collection<OrderDetails> attachedOrderDetailsCollection = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionOrderDetailsToAttach : products.getOrderDetailsCollection()) {
                orderDetailsCollectionOrderDetailsToAttach = em.getReference(orderDetailsCollectionOrderDetailsToAttach.getClass(), orderDetailsCollectionOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollection.add(orderDetailsCollectionOrderDetailsToAttach);
            }
            products.setOrderDetailsCollection(attachedOrderDetailsCollection);
            em.persist(products);
            if (categoryID != null) {
                categoryID.getProductsCollection().add(products);
                categoryID = em.merge(categoryID);
            }
            if (supplierID != null) {
                supplierID.getProductsCollection().add(products);
                supplierID = em.merge(supplierID);
            }
            for (OrderDetails orderDetailsCollectionOrderDetails : products.getOrderDetailsCollection()) {
                Products oldProductsOfOrderDetailsCollectionOrderDetails = orderDetailsCollectionOrderDetails.getProducts();
                orderDetailsCollectionOrderDetails.setProducts(products);
                orderDetailsCollectionOrderDetails = em.merge(orderDetailsCollectionOrderDetails);
                if (oldProductsOfOrderDetailsCollectionOrderDetails != null) {
                    oldProductsOfOrderDetailsCollectionOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionOrderDetails);
                    oldProductsOfOrderDetailsCollectionOrderDetails = em.merge(oldProductsOfOrderDetailsCollectionOrderDetails);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Products products) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products persistentProducts = em.find(Products.class, products.getProductID());
            Categories categoryIDOld = persistentProducts.getCategoryID();
            Categories categoryIDNew = products.getCategoryID();
            Suppliers supplierIDOld = persistentProducts.getSupplierID();
            Suppliers supplierIDNew = products.getSupplierID();
            Collection<OrderDetails> orderDetailsCollectionOld = persistentProducts.getOrderDetailsCollection();
            Collection<OrderDetails> orderDetailsCollectionNew = products.getOrderDetailsCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderDetails orderDetailsCollectionOldOrderDetails : orderDetailsCollectionOld) {
                if (!orderDetailsCollectionNew.contains(orderDetailsCollectionOldOrderDetails)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderDetails " + orderDetailsCollectionOldOrderDetails + " since its products field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryIDNew != null) {
                categoryIDNew = em.getReference(categoryIDNew.getClass(), categoryIDNew.getCategoryID());
                products.setCategoryID(categoryIDNew);
            }
            if (supplierIDNew != null) {
                supplierIDNew = em.getReference(supplierIDNew.getClass(), supplierIDNew.getSupplierID());
                products.setSupplierID(supplierIDNew);
            }
            Collection<OrderDetails> attachedOrderDetailsCollectionNew = new ArrayList<OrderDetails>();
            for (OrderDetails orderDetailsCollectionNewOrderDetailsToAttach : orderDetailsCollectionNew) {
                orderDetailsCollectionNewOrderDetailsToAttach = em.getReference(orderDetailsCollectionNewOrderDetailsToAttach.getClass(), orderDetailsCollectionNewOrderDetailsToAttach.getOrderDetailsPK());
                attachedOrderDetailsCollectionNew.add(orderDetailsCollectionNewOrderDetailsToAttach);
            }
            orderDetailsCollectionNew = attachedOrderDetailsCollectionNew;
            products.setOrderDetailsCollection(orderDetailsCollectionNew);
            products = em.merge(products);
            if (categoryIDOld != null && !categoryIDOld.equals(categoryIDNew)) {
                categoryIDOld.getProductsCollection().remove(products);
                categoryIDOld = em.merge(categoryIDOld);
            }
            if (categoryIDNew != null && !categoryIDNew.equals(categoryIDOld)) {
                categoryIDNew.getProductsCollection().add(products);
                categoryIDNew = em.merge(categoryIDNew);
            }
            if (supplierIDOld != null && !supplierIDOld.equals(supplierIDNew)) {
                supplierIDOld.getProductsCollection().remove(products);
                supplierIDOld = em.merge(supplierIDOld);
            }
            if (supplierIDNew != null && !supplierIDNew.equals(supplierIDOld)) {
                supplierIDNew.getProductsCollection().add(products);
                supplierIDNew = em.merge(supplierIDNew);
            }
            for (OrderDetails orderDetailsCollectionNewOrderDetails : orderDetailsCollectionNew) {
                if (!orderDetailsCollectionOld.contains(orderDetailsCollectionNewOrderDetails)) {
                    Products oldProductsOfOrderDetailsCollectionNewOrderDetails = orderDetailsCollectionNewOrderDetails.getProducts();
                    orderDetailsCollectionNewOrderDetails.setProducts(products);
                    orderDetailsCollectionNewOrderDetails = em.merge(orderDetailsCollectionNewOrderDetails);
                    if (oldProductsOfOrderDetailsCollectionNewOrderDetails != null && !oldProductsOfOrderDetailsCollectionNewOrderDetails.equals(products)) {
                        oldProductsOfOrderDetailsCollectionNewOrderDetails.getOrderDetailsCollection().remove(orderDetailsCollectionNewOrderDetails);
                        oldProductsOfOrderDetailsCollectionNewOrderDetails = em.merge(oldProductsOfOrderDetailsCollectionNewOrderDetails);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = products.getProductID();
                if (findProducts(id) == null) {
                    throw new NonexistentEntityException("The products with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Products products;
            try {
                products = em.getReference(Products.class, id);
                products.getProductID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The products with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderDetails> orderDetailsCollectionOrphanCheck = products.getOrderDetailsCollection();
            for (OrderDetails orderDetailsCollectionOrphanCheckOrderDetails : orderDetailsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Products (" + products + ") cannot be destroyed since the OrderDetails " + orderDetailsCollectionOrphanCheckOrderDetails + " in its orderDetailsCollection field has a non-nullable products field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Categories categoryID = products.getCategoryID();
            if (categoryID != null) {
                categoryID.getProductsCollection().remove(products);
                categoryID = em.merge(categoryID);
            }
            Suppliers supplierID = products.getSupplierID();
            if (supplierID != null) {
                supplierID.getProductsCollection().remove(products);
                supplierID = em.merge(supplierID);
            }
            em.remove(products);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Products> findProductsEntities() {
        return findProductsEntities(true, -1, -1);
    }

    public List<Products> findProductsEntities(int maxResults, int firstResult) {
        return findProductsEntities(false, maxResults, firstResult);
    }

    private List<Products> findProductsEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Products.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Products findProducts(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Products.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductsCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Products> rt = cq.from(Products.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
