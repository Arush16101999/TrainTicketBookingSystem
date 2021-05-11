import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;

import java.util.Scanner;


public class TrainStation extends Application  {

    private static ArrayList<Passenger> waitingRoom = new ArrayList();
    private static PassengerQueue trainQueue = new PassengerQueue();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        addPassengerFromTextFile();
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("A: Add Passenger to train Queue-------------------------------------------------->\n" +
                    "V: View Queue ------------------------------------------------------------------->\n" +
                    "D: Delete Passenger from Train Queue -------------------------------------------->\n" +
                    "S: Save details ----------------------------------------------------------------->\n"+
                    "L: Load Data from text file ----------------------------------------------------->\n"+
                    "R: Run simulation --------------------------------------------------------------->\n"+
                    "Q: Quit from the system");
            System.out.println("Enter one of the Above Options : ");
            String userOption = sc.next().toLowerCase();
            switch (userOption) {
                case "a":
                    addPassengerToQueue();
                    break;
                case "v":
                    viewTrainQueue();
                    break;

                case "d":
                    deleteFromTrainQueue();
                    break;

                case "s":
                    saveTrainQueue();
                    break;

                case "l":
                    loadQueue();
                    break;


                case "q":
                    System.exit(0);
                    break;


                default:
                    System.out.println("Wrong Option Try Again---------------------------------------------------------");
            }
        }
    }


    private void addPassengerFromTextFile() {

        //READ FROM CW1 TEXT FILE USER  NAME SEAT NUMBER AND SECONDS IN QUE
        //ADD ALL PASSENGERS FROM TEXT FILE TO WAITING ROOM

        try (Scanner scanner = new Scanner(new FileInputStream("file.txt"))) {
            try {
                while (scanner.hasNextLine()) {
                    String fName = scanner.next();
                    int seatNumber = scanner.nextInt();
                    //cw asks for delay to be generated using 3 dice
                    int delay1 = 1 + (int) (Math.random() * ((6 - 1) + 1));
                    int delay2 = 1 + (int) (Math.random() * ((6 - 1) + 1));
                    int delay3 = 1 + (int) (Math.random() * ((6 - 1) + 1));
                    int secondsInQueue = delay1+delay2+delay3; // total delay is the sum of 3 dice
                    //create new passenger class object
                    Passenger passengerDetails = new Passenger();

                    //add all variables to object instance
                    passengerDetails.setFirstName(fName);

                    passengerDetails.setSeatNumber(seatNumber);
                    passengerDetails.setSecondsInQueue(secondsInQueue);
                    waitingRoom.add(passengerDetails);
                }
            } catch (Exception e) {
                System.out.println(" Load unsuccessful");
                System.out.println();
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println(waitingRoom);
        System.out.println("------------------------------------------------------------------------------------------");
    }


    //Add Passenger from Text to Train Queue
    private void addPassengerToQueue() {

        Stage stage = new Stage();
        stage.setTitle("Waiting Room");
        stage.setScene(new Scene(room(stage)));
        stage.showAndWait();


    }

    private static ScrollPane room(Stage stage) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(400);
        scrollPane.setPadding(new Insets(10));

        TilePane tilePane = new TilePane();
        tilePane.setPrefWidth(585);
        tilePane.setPrefWidth(585);
        tilePane.setHgap(10);
        tilePane.setVgap(10);
        tilePane.setAlignment(Pos.TOP_LEFT);
        tilePane.setStyle("-fx-background-color:#66ff99");

        for (int i = 0; i < waitingRoom.size(); i++) {
            if (waitingRoom.get(i) != null) {
                AnchorPane passengerDisplay = new AnchorPane();
                passengerDisplay.setPrefWidth(280);
                passengerDisplay.setPrefHeight(150);
                passengerDisplay.setStyle("-fx-background-color:#ccffff");

                Label nameLabel = new Label("Passenger Name");
                nameLabel.setLayoutX(15);
                nameLabel.setLayoutY(20);

                Label nameDisplay = new Label(waitingRoom.get(i).getFirstName());
                nameDisplay.setLayoutX(35);
                nameDisplay.setLayoutY(45);

                Button addButton = new Button("Add\n to \n queue");
                addButton.setId(Integer.toString(i));
                addButton.setLayoutX(190);
                addButton.setLayoutY(20);
                addButton.setPrefWidth(90);
                addButton.setPrefHeight(110);
                addButton.setStyle("-fx-background-color:#ff4d4d");

                addButton.setOnAction(event -> {
                    int INDEX = Integer.parseInt(addButton.getId());
                    if (trainQueue.isFull()) {
                        System.out.println("Sorry Train Queue is Full");
                        System.out.println("--------------------------------------------------------------------------");
                    } else {

                        trainQueue.add(waitingRoom.get(INDEX));
                        System.out.println("---------------------------------------------------------------------------");
                    }

                    stage.close();

                });
                passengerDisplay.getChildren().addAll(nameLabel,nameDisplay,addButton);
                tilePane.getChildren().add(passengerDisplay);
            }
        }
        scrollPane.setContent(tilePane);
        return scrollPane;
    }

    //Display Train Queue
    private void viewTrainQueue() {
        trainQueue.display();

        System.out.println("-------------------------------------------------------------------------------------------");
    }

    //Delete from train queue by taking passenger details input
    private void deleteFromTrainQueue() {
        trainQueue.remove();

        System.out.println("-------------------------------------------------------------------------------------------");

    }

    public void saveTrainQueue() {
        ObjectOutputStream outputStream = null;
        try {
            outputStream = new ObjectOutputStream(new FileOutputStream("trainQueue.txt"));
            outputStream.writeObject(trainQueue);
            System.out.println("Saved!");
        } catch (IOException ex) {
            System.out.println("Error while Saving!");
        } finally {
            //Close the ObjectOutputStream
            try {
                if (outputStream != null) {
                    outputStream.flush();
                    outputStream.close();
                }
            } catch (IOException ex) {
                System.out.println("Closing file stream error!");
            }
        }
        System.out.println("------------------------------------------------------------------------------------------");
    }


    public void loadQueue() {
        ObjectInputStream objectIn = null;
        try {
            FileInputStream fileIn = new FileInputStream("trainQueue.txt");
            objectIn = new ObjectInputStream(fileIn);
            trainQueue = (PassengerQueue) objectIn.readObject();
            System.out.println("Loaded!");
        } catch (Exception ex) {
            System.out.println("Error in load");
        } finally {
            try {
                if (objectIn != null) {addPassengerFromTextFile();
                    objectIn.close();
                }
            } catch (IOException ex) {
                System.out.println("Closing file stream error!");
            }
        }

    }

}
