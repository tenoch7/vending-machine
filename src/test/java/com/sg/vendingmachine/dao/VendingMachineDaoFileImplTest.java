/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.dao;

import com.sg.vendingmachine.dto.Item;
import java.math.BigDecimal;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author juanm
 */
public class VendingMachineDaoFileImplTest {
    
    private VendingMachineDao dao = new VendingMachineDaoFileImpl();
    
    public VendingMachineDaoFileImplTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws VendingMachinePersistenceException {
        List<Item> itemList = dao.getAllItems();
        for (Item item : itemList) {
            dao.removeItem(item.getCandyName());
        }
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getAllItems method, of class VendingMachineDaoFileImpl.
     * @throws com.sg.vendingmachine.dao.VendingMachinePersistenceException
     */
    @Test
    public void testGetAllItems() throws VendingMachinePersistenceException {
        Item item = new Item("Twix");
        item.setPrice(BigDecimal.ONE);
        item.setInventoryAmount(50);
        
        dao.addItem(item);
        
        Item item2 = new Item("Sneakers");
        item2.setPrice(BigDecimal.TEN);
        item2.setInventoryAmount(40);
        
        dao.addItem(item2);
        
        assertEquals(5, dao.getAllItems().size()); //inventory.txt set to 5
    }

    /**
     * Test of getItem method, of class VendingMachineDaoFileImpl.
     * @throws com.sg.vendingmachine.dao.VendingMachinePersistenceException
     */
    @Test
    public void testGetItem() throws VendingMachinePersistenceException {
        Item item = new Item("Twix");
        item.setPrice(BigDecimal.ONE);
        item.setInventoryAmount(50);
        
        dao.addItem(item);
        Item item2 = dao.getItem("Twix");
        
        assertEquals(item.getCandyName(), item2.getCandyName());
    }

    /**
     * Test of removeItem method, of class VendingMachineDaoFileImpl.
     * @throws com.sg.vendingmachine.dao.VendingMachinePersistenceException
     */
    @Test
    public void testRemoveItem() throws VendingMachinePersistenceException {
        Item item = new Item("Twix");
        item.setPrice(BigDecimal.ONE);
        item.setInventoryAmount(50);
        
        dao.addItem(item);
        
        Item item2 = new Item("Sneakers");
        item2.setPrice(BigDecimal.TEN);
        item2.setInventoryAmount(40);
        
        dao.addItem(item2);
        
        dao.removeItem("Sneakers");
        dao.removeItem("Sneakers");
        dao.removeItem("Twix");
        assertEquals(5, dao.getAllItems().size()); //set at 5 in the "inventory.txt"
        assertEquals(49, (dao.getItem("Twix").getInventoryAmount()));
        assertEquals(38, (dao.getItem("Sneakers").getInventoryAmount()));
    }
    
}
