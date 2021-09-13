package com.kvstore.commands;

import com.kvstore.service.KVStoreService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author shubham.gupta
 */
public class CommandExecutorFactory {
    private final Map<String, CommandExecutor> commandExecutorMap = new HashMap<>();

    public CommandExecutorFactory(KVStoreService kvStoreService) {
        commandExecutorMap.put(UpdateCommandExecutor.COMMAND_NAME, new UpdateCommandExecutor(kvStoreService));
        commandExecutorMap.put(GetCommandExecutor.COMMAND_NAME, new GetCommandExecutor(kvStoreService));
        commandExecutorMap.put(SearchCommandExecutor.COMMAND_NAME, new SearchCommandExecutor(kvStoreService));
        commandExecutorMap.put(DeleteCommandExecutor.COMMAND_NAME, new DeleteCommandExecutor(kvStoreService));

    }

    public CommandExecutor getCommandExecutor(Command command) {
        final CommandExecutor commandExecutor = commandExecutorMap.get(command.getName());
        if (commandExecutor == null) {
            throw new InvalidCommandException("The following command is not configured");
        }
        return commandExecutor;
    }
}
