/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carljmosca.hello.world.service;

import com.carljmosca.hello.world.model.Widget;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class WidgetService {

    private final static Logger LOGGER = Logger.getLogger(WidgetService.class.getName());

    private static final int WIDGETS = 10;
    private final List<Widget> widgets;

    public WidgetService() {
        widgets = new ArrayList<>();
    }

    public void create() {

        for (int i = 0; i < WIDGETS; i++) {
            LOGGER.info(String.format("Creating widget %d", i));
            widgets.add(new Widget(i, String.format("Name %d", i)));
        }
    }

    public void display() {
        LOGGER.info("Displaying widgets");
        widgets.forEach((widget) -> {
            System.out.println(widget.toString());
        });
    }
}
