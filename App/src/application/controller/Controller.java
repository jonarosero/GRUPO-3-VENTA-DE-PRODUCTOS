package application.controller;

import application.controller.jpa.CategoriesJpaController;
import application.controller.jpa.CustomersJpaController;
import application.controller.jpa.OrderDetailsJpaController;
import application.controller.jpa.OrdersJpaController;
import application.controller.jpa.ProductsJpaController;
import application.controller.jpa.SuppliersJpaController;
import application.controller.jpa.exceptions.IllegalOrphanException;
import application.controller.jpa.exceptions.NonexistentEntityException;
import application.model.Customers;
import application.model.OrderDetails;
import application.model.OrderDetailsPK;
import application.model.Orders;
import application.model.Products;
import application.view.GUI;
import com.Utils;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author bruno
 */
public class Controller implements ActionListener {

    /**
     * return new EntityManagerFactory
     */
    public static final EntityManagerFactory Manager = Persistence.createEntityManagerFactory("AppPU");

    // model
    private Customers customer;
    private Orders orders;

    // view
    private GUI gui;

    // controlers
    CategoriesJpaController categoriesController = new CategoriesJpaController(Controller.Manager);
    CustomersJpaController customerController = new CustomersJpaController(Controller.Manager);
    OrderDetailsJpaController orderDetailsController = new OrderDetailsJpaController(Controller.Manager);
    OrdersJpaController ordersController = new OrdersJpaController(Controller.Manager);
    ProductsJpaController productsController = new ProductsJpaController(Controller.Manager);
    SuppliersJpaController suppliersController = new SuppliersJpaController(Controller.Manager);

    public Controller(GUI gui) {
        this.gui = gui;
    }

