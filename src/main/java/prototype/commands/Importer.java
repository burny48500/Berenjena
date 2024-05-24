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

    public static void selectFile() {
        Frame frame = new Frame();
        frame.setVisible(true);

        FileDialog fileDialog = new FileDialog(frame, "Select a CSV file", FileDialog.LOAD);
        fileDialog.setFile("*.csv");
        fileDialog.setVisible(true);

        directory = fileDialog.getDirectory();
        filename = fileDialog.getFile();
        frame.dispose();
    }

    public static void importBook(){
        selectFile();
        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)){

                for (CSVRecord csvRecord : csvParser) {
                    String column1 = csvRecord.get("Title");
                    String column2 = csvRecord.get("ISBN");
                    String column3 = csvRecord.get("Author");
                    String column4 = csvRecord.get("Shelf Location");

                    System.out.println("\nBook " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    System.out.println("Title: " + column1);
                    System.out.println("ISBN: " + column2);
                    System.out.println("Author: " + column3);
                    System.out.println("Shelf Location: " + column4);
                    System.out.println("---------------");
                    if (!Book.sameBook(column2)){
                        new Book(column1, column2, column3, column4);
                    }else {
                        System.out.println("The book is already created.");
                    }
                }
                System.out.println("All books were imported successfully!");
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

                for (CSVRecord csvRecord : csvParser) {
                    String column1 = csvRecord.get("ISBN");

                    System.out.println("\nBook Copy " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    System.out.println("ISBN: " + column1);
                    System.out.println("---------------");
                    if (Book.sameBook(column1)){
                        new BookCopy(column1);
                    }else {
                        System.out.println("Book copy not added, cause no book has that ISBN");
                    }
                }
                System.out.println("All book copies were imported successfully!");
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
                Customer.setText(false);
                for (CSVRecord csvRecord : csvParser) {
                    String column1 = csvRecord.get("Mail");
                    String column2 = csvRecord.get("Phone Number");

                    System.out.println("\nCustomer " + csvRecord.getRecordNumber());
                    System.out.println("---------------");
                    System.out.println("Mail: " + column1);
                    System.out.println("Phone Number: " + column2);
                    System.out.println("---------------");

                    if (!Customer.sameCustomer(column1)){
                        new Customer(column1, column2);
                    }else {
                        System.out.println("Customer with same mail is already created.\n");
                    }
                }
                System.out.println("Customers imported successfully!");
                Customer.setText(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }
}