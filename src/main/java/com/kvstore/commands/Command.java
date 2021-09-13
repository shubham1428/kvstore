package com.kvstore.commands;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author shubham.gupta
 */
public class Command {
    private String name;
    private List<String> commandParams;

    public Command(String input){

        final List<String> tokensList = Arrays.stream(input.trim().split(" "))
                .map(String::trim)
                .filter(token -> (token.length() > 0)).collect(Collectors.toList());
        if (tokensList.size() == 0) {
            throw new RuntimeException("Invalid command");
        }

        name = tokensList.get(0);
        tokensList.remove(0);
        commandParams = tokensList;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getCommandParams() {
        return commandParams;
    }

    public void setCommandParams(List<String> commandParams) {
        this.commandParams = commandParams;
    }
}
