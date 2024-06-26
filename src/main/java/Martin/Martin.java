package martin;

import java.util.ArrayList;
import java.io.IOException;

/**
 * The Martin class represents the main chatbot application.
 * It handles user input, processes commands, and interacts with the storage,
 * UI, and parser components.
 */
public class Martin {
    protected static ArrayList<Task> todoList = new ArrayList<>();
    private static String FILEPATH = "./data/martin.txt";

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;
    private Command command;

    /**
     * Constructs a new Martin object.
     * Initializes the storage with a fixed file path, a new Ui object, and a new
     * Parser object.
     */
    public Martin() {
        this.storage = new Storage(FILEPATH); // fixed file path for now
        this.ui = new Ui();
        this.parser = new Parser();

        todoList = storage.startUpSequence();
        this.tasks = new TaskList(todoList);
        assert tasks != null : "tasks should not be null";
        this.command = new Command(tasks, storage, ui, parser);
    }

    /**
     * Generates a response to the user input.
     *
     * @param input The user input.
     * @return The response generated by the chatbot.
     */
    public String getResponse(String input) {
        ChatbotKeyword command = parser.parse(input);
        String remainingWords = parser.removeCommandWord(input);
        String response = "";
        try {
            response = this.command.handleCommand(command, remainingWords);
        } catch (IOException e) {
            return "Error writing to file";
        }
        return response;
    }
}
