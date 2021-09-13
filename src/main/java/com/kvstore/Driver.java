package com.kvstore;

import com.kvstore.commands.Command;
import com.kvstore.commands.CommandExecutor;
import com.kvstore.commands.CommandExecutorFactory;
import com.kvstore.dao.InMemoryKVStore;
import com.kvstore.dao.KVStore;
import com.kvstore.entities.AttributeValueTypeFactory;
import com.kvstore.service.KVStoreService;
import com.kvstore.service.KVStoreServiceImpl;

import java.util.Scanner;

/**
 * @author shubham.gupta
 */
public class Driver {

    public static void main(String[] args) {
        AttributeValueTypeFactory attributeValueTypeFactory = new AttributeValueTypeFactory();
        KVStore kvStore = new InMemoryKVStore();
        KVStoreService kvStoreService = new KVStoreServiceImpl(kvStore, attributeValueTypeFactory);
        CommandExecutorFactory commandExecutorFactory = new CommandExecutorFactory(kvStoreService);

        Scanner sc = new Scanner(System.in);

        while(true) {
            String input = sc.nextLine();
            Command command = new Command(input);
            CommandExecutor commandExecutor = commandExecutorFactory.getCommandExecutor(command);
            if (commandExecutor.validate(command)) {
                commandExecutor.execute(command);
            } else {
                System.out.println("Invalid command");
                break;
            }
        }
    }
}
