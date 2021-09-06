package menu.input;

import java.util.InputMismatchException;
import java.util.Scanner;

public class HIUserMenuInput extends Input<Integer> {

    public HIUserMenuInput(Scanner scan) {
        super(scan);
    }

    Integer menuOption = -1;

    @Override
    public Integer getInput() {
        do {
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
            case 1:
            case 2:
            case 3:
            case 4:
                return true;
            default:
                return false;
        }
    }
}
