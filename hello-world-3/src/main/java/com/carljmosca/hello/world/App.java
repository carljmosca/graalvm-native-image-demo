/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carljmosca.hello.world;

import com.carljmosca.hello.world.service.WidgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class App {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    
    public static void main(String[] args) {
        LOGGER.info("HelloWorld");
        WidgetService widgetService = new WidgetService();
        widgetService.create();
        widgetService.display();
    }
    
}
