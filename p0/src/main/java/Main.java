import menu.input.*;
import services.HealthServices;
import services.RestServices;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        Input<Integer> loginMenusInput = new LoginMenusInput(scan);
        Input<Integer> hIUserMenuInput = new HIUserMenuInput(scan);
        Input<String> getStrings = new GetStrings(scan);
        Input<Integer> restUserMenuInput = new RestUserMenuInput(scan);
        RestServices restServices = new RestServices();
        HealthServices healthServices = new HealthServices();

        MenuDriver driver = new MenuDriver(loginMenusInput, hIUserMenuInput, getStrings, restUserMenuInput, restServices, healthServices);
        driver.runStart();
    }
}
