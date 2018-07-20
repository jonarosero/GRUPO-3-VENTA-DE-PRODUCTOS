package application.controller;

import application.controller.jpa.CustomersJpaController;
import application.model.Customers;
import application.view.GUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
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
    Customers customer;

    // view
    private GUI gui;

    // controlers
    CustomersJpaController customerController = new CustomersJpaController(Controller.Manager);

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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        if (command.equals("Login")) {
            login();
        } else if (command.equals("New")) {
            newOrder();
        }
    }

    /**
     * Set application for new Order
     */
    private void newOrder() {
        // change panel
        gui.panelStatus.setVisible(false);
        gui.panelOrder.setVisible(true);
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
        /*
        // get cuenta cliente with account id
        List<Cuentacliente> cc = this.cuentaClienteController.findCuentaclienteEntitiesByAccountID(id);
        for (int i = 0; i < cc.size(); i++) {
            // get account
            Cliente c = this.clienteController.findClienteById(cc.get(i).getHolderID());
            if (c == null) {
                continue;
            }
            Object[] obj = null;
            tableModel.addRow(obj);
            tableModel.setValueAt(c.getIdCliente(), i, 0);
            tableModel.setValueAt(c.getCedula(), i, 1);
            tableModel.setValueAt(c.getApellidos(), i, 2);
            tableModel.setValueAt(c.getNombres(), i, 3);
        }
         */
    }

}
