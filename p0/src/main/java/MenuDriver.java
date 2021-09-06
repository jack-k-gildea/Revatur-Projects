import menu.*;
import menu.input.*;
import services.HealthServices;
import models.*;
import services.RestServices;

public class MenuDriver {
    Input<Integer> loginMenusInput;
    Input<Integer> hIUserMenuInput;
    Input<String> getStrings;
    Input<Integer> RestUserMenuInput;
    RestServices restServices;
    HealthServices healthServices;

    public MenuDriver(Input<Integer> loginMenusInput, Input<Integer> hIUserMenuInput, Input<String> getStrings, Input<Integer> restUserMenuInput, RestServices restServices, HealthServices healthServices) {
        this.loginMenusInput = loginMenusInput;
        this.hIUserMenuInput = hIUserMenuInput;
        this.getStrings = getStrings;
        this.RestUserMenuInput = restUserMenuInput;
        this.restServices = restServices;
        this.healthServices = healthServices;
    }

    String username; //class scope because it is used in basically all methods
    public void runStart(){ //login menus
        Start.typeOfUser();
        switch(loginMenusInput.getInput()) {
            case 1: // health inspector user
                LoginOrCreateAccount.loginOrCreate();
                switch(loginMenusInput.getInput()) {
                    case 1: // create health account
                        healthCreate();
                    case 2: // login to health account
                        username = healthLogin();
                        runHealthMenu();
                        break;
                    case 3: runStart(); // back to login
                        break;
                }
                break;
            case 2: // rest user
                LoginOrCreateAccount.loginOrCreate();
                switch(loginMenusInput.getInput()) {
                    case 1: // create rest user
                        restCreate();
                    case 2: // login to rest user
                        username = restLogin();
                        runRestMenu();
                        break;
                    case 3: runStart();// back to login
                        break;
                }
            case 3: // exit
                System.out.println("Good-bye");
                System.exit(0);
        }
    }

    ///////////////////////////////Restaurant User Functions //////////////////////////////////////

    private void runRestMenu(){ //restaurant user menu
        RestUserMenu.runRestUserMenu();
        switch (RestUserMenuInput.getInput()){
            case 1: viewRestFridges(username);
                runRestMenu();
                break;
            case 2: addRestFridge(username);
                runRestMenu();
                break;
            case 3: removeRestFridge(username);
                runRestMenu();
                break;
            case 4: viewFood(username);
                runRestMenu();
            case 5: addFood(username);
                runRestMenu();
                break;
            case 6: removeFood(username);
                runRestMenu();
                break;
            case 7:moveFood(username);
                runRestMenu();
                break;
            case 8: addHI(username);
                runRestMenu();
                break;
            case 0: runStart();
                break;

        }
    }

    private String viewFood(String username) {
        viewRestFridges(username);
        System.out.println("Which fridge do you want to view food from?");
        String fridgeName = getStrings.getInput();
        this.restServices.viewFood(username, fridgeName);
        return fridgeName;
    }

    private void viewRestFridges(String username){
        this.restServices.restGetFridges(username);
    }

    private void addRestFridge(String username){
        int run = 0;
        System.out.println("Enter fridge name:");
        String fridgeName = getStrings.getInput();
        if(!this.restServices.checkFridgeName(username, fridgeName)){
            System.out.println("\n Fridge name already in use, try a different fridge name: \n");
            addRestFridge(username); //recursive call
            run++;
        }
        if(run < 1) { //prevents adding multiple fridges with the same values
            this.restServices.restAddFridge(username, fridgeName);
        }
    }

