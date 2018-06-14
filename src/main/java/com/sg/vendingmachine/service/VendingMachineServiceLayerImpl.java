/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author juanm
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    VendingMachineDao dao;

    public VendingMachineServiceLayerImpl(VendingMachineDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Item> getAvailableItems() throws VendingMachinePersistenceException {
        //filters inventory to oly display items with InventoryAmount greater than zero.
        return dao.getAllItems().stream().filter(i -> i.getInventoryAmount() > 0).collect(Collectors.toList());
    }

    @Override
    public Item getOneItem(String candyName) throws VendingMachinePersistenceException, VendingMachineInventoryException {
        if (dao.getItem(candyName) == null) {
            throw new VendingMachineInventoryException("Item not on inventory.");
        } else {
            return dao.getItem(candyName);
        }
    }

    @Override
    public boolean validateMoney(BigDecimal userMoney, String userChoice) throws VendingMachinePersistenceException, VendingMachineInsufficientFundsException {

        int moneyVsPrice = userMoney.compareTo(dao.getItem(userChoice).getPrice());
        if (moneyVsPrice == 0 || moneyVsPrice == 1)/*User money is equal or greater than price*/ {
            return true;
        } else /*Price is greater than user Money*/ {
            throw new VendingMachineInsufficientFundsException("Insuficcient funds.");

        }
    }

    @Override
    public BigDecimal dispatchItem(BigDecimal userMoney, Item item) throws VendingMachinePersistenceException {
        BigDecimal change = userMoney.subtract(item.getPrice());

        dao.removeItem(item.getCandyName());
        return change;
    }

    @Override
    public String getChangeInCoints(BigDecimal change) {
        change = change.multiply(BigDecimal.valueOf(100));
        BigDecimal quarters[] = change.divideAndRemainder(BigDecimal.valueOf(25));
        int quarterCoins = quarters[0].intValue();

        BigDecimal dimes[] = quarters[1]/*quarters remainder*/.divideAndRemainder(BigDecimal.valueOf(10));
        int dimeCoins = dimes[0].intValue();

        BigDecimal nickels[] = dimes[1].divideAndRemainder(BigDecimal.valueOf(5));
        int nickelCoins = nickels[0].intValue();

        int pennyCoins = nickels[1].intValue();

        return ("Quarters: " + quarterCoins + "\nDimes: " + dimeCoins + "\nNickels: " + nickelCoins + "\nPennies: " + pennyCoins);
    }

    @Override
    public String getDeficit(BigDecimal userMoney, String userChoice, Item item) throws VendingMachinePersistenceException {

        BigDecimal moneyNeeded = item.getPrice().subtract(userMoney);
        String deficit = ("Sorry, " + item.getCandyName() + " costs $" + item.getPrice()
                + ".\nYou only entered $" + userMoney + ". You are $" + moneyNeeded + " short.");
        return deficit;
    }

    @Override
    public boolean matchUserChoiceWithItem(List<Item> availableItems, String userChoice) throws VendingMachinePersistenceException {
        boolean match = availableItems.stream().anyMatch(i -> i.getCandyName().equals(userChoice));
        return match;
    }

}
