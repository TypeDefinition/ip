package chatbot.command;

import java.util.Arrays;

import chatbot.sfx.Sfx;
import chatbot.task.Task;
import chatbot.task.TaskList;

public class FindCommand extends Command {
    public static final String TRIGGER = "find";
    public static final String FORMAT = TRIGGER + " <keyword>";

    public FindCommand() {
        super(TRIGGER);
    }

    @Override
    public CommandOutput execute(String[] input, TaskList taskList) {
        validateTrigger(input);

        if (input.length < 2) {
            return new CommandOutput("Error: Invalid arguments\nCommand format: " + FORMAT, Sfx.SFX_ERROR_INVALID_ARGS);
        }

        String keyword = String.join(" ", Arrays.asList(input).subList(1, input.length));
        Task[] tasks = taskList.find(keyword);

        // No tasks found
        if (tasks.length == 0) {
            return new CommandOutput("Sorry! I couldn't find anything...", Sfx.SFX_COMMAND_FIND_NONE);
        }

        // Tasks found
        StringBuilder output = new StringBuilder("Here's what I found:\n");
        for (int i = 0; i < tasks.length; ++i) {
            output.append(i + 1).append(". ").append(tasks[i].toString());
        }
        return new CommandOutput(output.toString(), Sfx.SFX_COMMAND_FIND);
    }
}
