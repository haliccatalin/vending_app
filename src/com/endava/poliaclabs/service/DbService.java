package com.endava.poliaclabs.service;


import com.endava.poliaclabs.VendingMachine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Database Service - it is a service because it does not have a state.
 * Class that is used to interact with the file where the state of the VM is saved.
 */
public class DbService {

    private static final String FILE_PATH = "vendingMachine.json";

    /**
     * Used to serialize and deserialize the Vending Machine state.
     */
    private Gson gson;

    public DbService() {
        gson = new GsonBuilder().enableComplexMapKeySerialization()
                .setPrettyPrinting().create();
    }

    /**
     * Method used to serialize the vending machine object in a json format and write it in a file on the disk.
     * @param vm - the vending machine object
     */
    public void write(VendingMachine vm){
        Writer writer = null;
        try {
            writer = new OutputStreamWriter(new FileOutputStream(FILE_PATH), "UTF-8");
        }catch (Exception e){
            System.out.println("Failed create/write file." + e);
        }
        gson.toJson(vm, writer);
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("Failed close file." + e);
        }
    }

    /**
     * Read the file and initializes a VendingMachine with that data.
     * @return - a new vending machine initialized with the data from the file.
     */
    public VendingMachine read(){
        VendingMachine vm = null;
        try {
            vm = gson.fromJson(new FileReader(FILE_PATH), VendingMachine.class);
        } catch (FileNotFoundException e) {
            System.out.println("Failed to read file." + e);
        }
        return vm;
    }
}
