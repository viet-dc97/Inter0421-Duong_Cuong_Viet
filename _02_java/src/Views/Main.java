package Views;

import Model.Services;
import Model.Villa;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("nhập a ");
        String a = sc.next();
        String b = a.substring(6);
        System.out.println(b);
        int c = java.time.LocalDate.now().getYear();
        System.out.println(c);
    }
}
