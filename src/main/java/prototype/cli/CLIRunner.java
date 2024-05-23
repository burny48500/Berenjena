package prototype.cli;

import prototype.CommandParser;

public class CLIRunner {
    public void run(){
        CommandParser parser = new CommandParser();
        parser.creation(); // This method will create some books, bookCopies and customers.
        parser.initialMenu(true);
    }
}
