package com.kvstore.commands;

import com.kvstore.entities.Attribute;
import com.kvstore.service.KVStoreService;

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
        System.out.println(String.join(",", list.toString()));
        /*for(Attribute attribute: list){
            System.out.print(attribute +",");
        }
        System.out.println();*/
    }
}
