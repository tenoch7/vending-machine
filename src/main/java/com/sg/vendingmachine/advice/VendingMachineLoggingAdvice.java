/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.vendingmachine.advice;

import com.sg.vendingmachine.dao.VendingMachineAuditDao;
import com.sg.vendingmachine.dao.VendingMachinePersistenceException;
import com.sg.vendingmachine.service.VendingMachineInsufficientFundsException;
import org.aspectj.lang.JoinPoint;

/**
 *
 * @author juanm
 */
public class VendingMachineLoggingAdvice {

    VendingMachineAuditDao auditDao;

    public VendingMachineLoggingAdvice(VendingMachineAuditDao auditDao) {
        this.auditDao = auditDao;
    }

    public void createAuditEntry(JoinPoint jp, Exception ex) {

        Object[] args = jp.getArgs();
        String auditEntry = jp.getSignature().getName() + ": " + " |Args: ";

        for (int i = 0; i < args.length; i++) {
            if (i != 0) {
                auditEntry = auditEntry + ", ";
            }
            auditEntry += args[i];
        }
        auditEntry += " |EX: " + ex;
        try {
            auditDao.writeAuditEntry(auditEntry);
        } catch (VendingMachinePersistenceException e) {
            System.err.println(
                    "ERROR: Could not create audit entry in LoggingAdvice.");
        }
    }
}
