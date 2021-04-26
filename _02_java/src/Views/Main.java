package Views;

import Model.Services;
import Model.Villa;

public class Main {
    public static void main(String[] args) {
        Services villa1 = new Villa("villa01" , "Villa",
                30, 6000000, 6,
                "Villa","3 sao", null,50,3);

        villa1.showInfor();
    }
}
