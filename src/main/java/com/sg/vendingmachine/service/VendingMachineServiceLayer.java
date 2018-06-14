/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author juanm
 */
public interface VendingMachineServiceLayer {

    public List<Item> getAvailableItems() throws VendingMachinePersistenceException;
    
    public Item getOneItem(String candyName) throws VendingMachinePersistenceException, VendingMachineInventoryException;
    
    public boolean validateMoney(BigDecimal userMoney, String userChoice) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException;
    
    public BigDecimal dispatchItem(BigDecimal userMoney, Item item) throws VendingMachinePersistenceException;
    
    public String getChangeInCoints (BigDecimal change);
    
    public String getDeficit(BigDecimal userMoney, String userChoice, Item item) throws VendingMachinePersistenceException;
    
    public boolean matchUserChoiceWithItem(List<Item> availableItems, String userChoice) throws VendingMachinePersistenceException;
    
//    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
}
