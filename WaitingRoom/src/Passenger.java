import java.io.Serializable;

public class Passenger implements Serializable {

    private String firstName;
    private String surname;
    private int seatNumber;
    private int secondsInQueue;

    public String getFirstName() {

        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {

        return surname;
    }

    public void setSurname(String surname) {

        this.surname = surname;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public int getSecondsInQueue() {

        return secondsInQueue;
    }

    public void setSecondsInQueue(int secondsInQueue) {

        this.secondsInQueue = secondsInQueue;
    }

    public void display() {

    }
    // I didn't use sirName on my CW1
    // create first Name and seatNumber
    @Override
    public String toString() {
        return (" First Name: "+this.getFirstName()+" | "+
                " Seat Number: "+this.getSeatNumber()+" | "+
                " Seconds in Queue: "+ this.getSecondsInQueue()+"\n");
    }


}