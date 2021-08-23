package duke;

import duke.command.Command;
import duke.task.Task;

import java.util.ArrayList;

/**
 * This is an application that helps the user to keep track of their tasks in a list.
 *
 * @author Muhammad Faruq
 */
public class Duke {
    private TaskList tasks;
    private final Ui ui;
    private final Storage storage;
    private boolean isRunning = true;

    /**
     * Class constructor specifying path for data file.
     *
     * @param filePath the path for the data.txt file
     */
    Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(new ArrayList< Task >());
        try{
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.errorFrame(e.getMessage());

        }
    }

    /**
     * Runs the program
     */
    public void run() {
        ui.showWelcome();
        while (isRunning) {
            String stringCommand = ui.readCommand();
            try {
            Command c = Parser.parse(stringCommand);
                c.execute(tasks, ui, storage);
                isRunning = c.isRunning();
                storage.save(tasks);
            } catch (DukeException e) {
                ui.errorFrame(e.getMessage());
            }
        }
    }

    /**
     * The main method for Duke application
     *
     * @param args The command line arguments
     */
    public static void main(String[] args){
        Duke duke = new Duke(".\\src\\main\\data\\data.txt");
        duke.run();
    }
}
