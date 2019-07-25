package core;

import java.util.HashMap;

public class CommandExecuter {
    private HashMap<String, Command> commandHashMap;

    public CommandExecuter() {
         commandHashMap = new HashMap<>();
    }

    public void registerCommand(Class<? extends Command> commandType) {
        Command command = null;
        try {
            command = commandType.newInstance();
            commandHashMap.put(command.getToken(), command);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public Command getCommandByPrefix(String prefix) {
        return commandHashMap.getOrDefault(prefix, null);
    }

    public boolean isPrefixRegistered(String prefix) {
        return commandHashMap.containsKey(prefix);
    }
}
