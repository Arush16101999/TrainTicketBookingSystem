import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.FlowPane;
import javafx.scene.Scene;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

import java.lang.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class Final extends Application {

    static final int SEATING_CAPACITY = 42;

    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage){
        HashMap<String, Integer> customer = new HashMap<String, Integer>();

        //file to save customer details
        File file = new File("file.txt");


        primaryStage.setTitle("Seat Booking");

        Scanner Sc = new Scanner(System.in);

        while (true) {
            System.out.println("**** A/C Compartment in Denuwara Menike train to Budulla ****");
            System.out.println("**** A/C Compartment in Denuwara Menike train to colombo ****");
            System.out.println();

            //menu that user can pick what they want
            System.out.println("           A:  Add a customer to a seat.................... \n" +
                    "           V:  View all seats............................... \n" +
                    "           D:  Delete customer from seat.................... \n" +
                    "           F:  Find the seat for a given customers name..... \n" +
                    "           S:  Store program data in to file................ \n" +
                    "           L:  Load program data from file.................. \n" +
                    "           O:  View seats Ordered alphabetically by name.... \n" +
                    "           E:  View Empty Seats............................. \n" +
                    "           Q:  Exit Program................................. \n" +
                    "Enter Input : ");

            String input = Sc.next().toLowerCase();

            //switch case with methods
            switch (input) {
                case "a":
                    addCustomer(customer);
                    break;

                case "e":
                    emptySeats(customer);
                    break;

                case "v":
                    viewAllSeats(customer);
                    break;

                case "d":
                    deleteCustomer(customer);
                    break;
                case "f":
                    findSeat(customer);
                    break;

                case "s":
                    storeData(customer, file);
                    break;

                case "l":
                    loadProgram(customer, file);
                    break;

                case "o":
                    viewOder();
                    break;

                case "q":
                    quit();
                    break;

                default:
                    System.out.println("Your Input is Invalid......");
                    System.out.println("Enter only a, v, d, f, s, l, o, q");
                    continue;
            }
        }

    }

    private void viewAllSeats(HashMap<String, Integer> customer) {
        Stage stage = new Stage();
        stage.setTitle("View Booked seats");
        Label label1 = new Label("TRAIN SEAT MAP (ALL SEATS)");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(25);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);


        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button button = new Button("Seat " + i);
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);

            if (customer.containsValue(Integer.parseInt(button.getId()))) {
                button.setText("Booked");
                button.setStyle("-fx-background-color:red; -fx-text-fill: white");
            }else{
                button.setStyle("-fx-background-color:#99ffcc");
            }

        }
        Scene scene = new Scene(flowPane, 550, 300);
        stage.setScene(scene);
        stage.showAndWait();


    }


    private void emptySeats(HashMap<String, Integer> customer) {
        Stage stage = new Stage();
        stage.setTitle("Empty Seats");

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(25);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);


        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button button = new Button("Seat " + i);
            button.setStyle("-fx-background-color:#99ffcc");
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);

            if (customer.containsValue(Integer.parseInt(button.getId()))) {
                button.setVisible(false);
            }
        }
        Scene scene = new Scene(flowPane, 550, 300);
        stage.setScene(scene);
        stage.showAndWait();


    }

    private void addCustomer(HashMap<String, Integer> customer) {

        Scanner Sc = new Scanner(System.in);
        System.out.println();
        System.out.println("Enter Customer Name: ");
        String addCustomerName = Sc.next();


        //booking on GUI
        Stage stage = new Stage();
        stage.setTitle("Booking Seat");
        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(25);
        flowPane.setVgap(10);
        flowPane.setOrientation(Orientation.VERTICAL);


        for (int i = 1; i <= SEATING_CAPACITY; i++) {
            Button button = new Button("Seat " + i);
            button.setStyle("-fx-background-color:#99ffcc");
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);


            if (customer.containsValue(Integer.parseInt(button.getId()))) {
                button.setDisable(false);
                button.setStyle("-fx-background-color:red; -fx-text-fill:white");
            } else {
                button.setOnAction(event -> {
                    button.setStyle("-fx-background-color:red; -fx-text-fill: white");

                    //pop up Window
                    Stage dialogStage = new Stage();
                    dialogStage.initModality(Modality.WINDOW_MODAL);

                    Button book = new Button("  BOOK  ");
                    book.setStyle("-fx-background-color:red");
                    DatePicker date = new DatePicker();

                    VBox vbox = new VBox(new Text(" Date "), date, book);
                    vbox.setSpacing(20);
                    vbox.setStyle("fx-font: 20 timesRoman ; -fx-base:#b6e7c9");
                    vbox.setAlignment(Pos.TOP_LEFT);
                    vbox.setPadding(new Insets(15));

                    book.setOnAction(event1 -> {
                        LocalDate date1 = date.getValue();
                        customer.put(addCustomerName, Integer.parseInt(button.getId()));
                        System.out.println(addCustomerName + " you have successfully booked seat " + button.getId());
                        System.out.println("on " + date1);
                        dialogStage.close();

                    });
                    dialogStage.setScene(new Scene(vbox, 200, 200));
                    dialogStage.showAndWait();
                });
            }
        }
        Button close = new Button("Close");
        close.setStyle("fx-font: 20 timesRoman ; -fx-base:#b6e7c9");
        flowPane.getChildren().add(close);
        close.setOnAction(event -> {
            stage.close();

            System.out.println();
            System.out.println("BACK TO MENU.......");
            System.out.println();
        });
        Scene scene = new Scene(flowPane, 550, 300);
        stage.setScene(scene);
        stage.showAndWait();

    }

    private void deleteCustomer(HashMap<String, Integer> customer) {
        Scanner Sc = new Scanner(System.in);
        System.out.println(customer);
        System.out.println();
        System.out.println("Enter Customer's Name: ");
        String seatName = Sc.next();

        if (customer.containsKey(seatName)) {
            customer.remove(seatName);

            System.out.println(customer);
            System.out.println(seatName +" Successfully Deleted");
        } else {
            System.out.println("Seat number is not found... in  " + seatName);

        }



    }

    private void findSeat(HashMap<String, Integer> customer) {
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter Customer Name: ");
        String customerName = Sc.next();

        if (customer.containsKey(customerName)) {
            Integer find = customer.get(customerName);
            System.out.println(find);
            System.out.println("You have found the Seat for Given Customer Name...");

        } else {

            System.out.println("Given Customer didn't Book eny Seats... ");
        }

    }

    private void storeData(HashMap<String, Integer> customer, File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new FileWriter(file));
            for (Map.Entry<String, Integer> entry : customer.entrySet()) {
                bw.write(entry.getKey() + " " + entry.getValue());
                bw.newLine();
            }
            bw.flush();
            System.out.println("Successfully saved");
        } catch (IOException e) {
            System.out.println("Error in saving");
            e.printStackTrace();


        } finally {
            try {
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private void loadProgram(HashMap<String, Integer> customer, File file) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                String name = parts[0].trim();
                Integer seat = Integer.parseInt(parts[1].trim());


                if (!name.equals("") && !seat.equals("")) {
                    customer.put(name, seat);
                }

            }
            System.out.println("Data Successfully loaded");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void viewOder() {

    }


    private void quit() {
        System.out.println("Thank You for Booking Seats");
        System.out.println(".......Safe journey....... ");
        System.exit(0);
    }

}

