/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application.controller;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author bruno
 */
public class Controller {

    /**
     * return new EntityManagerFactory
     */
    public static EntityManagerFactory manager = Persistence.createEntityManagerFactory("AppPU");
}
