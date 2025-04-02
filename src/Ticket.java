import java.io.FileWriter;
import java.io.IOException;

public class Ticket{
    private char row;
    private int seat;
    public double price;
    private Person person;

    public Ticket(char row, int seat, double price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }
    public char getRow() {
        return row;
    }

    public void setRow(char row){
        this.row = row;
    }

    public int getSeat() {
        return seat;
    }

    public void setSeat(int seat){
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price){
        this.price = price;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person){
        this.person = person;
    }

    public void printInfo(){
        System.out.println("\t\tTicket Information");
        System.out.println("\tRow:" + row);
        System.out.println("\tSeat: "+ seat);
        System.out.println("\tPrice: £" + price);
        System.out.println("\t\tPassenger Information");
        person.printInfo();
    }

    public void save(){
        String fileName = getRow()+ "" + getSeat() + ".txt";
        try{
            FileWriter file = new FileWriter(fileName);
            file.write("Ticket Information for the Seat");
            file.write("-------------------------------");
            file.write("\tRow: " + getRow() + "\n");
            file.write("\tSeat: " + getSeat() + "\n");
            file.write("\tPrice: £" + getRow() + "\n");
            file.write("\t\t Person Information\t\t");
            file.write("\tFirst Name : " + person.getName() + "\n");
            file.write("\tSurname : " + person.getSurname() + "\n");
            file.write("\tEmail : " + person.getEmail() + "\n");

            file.close();
            System.out.println("Ticket information saved to file: " + fileName);

        } catch (IOException e){
            System.out.println("Error occured while saving the file!!!");
        }
    }
}