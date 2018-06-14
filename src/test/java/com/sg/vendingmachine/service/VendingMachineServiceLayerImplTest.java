/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.service;

import com.sg.vendingmachine.dao.VendingMachineDao;
import com.sg.vendingmachine.dao.VendingMachineDaoFileImpl;
import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author juanm
 */
public class VendingMachineServiceLayerImplTest {
    VendingMachineDao dao = new VendingMachineDaoFileImpl();
    private VendingMachineServiceLayer service = new VendingMachineServiceLayerImpl(dao);
    
    public VendingMachineServiceLayerImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAvailableItems method, of class VendingMachineServiceLayerImpl.
     */
    @Test
    public void testGetAvailableItems() throws Exception {
        /*My inventory file has 5 items*/
        assertEquals(5, service.getAvailableItems().size());
        
    }

    /**
     * Test of getOneItem method, of class VendingMachineServiceLayerImpl.
     */
    @Test
    public void testGetOneItem() throws Exception {
        Item itemTwix = service.getOneItem("Twix");
        assertEquals("Twix", itemTwix.getCandyName());
    }

    /**
     * Test of validateMoney method, of class VendingMachineServiceLayerImpl.
     */
    @Test
    public void testValidateMoney() throws Exception {
        Item itemTwix = service.getOneItem("Twix");
        
        itemTwix.getPrice();/*Twix price is 1.99*/
        /*validate returns true if the money that the user enters is equal or greater than the item price*/
        boolean validate = service.validateMoney(BigDecimal.valueOf(2.00), itemTwix.getCandyName());
        assertEquals(true, validate);
        
     
    }

    /**
     * Test of dispatchItem method, of class VendingMachineServiceLayerImpl.
     */
    @Test
    public void testDispatchItem() throws Exception {
        Item itemTwix = service.getOneItem("Twix"); 
        BigDecimal change = service.dispatchItem(BigDecimal.valueOf(3.0), itemTwix);//Twix price is $1.00
        //User entered $3.00 and the price of the itemTwix is $1.00. The change should be $2.00
        assertEquals(BigDecimal.valueOf(2.00), change);
    }
    
}
