package prototype.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import prototype.CommandParser;

import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class Importer {

    private static String directory;
    private static String filename;
    private static boolean testMode = false;

    public static String getDirectory() {
        return directory;
    }

    public static void setDirectory(String directory) {
        Importer.directory = directory;
    }

    public static String getFilename() {
        return filename;
    }

    public static void setFilename(String filename) {
        Importer.filename = filename;
    }
    public static void setTestMode(boolean testMode) {
        Importer.testMode = testMode;
    }
    public static void selectFile() {
        if (!testMode) {
            Frame frame = new Frame();
            frame.setVisible(true);

            FileDialog fileDialog = new FileDialog(frame, "Select a CSV file", FileDialog.LOAD);
            fileDialog.setFile("*.csv");
            fileDialog.setVisible(true);

            directory = fileDialog.getDirectory();
            filename = fileDialog.getFile();
            frame.dispose();
        }
    }

    public static void importBook(){
        selectFile();
        if (filename != null) {
            File selectedFile = new File(directory, filename);

            boolean isCSVIncorrect = false;

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)){

                for (CSVRecord csvRecord : csvParser) {
                    try {
                        String column1 = csvRecord.get("Title");
                        String column2 = csvRecord.get("Author");
                        String column3 = csvRecord.get("ISBN");
                        String column4 = csvRecord.get("Year");

                        System.out.println("\nBook " + csvRecord.getRecordNumber());
                        System.out.println("---------------");
                        System.out.println("Title: " + column1);
                        System.out.println("Author: " + column2);
                        System.out.println("ISBN: " + column3);
                        System.out.println("Year: " + column4);
                        System.out.println("---------------");
                        if (!Book.sameBook(column3)) {
                            new Book(column1, column2, column3, column4);
                        } else {
                            System.out.println("The book is already created.");
                        }
                    } catch (IllegalArgumentException e) {
                        isCSVIncorrect = true;
                        break;
                    }
                }

                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("All books were imported successfully!");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }

    public static void importBookCopy(){
        selectFile();
        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)){
                boolean isCSVIncorrect = false;
                try {
                    for (CSVRecord csvRecord : csvParser) {
                        String column1 = csvRecord.get("ISBN");
                        String column2 = csvRecord.get("Shelf Location");

                        System.out.println("\nBook Copy " + csvRecord.getRecordNumber());
                        System.out.println("---------------");
                        System.out.println("ISBN: " + column1);
                        System.out.println("Shelf Location: " + column2);
                        System.out.println("---------------");
                        if (Book.sameBook(column1)) {
                            new BookCopy(column1, column2);
                        } else {
                            System.out.println("Book copy not added, cause no book has that ISBN");
                        }
                    }
                } catch (IllegalArgumentException e) {
                    isCSVIncorrect = true;
                }
                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("All books were imported successfully!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }

    public static void importCustomer(){
        selectFile();

        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)){
                Customer.setText(false); // No message of adding every time a new customer
                boolean isCSVIncorrect = false;
                try {
                    for (CSVRecord csvRecord : csvParser) {
                        String column1 = csvRecord.get("Name");
                        String column2 = csvRecord.get("First Name");
                        String column3 = csvRecord.get("Mail");
                        String column4 = csvRecord.get("Phone Number");

                        System.out.println("\nCustomer " + csvRecord.getRecordNumber());
                        System.out.println("---------------");
                        System.out.println("First Name: " + column2);
                        System.out.println("Name: " + column1);
                        System.out.println("Mail: " + column3);
                        System.out.println("Phone Number: " + column4);
                        System.out.println("---------------");

                        if (!Customer.sameCustomer(column3)) {
                            new Customer(column1, column2, column3, column4);
                        } else {
                            System.out.println("Customer with same mail is already created.\n");
                        }
                    }

                } catch (IllegalArgumentException e){
                    isCSVIncorrect = true;
                }
                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("All books were imported successfully!");
                }
                Customer.setText(true); // Put it again as normally
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }
}