    private void removeRestFridge(String username){
        if(this.restServices.getFridgeCount(username) <= 1){ //check if last fridge because last fridge can't be deleted
            System.out.println("\n No available fridges to delete \n");
        }
        else{
            viewRestFridges(username);
            System.out.println("Which fridge do you want to delete?");
            String fridgeName = getStrings.getInput();
            if(!this.restServices.checkFridgeName(username, fridgeName)){ //check if fridge name exists in system, returns false if it does
                if(this.restServices.isEmpty(username,fridgeName)) { //check if food currently in fridge
                    this.restServices.deleteFridge(username, fridgeName);
                    }
                else {
                    System.out.println("\n Fridge is not empty. would you like to: \n" +
                        "1) Throw the food away \n" +
                        "2) Move to a different fridge \n" +
                        "3) cancel");
                    switch (loginMenusInput.getInput()) {
                        case 1:
                            this.restServices.deleteFridge(username, fridgeName); //deletes fridge and all food
                            break;
                        case 2:
                            this.restServices.restGetFridgesExceptCurrentOrFull(username, fridgeName); //displays all other fridges besides fridge to be deleted
                            System.out.println("Move to which fridge?");
                            String newFridge = getStrings.getInput();
                            if (this.restServices.moveAllFood(username, fridgeName, newFridge)) { //moves all food from fridge to be deleted and adds them to new fridge, returns true if all food transferred
                                this.restServices.deleteFridge(username, fridgeName);
                            }
                            else{
                                if(restServices.getFridgeCount(username) > 2) { // if they have more fridges available than the one being deleted and the one that filled up
                                    this.restServices.restGetFridgesExceptCurrentOrFull(username, fridgeName); //displays all other fridges besides fridge to be deleted
                                    System.out.println("Move to which fridge?");
                                    String newFridge2 = getStrings.getInput();
                                    if (this.restServices.moveAllFood(username, fridgeName, newFridge2)) { //moves all food from fridge to be deleted and adds them to new fridge, returns true if all food transferred
                                        this.restServices.deleteFridge(username, fridgeName);
                                    }
                                }
                                else{
                                    System.out.println("No other Fridges to move to");
                                }
                            }
                            break;
                        case 3:
                            break;
                        }
                    }
                }
            }
        }

    private void addFood(String username){
        viewRestFridges(username); //displays available fridges to choose from
        System.out.println("Which fridge do you want to add items to?");
        String fridgeName = getStrings.getInput();
        if(this.restServices.canAddItems(username, fridgeName)){ //checks if there is an empty spot in fridge
            System.out.println("What food would you like to add:");
            String foodName = getStrings.getInput();
            this.restServices.restAddFood(new FridgeModel(username, fridgeName, foodName));
        }
        else{
            System.out.println("\n Fridge full \n");
        }
    }

    private void removeFood(String username){
        String fridgeName = viewFood(username);
        System.out.println("Which food item do you want to remove?");
        String foodName = getStrings.getInput();
        if(this.restServices.checkFoodName(new FridgeModel(username, fridgeName, foodName))) {
            this.restServices.removeFood(new FridgeModel(username, fridgeName, foodName));
        }
        else{
            System.out.println("\n Food not present in the fridge, make sure you spelled it correctly and matched the case \n");
            removeFood(username);
        }
    }

    private void moveFood(String username){
        if(restServices.getFridgeCount(username) > 1) {
            viewRestFridges(username);
            System.out.println("Which fridge do you want to move from?");
            String oldFridge = getStrings.getInput();
            if (!restServices.checkFridgeName(username, oldFridge)) {
                System.out.println("Which fridge do you want to move to?");
                String newFridge = getStrings.getInput();
                if (!restServices.checkFridgeName(username, newFridge)) {
                    this.restServices.viewFood(username, oldFridge);
                    System.out.println("Which food item do you want to move?");
                    String foodItem = getStrings.getInput();
                    if (this.restServices.checkFoodName(new FridgeModel(username, oldFridge, foodItem))) {
                        this.restServices.moveFood(new FridgeModel(username, oldFridge, foodItem), newFridge);
                    } else {
                        System.out.println("\n Food not present in the fridge, make sure you spelled it correctly and matched the case \n");
                        moveFoodTryAgain(username, oldFridge, newFridge);
                    }
                } else {
                    System.out.println("\nTarget fridge name does not exist, enter a fridge name from the menu, make sure you spelled it correctly and matched the case \n ");
                    moveFoodSecondFridge(username, oldFridge);
                }
            } else {
                System.out.println("Fridge name does not exist, enter a fridge name from the menu, match the spelling and the case");
                moveFood(username);
            }
        }
        else {
            System.out.println("Only 1 fridge available");
        }
    }

    private void moveFoodSecondFridge(String username, String oldFridge) {
        System.out.println("Which fridge do you want to move to?");
        String newFridge = getStrings.getInput();
        if (!restServices.checkFridgeName(username, newFridge)) {
            this.restServices.viewFood(username, oldFridge);
            System.out.println("Which food item do you want to move?");
            String foodItem = getStrings.getInput();
            if (this.restServices.checkFoodName(new FridgeModel(username, oldFridge, foodItem))) {
                this.restServices.moveFood(new FridgeModel(username, oldFridge, foodItem), newFridge);
            } else {
                System.out.println("\n Food not present in the fridge, make sure you spelled it correctly and matched the case \n");
                moveFoodTryAgain(username, oldFridge, newFridge);
            }
        } else {
            System.out.println("\nTarget fridge name does not exist, enter a fridge name from the menu, make sure you spelled it correctly and matched the case \n ");
            moveFoodSecondFridge(username, newFridge);
        }
    }

