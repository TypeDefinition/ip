package chatbot.command;

import chatbot.task.Event;
import chatbot.task.Task;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;

public class EventCommand implements Command {
    public static final String KEYWORD = "event";

    @Override
    public CommandOutput execute(String[] input, ArrayList<Task> tasks) {
        // Parse input.
        String desc = "";
        LocalDate startDate = null;
        LocalTime startTime = null;
        LocalDate endDate = null;
        LocalTime endTime = null;
        try {
            int i = 1;
            while (i < input.length && !input[i].equals("/at")) {
                ++i;
            }
            desc = String.join(" ", Arrays.asList(input).subList(1, i));
            startDate = LocalDate.parse(input[i+1]);
            startTime = LocalTime.parse(input[i+2]);
            endDate = LocalDate.parse(input[i+3]);
            endTime = LocalTime.parse(input[i+4]);
        } catch (Exception ignored) {
        }

        String commandFormat = "Please enter the command as \"event <desc> /at <start_date> <start_time> <end_date> <end_time>\"\n" +
                "Dates must be in YYYY-MM-DD format.\n" +
                "Time must be in the HH:MM or HH:MM:SS format.";
        if (desc.isBlank()) {
            return new CommandOutput("Invalid description.\n" + commandFormat, false);
        } else if (startDate == null) {
            return new CommandOutput("Invalid start date.\n" + commandFormat, false);
        } else if (startTime == null) {
            return new CommandOutput("Invalid start time.\n" + commandFormat, false);
        } else if (endDate == null) {
            return new CommandOutput("Invalid end date.\n" + commandFormat, false);
        } else if (endTime == null) {
            return new CommandOutput("Invalid end time.\n" + commandFormat, false);
        } else if (startDate.isAfter(endDate)) {
            return new CommandOutput("Start date cannot be after end date.\n" + commandFormat, false);
        } else if (startDate.equals(endDate) && startTime.isAfter(endTime)) {
            return new CommandOutput("Start time cannot be after end time.\n" + commandFormat, false);
        }

        // Add event.
        Task task = new Event(desc, startDate, startTime, endDate, endTime);
        tasks.add(task);
        return new CommandOutput(String.format("Got it. I've added this task:\n  %s\nNow you have %d tasks in the list.", task, tasks.size()), true);
    }
}
