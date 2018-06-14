/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.controller;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import com.sg.vendingmachine.service.VendingMachineInventoryException;
import com.sg.vendingmachine.service.VendingMachineServiceLayer;
import com.sg.vendingmachine.ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author juanm
 */
public class VendingMachineController {

    private final VendingMachineServiceLayer service;
    private final VendingMachineView view;

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {

        BigDecimal userMoney = BigDecimal.ZERO;
        String userChoice = "";
        boolean keepGoing = true;
        while (keepGoing) {

            List<Item> availableItems;
            try {

                availableItems = service.getAvailableItems();
            } catch (VendingMachinePersistenceException e) {
                view.printError("Error retreiving available items. " + e.getMessage());
                return;
            }
            //VM Displays ONLY available items
            view.printAvailableItems(availableItems);

            //User enters money
            userMoney = view.getUserMoney();

            //User enters Item selection    
            userChoice = view.getItemChoice();
            //Check that entry is not empty

            if (userChoice.trim().isEmpty()) {
                view.printMessage("Godbye!");
                keepGoing = false;
                return;
            }

            Item item;
            try {
                item = service.getOneItem(userChoice);
            } catch (VendingMachinePersistenceException | VendingMachineInventoryException e) {
                view.printError("Error retreiving single item. " + e.getMessage());
                run();
                return;
            }
            //Find a match for userChoice 
            boolean match;
            try {
                match = service.matchUserChoiceWithItem(availableItems, userChoice);
            } catch (VendingMachinePersistenceException ex) {
                view.printError("Error retreiving item information.");
                return;
            }
            if (!match) {
                System.out.println("Item doesn't exist.");
                run();
            }

            //Check if the user entered enough money for the item    
            boolean isValid = false;
            try {
                isValid = service.validateMoney(userMoney, userChoice);
            } catch (VendingMachinePersistenceException | VendingMachineInsufficientFundsException e) {
                view.printError("Not enough money || Error retriving item price. " + e.getMessage());
            }
//    Find how much money the used needed to purchase the item    
            if (!isValid) {
                String deficit;
                try {
                    deficit = service.getDeficit(userMoney, userChoice, item);
                } catch (VendingMachinePersistenceException e) {
                    view.printError("Error retriving item price. " + e.getMessage());
                    return;
                }
                view.printMessage(deficit + "Try again!");
                run();
                return;
            }
            //Get the change as BigDecimal for the user and update item inventory    
            BigDecimal change;
            try {
                change = service.dispatchItem(userMoney, item);
            } catch (VendingMachinePersistenceException e) {
                view.printError("Error updating item inventory. " + e.getMessage());
                return;
            }
            //Get change in coins
            view.printMessage("Thank you for shopping!\nYour change is $" + change + "\n===You will receive===\n" + service.getChangeInCoints(change) + "\n\n");

        }
    }

}
