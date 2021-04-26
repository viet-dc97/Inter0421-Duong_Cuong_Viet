package CSV;

import Model.Room;

import java.util.ArrayList;

public abstract class CsvCommon<E> {
    public static final String COMMA_DELIMITER = ",";
    public static final String NEW_LINE_SEPARATOR = "\n";

    abstract ArrayList<E> getFileCsvToList();
    abstract void writeListToCsv(ArrayList<E> list);
    abstract void addNew();
    abstract void showAll();
}
