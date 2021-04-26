package CSV;

import Commons.RegexMatches;
import Model.Custommer;
import Model.House;
import Model.Services;
import Views.csvReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CustommerCsv extends CsvCommon<Custommer> {
    public static final String FILE_HEADER = "name,birthDay,gender,idCard,phone,email,customerType,address,services";
    public static final String file_room = "src/Data/custommer.csv";
    public static final String Email_pattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String idCard_pattern = "[0-9]{9}";
    public static final String gender_pattern = "(male)|(female)|(unknow)";
    public static final String name_pattern = "";
    public static final String birthday_pattern = "";
    static Scanner sc = new Scanner(System.in);
    RegexMatches regexMatches = new RegexMatches();

    @Override
    public ArrayList<Custommer> getFileCsvToList() {
        String line = "";
        ArrayList<Custommer> list = new ArrayList<>();
        BufferedReader br = null;
        try {

            br = new BufferedReader(new FileReader(file_room));
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                if (values[0].equals("name")) {
                    continue;
                }
                Custommer custommer = new Custommer();
                custommer.setName(values[0]);
                custommer.setBirthDay(values[1]);
                custommer.setGender(values[2]);
                custommer.setIdCard(values[3]);
                custommer.setPhone(values[4]);
                custommer.setEmail(values[5]);
                custommer.setCustomerType(values[6]);
                custommer.setAddress(values[7]);
                custommer.setServices(values[8]);
                list.add(custommer);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;

    }

    @Override
    public void writeListToCsv(ArrayList<Custommer> list) {
        FileWriter fileWriter = null;
        try {
            fileWriter = new FileWriter(file_room);
            fileWriter.append(FILE_HEADER);
            fileWriter.append(csvReader.NEW_LINE_SEPARATOR);
            for(Custommer custommer : list){
                fileWriter.append(custommer.getName());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getBirthDay());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getGender());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getIdCard());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getPhone());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getEmail());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getCustomerType());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getAddress());
                fileWriter.append(csvReader.COMMA_DELIMITER);
                fileWriter.append(custommer.getServices());
                fileWriter.append(csvReader.NEW_LINE_SEPARATOR);


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

    @Override
    public void addNew() {

    }

    @Override
    public void showAll() {
        ArrayList<Custommer> list = getFileCsvToList();
        for(Custommer custommer : list){
            System.out.println("-------------------");
            custommer.showInfor();
        }
        System.out.println("\n");
    }
}
