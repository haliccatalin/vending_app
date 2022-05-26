package com.endava.poliaclabs;

import com.endava.poliaclabs.model.CurrencyType;
import com.endava.poliaclabs.service.DbService;
import com.endava.poliaclabs.service.IoService;

/**
 * Main class of the application.
 */
public class Main {

    public static void main(String[] args){
        DbService dbService = new DbService();
        IoService ioService = new IoService();

        VendingMachine vm = dbService.read();

        vm.setDbService(dbService);
        vm.setIoService(ioService);
        vm.start();
    }
}
