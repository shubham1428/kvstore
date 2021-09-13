package com.kvstore.commands;

import com.kvstore.entities.Attribute;
import com.kvstore.entities.Key;
import com.kvstore.service.KVStoreService;
import javafx.util.Pair;

import java.util.List;

/**
 * @author shubham.gupta
 */
public class SearchCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "search";
    private final KVStoreService kvStoreService;

    public SearchCommandExecutor(KVStoreService kvStoreService) {
        this.kvStoreService = kvStoreService;
    }

    @Override
    public boolean validate(Command command) {
        return command.getCommandParams().size() == 2;
    }

    @Override
    public void execute(Command command) {
        List<Pair<Key,List<Attribute>>> keys = kvStoreService.search(command.getCommandParams().get(0), command.getCommandParams().get(1));
        //iterate over pair to print result
        System.out.println(String.join(",", keys.toString()));
    }
}