package application.controller;

import application.controller.jpa.CategoriesJpaController;
import application.controller.jpa.CustomersJpaController;
import application.controller.jpa.OrderDetailsJpaController;
import application.controller.jpa.OrdersJpaController;
import application.controller.jpa.ProductsJpaController;
import application.controller.jpa.SuppliersJpaController;
import application.model.Customers;
import application.model.OrderDetails;
import application.model.Orders;
import application.model.Products;
import application.view.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        }
    }

    /**
     * Add product to client car
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
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrders(orders);
        orderDetails.setProducts(product);
        orderDetails.setQuantity((short) 1); // TODO: add quantity
        orderDetails.setUnitPrice(product.getUnitPrice());
        try {
            // remove from db 1 unit of that product
            product.setUnitsInStock((short) (product.getUnitsInStock() - 1)); // TODO: add quantity
            productsController.edit(product);
            // add to customer db as new product, or update product
            OrderDetails temp = orderDetailsController.findOrderDetails(orderDetails.getOrderDetailsPK());
            if (temp == null) {
                System.out.println("Creating new order");
                orderDetailsController.create(orderDetails);
            } else {
                System.out.println("Updating order");
                orderDetails.setQuantity((short) (orderDetails.getQuantity() + 1));// TODO: add quantity
                orderDetailsController.edit(orderDetails);
            }
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "No se pudo agregar el producto, vuelve a intentarlo");
            return;
        }
        // update tables
        this.loadProducts();
        this.loadCart();
    }

    /**
     * Set application for new Order
     */
    private void newOrder() {
        // create new order
        orders = new Orders();
        ordersController.create(orders);
        // change panel
        gui.panelStatus.setVisible(false);
        gui.panelOrder.setVisible(true);
        // load products
        loadProducts();
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

    private void loadProducts() {
        // get new model
        Object tblCol[] = {"ID", "Nombre", "Stock", "Precio", "Categoria", "Distribuidor"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableProducts.setModel(tableModel);

        // get cuenta cliente with account id
        List<Products> products = this.productsController.findProductsEntities();
        for (int i = 0; i < products.size(); i++) {
            Products pro = products.get(i);
            // Validate stock of product
            if (pro.getUnitsInStock() < 0) {
                // no more of this product, return
                continue;
            }
            Object[] obj = null;
            tableModel.addRow(obj);
            tableModel.setValueAt(pro.getProductID(), i, 0);
            tableModel.setValueAt(pro.getProductName(), i, 1);
            tableModel.setValueAt(pro.getUnitsInStock(), i, 2);
            tableModel.setValueAt(pro.getUnitPrice(), i, 3);
            tableModel.setValueAt(pro.getCategoryID().getCategoryName(), i, 4);
            tableModel.setValueAt(pro.getSupplierID().getName(), i, 5);
        }
    }

    private void loadCart() {
        // get new model
        Object tblCol[] = {"ID", "Nombre", "Cantidad", "Precio", "Categoria", "Distribuidor"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableCar.setModel(tableModel);
        // update and get ordes
        orders = ordersController.findOrders(orders.getOrderID());
        List<OrderDetails> orderDetails = new ArrayList(orders.getOrderDetailsCollection());
        for (int i = 0; i < orderDetails.size(); i++) {
            OrderDetails detail = orderDetails.get(i);
            Products pro = detail.getProducts();
            // Validate stock of product
            if (pro.getUnitsInStock() < 0) {
                // no more of this product, return
                continue;
            }
            Object[] obj = null;
            tableModel.addRow(obj);
            tableModel.setValueAt(pro.getProductID(), i, 0);
            tableModel.setValueAt(pro.getProductName(), i, 1);
            tableModel.setValueAt(pro.getQuantityPerUnit(), i, 2);
            tableModel.setValueAt(pro.getUnitPrice(), i, 3);
            tableModel.setValueAt(pro.getCategoryID().getCategoryName(), i, 4);
            tableModel.setValueAt(pro.getSupplierID().getName(), i, 5);
            // TODO: manage price
        }
    }

}
