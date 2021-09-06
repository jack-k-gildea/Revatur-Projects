package menu.input;

import java.util.Scanner;

public abstract class Input<T> {
    protected static Scanner scan;

    public Input(Scanner scan) {
        Input.scan = scan;
    }

    public abstract T getInput();

    public abstract boolean isValid(T input);
}
