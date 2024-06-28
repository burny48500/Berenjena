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


/**
 * The Importer class provides functionality to import books, book copies, and customers
 * from CSV files into the system. It also handles file selection and test mode configurations.
 */
public class Importer {

    private String directory;
    private String filename;
    private boolean testMode = false;
    private Book book;
    private Importer importer;
    private Manager manager;
    private Customer customer;

    /**
     * Opens a file dialog for the user to select a CSV file. If test mode is enabled,
     * this method does nothing.
     */
    public void selectFile() {
        if (!testMode) {
            Frame frame = new Frame();
            try {
                frame.setVisible(true);

                FileDialog fileDialog = new FileDialog(frame, "Select a CSV file", FileDialog.LOAD);
                fileDialog.setFile("*.csv");
                fileDialog.setVisible(true);

                directory = fileDialog.getDirectory();
                filename = fileDialog.getFile();
            } finally {
                frame.dispose();
            }
        }
    }

    /**
     * Imports book data from a selected CSV file. The CSV file should have the columns:
     * "Title", "Author", "ISBN", and "Year".
     */
    public void importBook() {
        selectFile();
        if (filename != null) {
            File selectedFile = new File(directory, filename);

            boolean isCSVIncorrect = false;

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {

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
                        if (!book.sameBook(column3)) {
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

    /**
     * Imports book copy data from a selected CSV file. The CSV file should have the columns:
     * "ISBN", "Shelf Location", "Publisher", and "CustomerId".
     */
    public void importBookCopy() {
        selectFile();
        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
                boolean isCSVIncorrect = false;
                try {
                    for (CSVRecord csvRecord : csvParser) {
                        String column1 = csvRecord.get("ISBN");
                        String column2 = csvRecord.get("Shelf Location");
                        String column3 = csvRecord.get("Publisher");
                        String column4 = csvRecord.get("CumstomerId");

                        System.out.println("\nBook Copy " + csvRecord.getRecordNumber());
                        System.out.println("---------------");
                        System.out.println("ISBN: " + column1);
                        System.out.println("Shelf Location: " + column2);
                        System.out.println("Publisher: " + column3);
                        System.out.println("---------------");
                        if (book.sameBook(column1)) {
                            if (Integer.parseInt(column4) != -1) {  // checks whether the book copy is on loan (has an assigned positive integer)
                                if (manager.customerExistsTests(Integer.parseInt(column4))) {   // checks whether customer already exists in the system
                                    new BookCopy(column1, column2, column3, column4);
                                } else {
                                    System.out.println("The according Customer does not exist in the System. Please import first!");
                                }
                            } else {
                                new BookCopy(column1, column2, column3);
                            }
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
                    System.out.println("All book copies were imported successfully!");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }


    /**
     * Imports customer data from a selected CSV file. The CSV file should have the columns:
     * "Name", "First Name", "Mail", and "Phone Number".
     */
    public void importCustomer() {
        selectFile();

        if (filename != null) {
            File selectedFile = new File(directory, filename);

            try (Reader reader = new FileReader(selectedFile);
                 CSVParser csvParser = CSVFormat.Builder.create().setHeader().setSkipHeaderRecord(true).build().parse(reader)) {
                customer.setText(false); // No message of adding every time a new customer
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

                        if (!customer.sameCustomer(column3)) {
                            new Customer(column1, column2, column3, column4);
                        } else {
                            System.out.println("Customer with same mail is already created.\n");
                        }
                    }

                } catch (IllegalArgumentException e) {
                    isCSVIncorrect = true;
                }
                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("All customers were imported successfully!");
                }
                customer.setText(true); // Put it again as normally
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File selection cancelled by user.");
        }
    }

    //GETTERS AND SETTERS
    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        importer.directory = directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        importer.filename = filename;
    }

    public void setTestMode(boolean testMode) {
        importer.testMode = testMode;
    }

}