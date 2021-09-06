package menu.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class GetStrings extends Input<String> {

    public GetStrings(Scanner scan) {
        super(scan);
    }
    String input = "";
    @Override
    public String getInput() {
        do {
            try {
                input = scan.next();
            } catch(InputMismatchException e){
                System.out.println("Please type an integer");
            }
        } while (isValid(input) == false);
        return input;
    }

    @Override
    public boolean isValid(String input) {
        switch(input) {
            case "":
            case " ":
                return false;
            default:
                return true;
        }
    }
}
