package prototype.prompt;

import java.util.Scanner;

public class Prompter {
    private Scanner sc = new Scanner(System.in);
    public String nextInput()
    {
        return sc.nextLine();
    }
    public boolean hasnextLine(){
        return sc.hasNextLine();
    }

    public void close() {
        sc.close();
    }
}

