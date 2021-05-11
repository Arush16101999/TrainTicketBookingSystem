import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class PassengerQueue implements Serializable {

    private  LinkedList<Passenger> queueArray = new LinkedList<Passenger>();
    private int first;
    private int last;
    private int maxStayInQueue;
    private int maxLength;
//constructor is not compulsory


    public int getMaxStayInQueue() {
        int secondsToCheckBoardingTicket =  1 + (int) (Math.random() * ((4 - 1) + 1));  //print random value from 1-3
        return secondsToCheckBoardingTicket;
    }

    public int getMaxLength() {
        int length =queueArray.size();
        return length;
    }

    public void display() {

        Stage stage=new Stage();
        stage.setTitle("TRAIN QUEUE");
        FlowPane flowPane=new FlowPane();
        flowPane.setHgap(25);
        flowPane.setVgap(10);
        flowPane.setStyle("-fx-background-color:#66ffff");
        flowPane.setOrientation(Orientation.HORIZONTAL);

        for(int i=0; i <=queueArray.size()-1;i++) {
            if (!(queueArray.isEmpty())) {
                Button button = new Button(queueArray.get(i).getFirstName()); //Passenger Name display in button
                button.setId(Integer.toString(i));
                button.setStyle("-fx-background-color:#ff3333");
                flowPane.getChildren().add(button);
            }
        }

        int takenSpace = queueArray.size();
        for(int i =takenSpace; i < 42; i++ ){
            Button button = new Button("EMPTY");
            button.setId(Integer.toString(i));
            flowPane.getChildren().add(button);
            button.setStyle("-fx-background-color:#99ffcc");
        }

        Scene scene = new Scene(flowPane, 550, 300);
        stage.setScene(scene);
        stage.showAndWait();

        //queue display in console
        System.out.println(queueArray);

    }
    public void add(Passenger next) {
        ArrayList<String> names = new ArrayList<>();
        for(int i =0 ; i<=queueArray.size()-1;i++){
            names.add(queueArray.get(i).getFirstName());
        }

        if (!names.contains(next.getFirstName())) {
            queueArray.add(next);
            System.out.println("SuccessFully Added Customer -"+ next.getFirstName() + "- to Train Queue");
        }else{
            System.out.println("passenger is already in queue");
        }
    }

    public void remove() {
        System.out.println(queueArray);
        Scanner Sc = new Scanner(System.in);
        System.out.println("Enter the FirstName of Passenger: ");
        String Name = Sc.next();
        int removeCounter = 0;
        for (int i=0; i <= queueArray.size()-1; i++) {
            Passenger removalPassenger = queueArray.get(i);
            if (queueArray.get(i).getFirstName() != null && Name.equals(queueArray.get(i).getFirstName())) {
                queueArray.remove(removalPassenger);
                removeCounter++;
                i--; //decrease the counter by one to prevent index error in for loop
                System.out.println("This passenger has been deleted:");
                System.out.println("First Name: " + removalPassenger.getFirstName());
                System.out.println("Seat Number: " + removalPassenger.getSeatNumber() + "\n");
            }
        }

        //print message if person with that name is not found
        if (removeCounter == 0){
            System.out.println("there is no passenger with the name " + Name + "\n");
        }

    }

    public Boolean isEmpty() {
        if(queueArray.size() ==0){
            return true;
        }
        return false;

    }

    public Boolean isFull() {
        if ((queueArray.size()==21)) {
            return true;
        }
        return false;
    }

}
