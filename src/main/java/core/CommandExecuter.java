package core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public Set<Map.Entry<String, Command>> getCommandEntrySet() {
        return commandHashMap.entrySet();
    }

}
