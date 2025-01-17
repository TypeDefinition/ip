package chatbot.command;

import chatbot.sfx.Sfx;
import chatbot.task.Task;
import chatbot.task.TaskList;

public class MarkCommand extends Command {
    public static final String TRIGGER = "mark";
    public static final String FORMAT = TRIGGER + " <index>\n" + "Index range: 1 to <size of list>";

    public MarkCommand() {
        super(TRIGGER);
    }

    @Override
    public CommandOutput execute(String[] input, TaskList taskList) {
        validateTrigger(input);

        try {
            int index = Integer.parseInt(input[1]);
            Task task = taskList.get(index - 1);
            task.setDone(true);
            return new CommandOutput(String.format("Good job! I've marked this task as done:\n  %s", task),
                Sfx.SFX_COMMAND_MARK);
        } catch (Exception e) {
            return new CommandOutput("Error: Invalid index\nCommand format: " + FORMAT, Sfx.SFX_ERROR_INVALID_INDEX);
        }
    }
}
