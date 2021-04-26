package Views;

import Model.Room;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class csvReader {
    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";
    public static final String FILE_HEADER = "id,serviceName,acreage,rentalCost,maxNumOfPeople,rentalType,freeService";
    public static final String file_room = "src/Data/room.csv";
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        display();
    }
    static void display(){
        System.out.println("1.\tadd room");
        System.out.println("2.\tShow room");
        System.out.println("3.\tExit");
        System.out.print("Choose option : ");
        String option = sc.next();
        switch (option) {
            case "1":
                add();
                display();
                break;
            case "2":
                show();

                break;
            case "3":
                System.out.println("Thanks for using");
                System.exit(0);
            default:
                System.out.println("Pleases choose from 1 to 10");
                sc.nextLine();display();
        }
    }

    static void show(){
        ArrayList<Room> listRoom = getFileCsvToListRoom();
        for(Room room : listRoom){
            System.out.println("-------------------");
            System.out.println("id: "+room.getId());
            System.out.println("serviceName: "+ room.getServiceName());
            System.out.println("acreage: "+ room.getAcreage());
            System.out.println("rentalCost: "+ room.getRentalCost());
            System.out.println("maxNumOfPeople: "+ room.getMaxNumOfPeople());
            System.out.println("rentalType: "+ room.getRentalType());
            System.out.println("freeService: "+ room.getFreeService());
        }
        display();
    };

    static void add(){
        ArrayList<Room> list = getFileCsvToListRoom();
        Room room =new Room();
        System.out.println("Nhập id ");
        room.setId(sc.next());
        System.out.println("Nhập serviceName ");
        room.setServiceName(sc.next());
        System.out.println("Nhập acreage ");
        room.setAcreage(sc.nextDouble());
        System.out.println("Nhập rentalCost ");
        room.setRentalCost(sc.nextDouble());
        System.out.println("Nhập maxNumOfPeople ");
        room.setMaxNumOfPeople(sc.nextInt());
        System.out.println("Nhập rentalType ");
        room.setRentalType(sc.next());
        System.out.println("Nhập freeService ");
        room.setFreeService(sc.next());

        list.add(room);
        writeListToFileCsv(list);
        System.out.println("Complete");
//        display();

    }

    static ArrayList<Room> getFileCsvToListRoom() {
        ArrayList<Room> listRoom = new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file_room));
            String line = "";
            while( (line = br.readLine()) != null ){
                String[] splitData = line.split(COMMA_DELIMITER);
                if(splitData[0].equals("id")){
                    continue;
                }
                Room room = new Room();
                room.setId(String.valueOf(splitData[0]));
                room.setServiceName(splitData[1]);
                room.setAcreage(Double.parseDouble(splitData[2]));
                room.setRentalCost(Double.parseDouble(splitData[3]));
                room.setMaxNumOfPeople(Integer.parseInt(splitData[4]));
                room.setRentalType(String.valueOf(splitData[5]));
                room.setFreeService(String.valueOf(splitData[6]));
                listRoom.add(room);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch(Exception e){
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return listRoom;
    };

    static void writeListToFileCsv(ArrayList<Room> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(NEW_LINE_SEPARATOR);
            for(Room room : list){
                fileWriter.append(room.getId());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getServiceName());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getAcreage()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getRentalCost()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(String.valueOf(room.getMaxNumOfPeople()));
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getRentalType());
                fileWriter.append(COMMA_DELIMITER);
                fileWriter.append(room.getFreeService());
                fileWriter.append(NEW_LINE_SEPARATOR);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
