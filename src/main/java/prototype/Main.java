package prototype;

import prototype.cli.CLIRunner;

/**
 * The `Main` class serves as the entry point of the application.
 */
public class Main {
    /**
     * The main method which initiates the CLIRunner and creates initial data.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        // CLIRunner call
        CLIRunner runner = new CLIRunner();
        runner.run();
    }
}