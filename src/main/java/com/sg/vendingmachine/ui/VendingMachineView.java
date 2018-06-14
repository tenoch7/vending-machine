/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.ui;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author juanm
 */
public class VendingMachineView {

    private UserIO io;

    public VendingMachineView(UserIO io) {
        this.io = io;
    }

    public void printAvailableItems(List<Item> items) {
        io.print("=== Available items === ");
        for (Item currentItem : items) {
            io.print(currentItem.getCandyName() + " $" + currentItem.getPrice());
        }
        io.print("=======================");
    }

    public void printError(String message) {
        io.print(message);
    }

    public BigDecimal getUserMoney() {
        BigDecimal userMoney = io.readBigDecimal("Please insert money:");
        return userMoney;
    }

    public String getItemChoice() {
        String itemChoice = io.readString("Please enter the NAME of the item OR \nHit ENTER to Exit.");

        return itemChoice;
    }

    public void printMessage(String message) {
        io.print(message);
    }
}
