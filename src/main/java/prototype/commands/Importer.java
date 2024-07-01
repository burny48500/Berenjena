package prototype.commands;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import prototype.IncorrectCSVException;

import java.awt.*;
import java.io.*;


/**
 * The Importer class provides functionality to import books, book copies, and customers
 * from CSV files into the system. It also handles file selection and test mode configurations.
 */
public class Importer {

    private String directory;
    private String filename;
    private boolean testMode = false;
    private final Manager manager = new Manager();
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
                        String column1 = csvRecord.get(title);
                        String column2 = csvRecord.get(author);
                        String column3 = csvRecord.get(isbn);
                        String column4 = csvRecord.get(publishingYear);

                        System.out.println("\nBook " + csvRecord.getRecordNumber());
                        System.out.println(separator);
                        System.out.println(title + ": " + column1);
                        System.out.println(author + ": " + column2);
                        System.out.println(isbn + ": " + column3);
                        System.out.println(publishingYear + ": " + column4);
                        System.out.println(separator);
                        if (!Book.sameBook(column3)) {
                            new Book(column1, column2, column3, column4);
                        } else {
                            System.out.println("The book is already created.");
                        }
                    } catch (IncorrectCSVException e) {
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
                        String column4 = csvRecord.get("CustomerId");

                        System.out.println("\nBook Copy " + csvRecord.getRecordNumber());
                        System.out.println(separator);
                        System.out.println(isbn + ": " + column1);
                        System.out.println(shelfLocation + ": " + column2);
                        System.out.println(publisher + ": " + column3);
                        System.out.println(separator);
                        if (Book.sameBook(column1)) {
                            if (Integer.parseInt(column4) != -1) {
                                if (manager.customerExistsTests(Integer.parseInt(column4))) {
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
                    System.out.println(e);
                    isCSVIncorrect = true;
                }
                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("The book copies were imported successfully!");
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
                Customer.setText(false); // No message of adding every time a new customer
                boolean isCSVIncorrect = false;
                try {
                    for (CSVRecord csvRecord : csvParser) {
                        String column1 = csvRecord.get(lastName);
                        String column2 = csvRecord.get(firstName);
                        String column3 = csvRecord.get(mail);
                        String column4 = csvRecord.get(phoneNumber);

                        System.out.println("\nCustomer " + csvRecord.getRecordNumber());
                        System.out.println(separator);
                        System.out.println(firstName + ": " + column2);
                        System.out.println(lastName + ": " + column1);
                        System.out.println(mail + ": " + column3);
                        System.out.println(phoneNumber + ": " + column4);
                        System.out.println(separator);

                        if (!Customer.sameCustomer(column3)) {
                            new Customer(column1, column2, column3, column4);
                        } else {
                            System.out.println("Customer with same mail is already created.\n");
                        }
                    }

                } catch (IncorrectCSVException e) {
                    isCSVIncorrect = true;
                }
                if (isCSVIncorrect) {
                    System.out.println("The CSV file is incorrect.");
                } else {
                    System.out.println("All customers were imported successfully!");
                }
                Customer.setText(true); // Put it again as normally
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
        this.directory = directory;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }
}