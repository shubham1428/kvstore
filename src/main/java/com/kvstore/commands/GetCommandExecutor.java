package com.kvstore.commands;

import com.practice.kvstore.entities.Attribute;
import com.practice.kvstore.service.KVStoreService;

import java.util.List;

/**
 * @author shubham.gupta
 */
public class GetCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "get";
    private final KVStoreService kvStoreService;


    public GetCommandExecutor(KVStoreService kvStoreService) {
        this.kvStoreService = kvStoreService;
    }

    @Override
    public boolean validate(Command command) {
        return command.getCommandParams().size() == 1;
    }

    @Override
    public void execute(Command command) {
        List<Attribute> list = kvStoreService.get(command.getCommandParams().get(0));
        if(list == null){
            System.out.println("No entry found for " + command.getCommandParams().get(0));
            return;
        }
        for(Attribute attributePair: list){
            System.out.print(attributePair +",");
        }
        System.out.println();
    }
}
