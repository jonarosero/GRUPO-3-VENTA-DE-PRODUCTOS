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
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void login() {
        // Validate
        if (gui.inputCustomerID.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Ingresar un ID valido");
            return;
        }
        // Get costumer
        Customers c = customerController.findCustomers(gui.inputCustomerID.getText());
        // Validate db Response
        if (c == null) {
            JOptionPane.showMessageDialog(null, "No se encontraron resultados, Ingresar un ID valido");
            return;
        }
        // valid customer, do actions and change layout
        gui.inputCustomerID.setText("");
        gui.panelLogin.setVisible(false);
        gui.panelStatus.setVisible(true);
        gui.textCustomerID.setText(c.toString());
    }

    // load table data
    private void updateStatusTable(int id) {
        // get new model
        Object tblCol[] = {"Numero de Orden", "Fecha Pedido", "Fecha Enviado", "Nombre"};
        DefaultTableModel tableModel = new DefaultTableModel(null, tblCol);
        gui.tableStatus.setModel(tableModel);
        // no params were provided
        if (id == -1) {
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
