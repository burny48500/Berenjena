package prototype.cli;

import prototype.CommandParser;

/**
 * The `CLIRunner` class serves as the entry point to run the command-line interface.
 */
public class CLIRunner {
    /**
     * Starts the command-line interface by displaying the initial menu.
     */
    public void run() {
        CommandParser.creation(); // This method will create some books, bookCopies and customers.
        CommandParser.initialMenu(true);
    }
}