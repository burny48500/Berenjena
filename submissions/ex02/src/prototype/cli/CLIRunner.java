package prototype.cli;

import prototype.CommandParser;

public class CLIRunner {
    public void run(){
        CommandParser parser = new CommandParser();
        parser.initialMenu(true);
    }
}