    public void start() {
        // Window props
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
        gui.setTitle("Ventas");
        // set panels
        gui.panelLogin.setVisible(true);
        gui.panelStatus.setVisible(false);
        gui.panelOrder.setVisible(false);
        // set action listener to btn's
        gui.btnLogin.addActionListener(this);
        gui.btnNew.addActionListener(this);
        gui.btnDelete.addActionListener(this);
        gui.btnAdd.addActionListener(this);
        gui.btnCancel.addActionListener(this);
        gui.btnFin.addActionListener(this);
        gui.btnRemove.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Login")) {
            login();
        } else if (command.equals("New")) {
            newOrder();
        } else if (command.equals("New")) {
            newOrder();
        } else if (command.equals("Añadir")) {
            addProduct();
        } else if (command.equals("Eliminar")) {
            removeProduct();
        } else if (command.equals("Cancelar")) {
            cancelOrder();
        } else if (command.equals("Finalizar")) {
            finOrder();
        }
    }

    /**
     * ends order and data
     */
    private void finOrder() {
        // validate fields
        // set name of ship
        if (gui.inputNombre.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese el nombre de la Orden");
            return;
        }
        this.orders.setShipName(gui.inputNombre.getText());
        // set required date of ship
        if (gui.inputRequiredDate.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingrese la fecha requerida");
            return;
        }
        // todo: valida date
        this.orders.setRequiredDate(Date.valueOf(gui.inputRequiredDate.getText()));

        // location where shiped
        this.orders.setShipAddress(customer.getAddress());
        this.orders.setShipCity(customer.getCity());
        this.orders.setShipCountry(customer.getCountry());
        try {
            // update db
            this.ordersController.edit(orders);
            // update view
            gui.panelOrder.setVisible(false);
            gui.panelStatus.setVisible(true);
            this.updateStatusTable();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar tu pedido, vuelve a intentarlo");
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Add product to client car. TODO: everything
     */
    private void addProduct() {
        // get selected item
        int row = gui.tableProducts.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione una fila de productos");
            return;
        }
        // get product
        Products product = productsController.findProducts((short) gui.tableProducts.getValueAt(row, 0));
        // create order detail
        OrderDetails orderDetails = new OrderDetails(new OrderDetailsPK(orders.getOrderID(), product.getProductID()));
        orderDetails.setOrders(orders);
        orderDetails.setProducts(product);
        int q = 10;
        orderDetails.setQuantity((short) q); // TODO: add quantity
        orderDetails.setUnitPrice(product.getUnitPrice());
        try {
            // remove from db 1 unit of that product
            product.setUnitsInStock((short) (product.getUnitsInStock() - q)); // TODO: add quantity
            productsController.edit(product);
            // add to customer db as new product, or update product
            OrderDetails temp = orderDetailsController.findOrderDetails(orderDetails.getOrderDetailsPK());
            System.out.println(temp);
            if (temp == null) {
                System.out.println("Creating new order");
                orderDetailsController.create(orderDetails);
            } else {
                System.out.println("Updating order");
                orderDetails.setQuantity((short) (orderDetails.getQuantity() + 2));// TODO: add quantity
                orderDetailsController.edit(orderDetails);

            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class
                    .getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo agregar el producto, vuelve a intentarlo");
        }
        // update tables
        this.loadProducts();
        this.loadCart();
    }

    /**
     * Remove product from db.
     */
    private void removeProduct() {
        // get selected item
        int row = gui.tableCar.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(null, "Seleccione un producto de carrito");
            return;
        }
        // get product
        Products product = productsController.findProducts((short) gui.tableCar.getValueAt(row, 0));
        OrderDetailsPK orderDetail = new OrderDetailsPK(this.orders.getOrderID(), product.getProductID());
        try {
            // add un unit to products with the quantity of selected product
            product.setUnitsInStock((short) (1));// TODO: fix
            productsController.edit(product);
            this.orderDetailsController.destroy(orderDetail);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar producto del carrito");
            Logger
                    .getLogger(Controller.class
                            .getName()).log(Level.SEVERE, null, ex);
        }
        // update tables
        this.loadProducts();
        this.loadCart();
    }

    /**
     * Set application for new Order
     */
    private void newOrder() {
        // clean gui
        cleanOrderGUI();
        // update view
        // create new order
        orders = new Orders();
        orders.setOrderDate(new java.util.Date());
        ordersController.create(orders);
        // change panel
        gui.panelStatus.setVisible(false);
        gui.panelOrder.setVisible(true);
        // load products
        loadProducts();
        // set text
        gui.textNumber.setText(orders.getOrderID().toString());
        gui.textDate.setText(orders.getOrderDate().toString());
    }

    /**
     * Validate login, and change view
     */
    private void login() {
        // Validate
        if (gui.inputCustomerID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresar un ID valido");
            return;
        }
        // Get costumer
        customer = customerController.findCustomers(gui.inputCustomerID.getText());
        // Validate db Response
        if (customer == null) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados, Ingresar un ID valido");
            return;
        }
        // valid customer, do actions and change layout
        gui.inputCustomerID.setText("");
        gui.panelLogin.setVisible(false);
        gui.panelStatus.setVisible(true);
        gui.textCustomerID.setText(customer.toString());
        updateStatusTable();
    }

    // load table data
    private void updateStatusTable() {
        // get new model
        Object tblCol[] = {"Numero de Orden", "Fecha Pedido", "Fecha Enviado", "Nombre"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableStatus.setModel(tableModel);
        // no params were provided
        if (customer == null) {
            return;
        }
        // get cuenta cliente with account id
        List<Orders> orders = this.ordersController.findOrdersEntities();
        for (int i = 0; i < orders.size(); i++) {
            Orders order = orders.get(i);
            Object[] obj = null;
            tableModel.addRow(obj);
            tableModel.setValueAt(order.getOrderID(), i, 0);
            tableModel.setValueAt(order.getOrderDate(), i, 1);
            tableModel.setValueAt(order.getShippedDate(), i, 2);
            tableModel.setValueAt(order.getShipName(), i, 3);
        }
    }

    private void cleanOrderGUI() {
        // clean products
        Object tblCol1[] = {"ID", "Nombre", "Cantidad", "Precio", "Categoria", "Distribuidor"};
        gui.tableCar.setModel(new DefaultTableModel(null, tblCol1));
        // clean new model
        Object tblCol2[] = {"ID", "Nombre", "Cantidad", "Stock", "Precio", "Categoria", "Distribuidor"};
        gui.tableProducts.setModel(new DefaultTableModel(null, tblCol2));
    }

    private void loadCart() {
        // get new model
        Object tblCol[] = {"ID", "Nombre", "Cantidad", "Precio Unitario", "Total", "Distribuidor"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableCar.setModel(tableModel);
        // update and get ordes
        orders = ordersController.findOrders(orders.getOrderID());
        List<OrderDetails> orderDetails = new ArrayList(orders.getOrderDetailsCollection());
        BigDecimal price = BigDecimal.ZERO;
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetails detail = orderDetails.get(i);
            Products pro = detail.getProducts();
            Object[] obj = null;
            BigDecimal total = Utils.calculateCost(detail.getQuantity(), detail.getUnitPrice());
            ////////////////////
            tableModel.addRow(obj);
            tableModel.setValueAt(pro.getProductID(), i, 0);
            tableModel.setValueAt(pro.getProductName(), i, 1);
            tableModel.setValueAt(detail.getQuantity(), i, 2);
            tableModel.setValueAt(detail.getUnitPrice(), i, 3);
            tableModel.setValueAt(total, i, 4);
            tableModel.setValueAt(pro.getSupplierID().getName(), i, 5);
            // manage cost
            price = price.add(total);
        }
        // set status gui
        gui.textTotal.setText(price.toString());
    }

    /**
     * Delete order, and already selected items
     */
    private void cancelOrder() {
        // remove already selected items
        orders = ordersController.findOrders(orders.getOrderID()); // update orders
        List<OrderDetails> orderDetails = new ArrayList(orders.getOrderDetailsCollection());
        for (OrderDetails orderDetail : orderDetails) {
            Products product = orderDetail.getProducts();
            try {
                // add units to products with the quantity of selected product
                product.setUnitsInStock((short) (product.getUnitsInStock() + orderDetail.getQuantity()));
                productsController.edit(product);
                this.orderDetailsController.destroy(orderDetail.getOrderDetailsPK());
            } catch (Exception ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        try {
            this.ordersController.destroy(orders.getOrderID());
            // update view
            gui.panelOrder.setVisible(false);
            gui.panelStatus.setVisible(true);
        } catch (IllegalOrphanException | NonexistentEntityException ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo cancelar orden, vuelve a intentar");
        }
    }

    private void loadProducts() {
        // get new model
        Object tblCol[] = {"ID", "Nombre", "Cantidad", "Stock", "Precio", "Categoria", "Distribuidor"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableProducts.setModel(tableModel);

        // get cuenta cliente with account id
        List<Products> products = this.productsController.findProductsEntities();
        for (int i = 0; i < products.size(); i++) {
            Products pro = products.get(i);
            // Validate stock of product
            if (pro.getUnitsInStock() <= 0) {
                // no more of this product
                continue;
            }
            Object[] obj = null;
            tableModel.addRow(obj);
            tableModel.setValueAt(pro.getProductID(), i, 0);
            tableModel.setValueAt(pro.getProductName(), i, 1);
            tableModel.setValueAt(pro.getQuantityPerUnit(), i, 2);
            tableModel.setValueAt(pro.getUnitsInStock(), i, 3);
            tableModel.setValueAt(Utils.calculateCost(Integer.parseInt(pro.getQuantityPerUnit()), pro.getUnitPrice()), i, 4);
            tableModel.setValueAt(pro.getCategoryID().getCategoryName(), i, 5);
            tableModel.setValueAt(pro.getSupplierID().getName(), i, 6);
        }
    }
}
