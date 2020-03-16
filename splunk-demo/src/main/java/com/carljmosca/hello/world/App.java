/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carljmosca.hello.world;

import com.carljmosca.hello.world.service.WidgetService;
import java.util.logging.Level;
import java.util.logging.Logger;


public class App {
    
    private final static Logger LOGGER = Logger.getLogger(App.class.getName());
     
    public static void main(String[] args) {
        LOGGER.log(Level.INFO, "HelloWorld");
        WidgetService widgetService = new WidgetService();
        widgetService.create();
        widgetService.display();
    }
    
}
