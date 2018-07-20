package com;

import application.controller.Controller;
import application.view.GUI;

/**
 *
 * @author bruno
 */
public class Main {

    public static void main(String[] args) {
        GUI gui = new GUI();
        Controller c = new Controller(gui);
        c.start();
    }

}
