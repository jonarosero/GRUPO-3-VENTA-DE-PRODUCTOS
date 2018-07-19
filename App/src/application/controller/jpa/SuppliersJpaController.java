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
import application.model.Products;
import application.model.Suppliers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author bruno
 */
public class SuppliersJpaController implements Serializable {

    public SuppliersJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Suppliers suppliers) {
        if (suppliers.getProductsCollection() == null) {
            suppliers.setProductsCollection(new ArrayList<Products>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Collection<Products> attachedProductsCollection = new ArrayList<Products>();
            for (Products productsCollectionProductsToAttach : suppliers.getProductsCollection()) {
                productsCollectionProductsToAttach = em.getReference(productsCollectionProductsToAttach.getClass(), productsCollectionProductsToAttach.getProductID());
                attachedProductsCollection.add(productsCollectionProductsToAttach);
            }
            suppliers.setProductsCollection(attachedProductsCollection);
            em.persist(suppliers);
            for (Products productsCollectionProducts : suppliers.getProductsCollection()) {
                Suppliers oldSupplierIDOfProductsCollectionProducts = productsCollectionProducts.getSupplierID();
                productsCollectionProducts.setSupplierID(suppliers);
                productsCollectionProducts = em.merge(productsCollectionProducts);
                if (oldSupplierIDOfProductsCollectionProducts != null) {
                    oldSupplierIDOfProductsCollectionProducts.getProductsCollection().remove(productsCollectionProducts);
                    oldSupplierIDOfProductsCollectionProducts = em.merge(oldSupplierIDOfProductsCollectionProducts);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Suppliers suppliers) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Suppliers persistentSuppliers = em.find(Suppliers.class, suppliers.getSupplierID());
            Collection<Products> productsCollectionOld = persistentSuppliers.getProductsCollection();
            Collection<Products> productsCollectionNew = suppliers.getProductsCollection();
            List<String> illegalOrphanMessages = null;
            for (Products productsCollectionOldProducts : productsCollectionOld) {
                if (!productsCollectionNew.contains(productsCollectionOldProducts)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Products " + productsCollectionOldProducts + " since its supplierID field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Products> attachedProductsCollectionNew = new ArrayList<Products>();
            for (Products productsCollectionNewProductsToAttach : productsCollectionNew) {
                productsCollectionNewProductsToAttach = em.getReference(productsCollectionNewProductsToAttach.getClass(), productsCollectionNewProductsToAttach.getProductID());
                attachedProductsCollectionNew.add(productsCollectionNewProductsToAttach);
            }
            productsCollectionNew = attachedProductsCollectionNew;
            suppliers.setProductsCollection(productsCollectionNew);
            suppliers = em.merge(suppliers);
            for (Products productsCollectionNewProducts : productsCollectionNew) {
                if (!productsCollectionOld.contains(productsCollectionNewProducts)) {
                    Suppliers oldSupplierIDOfProductsCollectionNewProducts = productsCollectionNewProducts.getSupplierID();
                    productsCollectionNewProducts.setSupplierID(suppliers);
                    productsCollectionNewProducts = em.merge(productsCollectionNewProducts);
                    if (oldSupplierIDOfProductsCollectionNewProducts != null && !oldSupplierIDOfProductsCollectionNewProducts.equals(suppliers)) {
                        oldSupplierIDOfProductsCollectionNewProducts.getProductsCollection().remove(productsCollectionNewProducts);
                        oldSupplierIDOfProductsCollectionNewProducts = em.merge(oldSupplierIDOfProductsCollectionNewProducts);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = suppliers.getSupplierID();
                if (findSuppliers(id) == null) {
                    throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.");
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
            Suppliers suppliers;
            try {
                suppliers = em.getReference(Suppliers.class, id);
                suppliers.getSupplierID();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The suppliers with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Products> productsCollectionOrphanCheck = suppliers.getProductsCollection();
            for (Products productsCollectionOrphanCheckProducts : productsCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Suppliers (" + suppliers + ") cannot be destroyed since the Products " + productsCollectionOrphanCheckProducts + " in its productsCollection field has a non-nullable supplierID field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(suppliers);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Suppliers> findSuppliersEntities() {
        return findSuppliersEntities(true, -1, -1);
    }

    public List<Suppliers> findSuppliersEntities(int maxResults, int firstResult) {
        return findSuppliersEntities(false, maxResults, firstResult);
    }

    private List<Suppliers> findSuppliersEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Suppliers.class));
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

    public Suppliers findSuppliers(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Suppliers.class, id);
        } finally {
            em.close();
        }
    }

    public int getSuppliersCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Suppliers> rt = cq.from(Suppliers.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
