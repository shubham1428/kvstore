package com.kvstore.commands;

/**
 * @author shubham.gupta
 */
public interface CommandExecutor {
    public boolean validate(Command command);
    public void execute(Command command);
}
