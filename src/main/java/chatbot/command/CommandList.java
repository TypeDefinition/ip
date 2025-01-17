package chatbot.command;

import java.util.Hashtable;

import chatbot.sfx.Sfx;
import chatbot.task.TaskList;

/**
 * A collection of commands.
 */
public class CommandList {
    private final Hashtable<String, Command> commands;
    private final Command unrecognizedCommand;

    /**
     * Constructs an empty command list.
     */
    public CommandList() {
        this.commands = new Hashtable<>();
        this.unrecognizedCommand = new Command("") {
            @Override
            public CommandOutput execute(String[] input, TaskList taskList) {
                return new CommandOutput("Unrecognised command.\nType \"help\" for a list of commands.",
                    Sfx.SFX_COMMAND_UNRECOGNISED);
            }
        };
    }

    /**
     * Associates the specified trigger with the specified command in this map.
     * If the parser previously contained a mapping for the trigger, the old command is replaced.
     *
     * @param command command to be associated with the specified trigger
     */
    public <T extends Command> void addCommand(T command) {
        this.commands.put(command.getTrigger(), command);
    }

    /**
     * Returns the output of the command associated with the specified user input.
     * If no command is associated, a default command is executed.
     *
     * @param input    the user input
     * @param taskList the task list to execute the command on
     * @return the output of the command associated with the specified user input.
     * If no command is associated, a default command is executed.
     */
    public CommandOutput executeCommand(String input, TaskList taskList) {
        String[] inputArr = input.split("\\s+");
        // Check for blank input.
        if (inputArr.length == 0) {
            return unrecognizedCommand.execute(inputArr, taskList);
        }
        return commands.getOrDefault(inputArr[0], unrecognizedCommand).execute(inputArr, taskList);
    }
}
