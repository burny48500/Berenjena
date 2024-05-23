package prototype.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Importer {
    public static void main(String[] args) {
        Frame frame = new Frame();
        frame.setVisible(true);

        FileDialog fileDialog = new FileDialog(frame, "Select a CSV file", FileDialog.LOAD);
        fileDialog.setFile("*.csv");
        fileDialog.setVisible(true);

        String directory = fileDialog.getDirectory();
        String filename = fileDialog.getFile();
        frame.dispose();

        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {

                for (CSVRecord csvRecord : csvParser) {
                    String column1 = csvRecord.get("Title");
                    String column2 = csvRecord.get("ISBN");
                    String column3 = csvRecord.get("Author");
                    String column4 = csvRecord.get("Year");
                    new Book(column1, column2, column3, column4, "a");

                    System.out.println("Book " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    System.out.println("Title: " + column1);
                    System.out.println("ISBN: " + column2);
                    System.out.println("Author: " + column3);
                    System.out.println("Year: " + column4);
                    System.out.println("---------------\n\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }
}
