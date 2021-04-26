package Controllers;

import CSV.CustommerCsv;
import CSV.VillaCsv;
import CSV.HouseCsv;
import CSV.RoomCsv;


import java.util.Scanner;

public class MainController {
    static Scanner sc = new Scanner(System.in);
    static VillaCsv villaCsv = new VillaCsv();
    static HouseCsv houseCsv = new HouseCsv();
    static RoomCsv roomCsv = new RoomCsv();
    static CustommerCsv custommerCsv = new CustommerCsv();

    public static void main(String[] args) {
        displayMainMenu();
    }

    public static void displayMainMenu() {
        System.out.println("1.\tAdd New Services");
        System.out.println("2.\tShow Services");
        System.out.println("3.\tAdd New Customer");
        System.out.println("4.\tShow Information of Customer");
        System.out.println("5.\tAdd New Booking");
        System.out.println("6.\tShow Information of Employee");
        System.out.println("7.\tExit");
        System.out.print("Choose option : ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                addNewServies();
                break;
            case "2":
                showServices();
                break;
            case "3":
                addNewCustomer();
                break;
            case "4":
                showInformationOfCustomer();
                break;
            case "5":
                addNewBooking();
                break;
            case "6":
                showInformationOfEmployee();
                break;
            case "7":
                System.out.println("Thanks for using");
                System.exit(0);
            default:
                System.out.println("Pleases choose from 1 to 10");
                sc.nextLine();
                displayMainMenu();
        }


    }

    //
    //
    //
    // function 1 in menu
    public static void addNewServies() {
        System.out.println("1.\tAdd New Villa");
        System.out.println("2.\tAdd New House");
        System.out.println("3.\tAdd New Room");
        System.out.println("4.\tBack to menu");
        System.out.println("5.\tExit");
        System.out.print("Choose option : ");
        String chosse = sc.nextLine();
        switch (chosse) {
            case "1":
                addNewVilla();
                displayMainMenu();
                break;
            case "2":
                addNewHouse();
                displayMainMenu();
                break;
            case "3":
                addNewRoom();
                displayMainMenu();
                break;
            case "4":
                displayMainMenu();
                break;
            case "5":
                System.out.println("Thanks for using");
                System.exit(0);
            default:
                System.out.println("Pleases choose from 1 to 5");
        }


    }

    private static void addNewRoom() {
        roomCsv.addNew();
    }

    private static void addNewHouse() {
        houseCsv.addNew();
    }

    private static void addNewVilla() {
        villaCsv.addNew();
    }


    //
    //
    //
    // function 2 in menu
    private static void showServices() {
        System.out.println("\n");
        System.out.println("1.\tShow all Villa");
        System.out.println("2.\tShow all House");
        System.out.println("3.\tShow all Room");
        System.out.println("4.\tShow All Name Villa Not Duplicate");
        System.out.println("5.\tShow All Name House Not Duplicate");
        System.out.println("6.\tShow All Name Room Not Duplicate");
        System.out.println("7.\tBack to menu");
        System.out.println("8.\tExit");
        System.out.print("Choose option : ");
        String option = sc.nextLine();
        switch (option) {
            case "1":
                showAllVilla();
                displayMainMenu();
                break;
            case "2":
                showAllHouse();
                displayMainMenu();
                break;
            case "3":
                showAllRoom();
                displayMainMenu();
                break;
            case "4":
                showAllNameVillaNotDuplicate();
                displayMainMenu();
                break;
            case "5":
                showAllNameHouseNotDuplicate();
                displayMainMenu();
                break;
            case "6":
                showAllNameRoomNotDuplicate();
                displayMainMenu();
                break;
            case "7":
                displayMainMenu();
                break;
            case "8":
                System.out.println("Thanks for using");
                System.exit(0);
            default:
                System.out.println("Pleases choose from 1 to 8");
                sc.nextLine();
        }
    }

    private static void showAllNameRoomNotDuplicate() {
    }

    private static void showAllNameHouseNotDuplicate() {
    }

    private static void showAllNameVillaNotDuplicate() {
    }

    private static void showAllRoom() {
        roomCsv.showAll();
    }

    private static void showAllHouse() {
        houseCsv.showAll();
    }

    private static void showAllVilla() {
        villaCsv.showAll();
    }

    //
    //
    // function 3 in menu
    private static void addNewCustomer() {
        custommerCsv.addNew();
    }



    //
    //
    //
    // function 4 in menu
    private static void showInformationOfEmployee() {
    }

    // function 5 in menu
    private static void addNewBooking() {
    }


    // function 6 in menu
    private static void showInformationOfCustomer() {
        custommerCsv.showAll();
    }




}
