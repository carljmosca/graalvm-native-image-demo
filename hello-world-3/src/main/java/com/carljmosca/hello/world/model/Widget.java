/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.carljmosca.hello.world.model;

public class Widget {
    
    private int id;
    private String name;

// Commented out no args constructor to see how we can work with GraalVM    
//    public Widget() {
//    }

    public Widget(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Widget{" + "id=" + id + ", name=" + name + '}';
    }
    
}
