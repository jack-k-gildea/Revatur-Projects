package menu.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class LoginMenusInput extends Input<Integer> {

    public LoginMenusInput(Scanner scan) {
        super(scan);
    }
    Integer login = -1;

    @Override
    public Integer getInput() {
        do {
            System.out.println("Enter a valid option from the menu");
            try {
                login = scan.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Please type an integer");
            }
        } while (isValid(login) == false);
        return login;
    }

    @Override
    public boolean isValid(Integer input) {
        switch (input) {
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }
}
