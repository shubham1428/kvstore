package com.kvstore.commands;

import com.kvstore.service.KVStoreService;

/**
 * @author shubham.gupta
 */
public class DeleteCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "delete";
    private final KVStoreService kvStoreService;

    public DeleteCommandExecutor(KVStoreService kvStoreService) {
        this.kvStoreService = kvStoreService;
    }

    @Override
    public boolean validate(Command command) {
        return command.getCommandParams().size() == 1;
    }

    @Override
    public void execute(Command command) {
        kvStoreService.delete(command.getCommandParams().get(0));
    }
}