    private void moveFoodTryAgain(String username, String oldFridge, String newFridge){
        System.out.println("Which food item do you want to move?");
        String foodItem = getStrings.getInput();
        if(this.restServices.checkFoodName(new FridgeModel(username, oldFridge, foodItem))) {
            this.restServices.moveFood(new FridgeModel(username, oldFridge, foodItem), newFridge);
        }
        else{
            System.out.println("\n Food not present in the fridge, make sure you spelled it correctly and matched the case \n");
        }
    }

    private void addHI(String username){
        viewRestFridges(username);
        System.out.println("which fridge do you want to add a health inspector to?");
        String fridgeName = getStrings.getInput();
        System.out.println("Type the username of the health inspector:");
        String HIName = getStrings.getInput();
        this.healthServices.addHI(new HIFridgeModel(HIName, username, fridgeName));
    }

    private void restCreate() {
        int run = 0; //int to check if program is recursively called from an already taken username
        System.out.println("Create your username:");
        String username = getStrings.getInput();
        if (this.restServices.checkUsername(username)) {
            System.out.println("\n Username taken \n");
            restCreate();
            run++;
        }
        if (run < 1) { // if program has been recursively called, prevents this section from being run multiple times
            System.out.println("Create your password:");
            String password = getStrings.getInput();
            this.restServices.restUserCreateAcct(username, password);
        }
    }

    private String restLogin(){
        System.out.println("Enter username:");
        String username = getStrings.getInput();
        System.out.println("Enter Password:");
        String password = getStrings.getInput();
        if(this.restServices.restUserCheckLogin(username, password)){
            System.out.println("\n Logged in \n");
        }
        else{
            System.out.println("\n Incorrect username or password, try again \n");
            restLogin();
        }
        return username;
    }


    /////////////////////////////// Health Inspector Functions //////////////////////////////////

    public void runHealthMenu(){
        HIUserMenu.hIMenu();
        switch (hIUserMenuInput.getInput()){
            case 1: viewFridges(username); // display available fridges
                runHealthMenu(); // return to health inspector menu
                break;
            case 2: viewFridgeItems(username); // view food items in a specific fridge
                runHealthMenu(); // return to health inspector menu
                break;
            case 3: deleteFridgeItem(username); // Delete specific item from specific fridge
                runHealthMenu(); // return to health inspector menu
                break;
            case 4:
                System.out.println("\n Good-bye \n");
                runStart(); // back to login menu
                break;

        }
    }

    private void healthCreate() {
        int run = 0;
        System.out.println("Create your username:");
        String username = getStrings.getInput();
        if (this.healthServices.hICheckUsernameAvailability(username)) { //check if username already in system
            System.out.println("\n Username taken \n");
            healthCreate(); //recursive call
            run++;
        }
        if (run < 1) { //prevents recursive call from printing repeatedly
            System.out.println("Create your password:");
            String password = getStrings.getInput();
            this.healthServices.hICreateAcct(username, password);
        }
    }

    private String healthLogin(){
        System.out.println("Enter username:");
        String username = getStrings.getInput();
        System.out.println("Enter Password:");
        String password = getStrings.getInput();
        if(this.healthServices.hICheckLogin(username, password)){ //check if username/password combo is in the users database
            System.out.println("\n Logged in \n");
        }
        else{
            System.out.println("\n Incorrect username or password, try again \n");
            healthLogin();
        }
        return username;
    }

    private void viewFridges(String username){this.healthServices.hIGetFridges(username);}

    private void viewFridgeItems(String username){
        viewFridges(username); // displays restaurant locations and fridge names for user to use as reference for input
        System.out.println("Type the username of the restaurant the fridge is located:");
        String restUser = getStrings.getInput();
        System.out.println("Type the name of the fridge you would like to view");
        String fridgeName = getStrings.getInput();
        this.healthServices.hIGetFridgeFood(new HIFridgeModel(username, restUser,fridgeName));
    }

    private void deleteFridgeItem(String username){ //////////// if time try to use viewFridgeItems to replace top 6 lines
        viewFridges(username);
        System.out.println("Type the username of the restaurant the fridge is located:");
        String restUser = getStrings.getInput();
        System.out.println("Type the name of the fridge you would like to view:");
        String fridgeName = getStrings.getInput();
        if(this.healthServices.checkFridgeName(new HIFridgeModel(username,restUser,fridgeName))) {
            this.healthServices.hIGetFridgeFood(new HIFridgeModel(username, restUser, fridgeName));
            System.out.println("type the name of the food item you would like to remove:");
            String foodItem = getStrings.getInput();
                this.healthServices.hIDeleteFood(new FridgeModel(restUser, fridgeName, foodItem));
        }
        else{
            System.out.println("Fridge not recognized or access not allowed");
        }
    }

}
