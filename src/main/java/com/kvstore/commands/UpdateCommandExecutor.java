package com.kvstore.commands;

import com.kvstore.entities.AttributeKey;
import com.kvstore.entities.Attribute;
import com.kvstore.entities.AttributeValueType;
import com.kvstore.service.KVStoreService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shubham.gupta
 */
public class UpdateCommandExecutor implements CommandExecutor {
    public static final String COMMAND_NAME = "update";
    private final KVStoreService kvStoreService;

    public UpdateCommandExecutor(KVStoreService kvStoreService) {
        this.kvStoreService = kvStoreService;
    }

    @Override
    public boolean validate(Command command) {
        return command.getCommandParams().size()%2 == 1;
    }

    @Override
    public void execute(Command command) {
        String key = command.getCommandParams().get(0);
        command.getCommandParams().remove(0);
        List<Attribute> attributeList = new ArrayList<>();
        for(int i=0; i<command.getCommandParams().size(); i+=2){
            AttributeKey attributeKey = new AttributeKey(command.getCommandParams().get(i));
            Object attributeValue = command.getCommandParams().get(i+1);
            attributeList.add(new Attribute(attributeKey, attributeValue));
        }
        kvStoreService.update(key, attributeList);
    }
}