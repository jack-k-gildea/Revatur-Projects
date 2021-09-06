package menu.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class RestUserMenuInput extends Input<Integer>{

    int menuOption = -1;
    public RestUserMenuInput(Scanner scan) {
        super(scan);
    }

    @Override
    public Integer getInput() {
        do {
            System.out.println("Enter a valid option from the menu");
            try {
                menuOption = scan.nextInt();
            } catch(InputMismatchException e){
                System.out.println("Please type an integer");
            }
        } while (isValid(menuOption) == false);
        return menuOption;
    }

    @Override
    public boolean isValid(Integer input) {
        switch (input) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
                return true;
            default:
                return false;
        }
    }
}
