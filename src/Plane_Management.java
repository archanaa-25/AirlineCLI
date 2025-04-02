import java.util.InputMismatchException;
import java.util.Scanner;

public class Plane_Management{
    public static void options() {
        System.out.println("""
                ************************************************
                *                 MENU OPTIONS                 *
                ************************************************
                \t 1)Buy a seat
                \t 2)Cancel a seat
                \t 3)Find first seat available
                \t 4)Show seating plan
                \t 5)Print tickets information and total sales
                \t 6)Search tickets
                \t 0)Quit
                        
                ************************************************
                """);
    }
    public static int menu() {
        Scanner scanner = new Scanner(System.in);
        int menu_selection = -1; // initializing with an invalid value, loop will continue until a valid input.

        while(menu_selection < 0 || menu_selection > 6 ){
            try{
                System.out.print("\n Select your option: ");
                menu_selection = scanner.nextInt();
                if(menu_selection < 0 || menu_selection > 6 ){
                    System.out.println("Invalid option. Please enter a number between 0-6 according to your choice.");
                }
            } catch (InputMismatchException e){
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next();
            }
        }
        return menu_selection;
    }

    public static int[][] seat = {//2D array to creat each rows and its seats
            new int[14],  //Row A - 14 seats
            new int[12],  //Row B - 12 seats
            new int[12],  //Row C - 12 seats
            new int[14]   //Row D - 14 seats
    };

    public static Ticket[] tickets = new Ticket[52];

    public static double[] prices ={200,150,180};

    public static void main(String[] args) {

        System.out.println("Welcome to the Plane Management application");
        options();

        boolean finish = false;
        do {
            int menu_selection = menu();
            switch (menu_selection) {
                case 0:
                    finish = true;
                    System.out.println("Thank you for choosing us, Contact our team for more information!");
                    break;
                case 1:
                    buy_seat();
                    break;
                case 2:
                    cancel_seat();
                    break;
                case 3:
                    find_first_available();
                    break;
                case 4:
                    show_seating_plan();
                    break;
                case 5:
                    print_tickets_info();
                    break;
                case 6:
                    search_ticket();
                    break;

            }
        } while (!finish);
    }

    private static void buy_seat() {
        Scanner scanner = new Scanner(System.in);
        char row;
        while(true) {
            System.out.print("Enter your row letter from A-D: ");
            row = scanner.next().toUpperCase().charAt(0);

            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row.Please enter a row letter between A-D.");
            } else{
                break; //Exits the method if the row is valid
            }
        }
        int rowletter = row - 'A';
        /*Subtracting 'A' which means, subtracting A's ascii value from user input character's ascii value to get its index value in the array*/
        System.out.print("Enter the seat number: ");
        int seatNum;
        try {
            seatNum = scanner.nextInt();
            //check if seat number is out of range for the given row
            if (seatNum < 1 || seatNum > seat[rowletter].length + 1) {
                throw new IllegalArgumentException("Seat number is out of range for the given row");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid seat number.");
            return; // Exit the method
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return; //Exit the method
        }

        if (seat[rowletter][seatNum - 1] == 0) {
            seat[rowletter][seatNum - 1] = 1;

            System.out.print("Enter your name: ");
            String name = scanner.next();

            System.out.print("Enter your surname: ");
            String surname = scanner.next();

            System.out.print("Enter your email address: ");
            String email = scanner.next();

            Person person = new Person(name, surname, email);

            double price;

            if (seatNum < 6){
                price = prices[0];
            } else if(seatNum > 5 && seatNum < 10){
                price = prices[1];
            } else{
                price = prices[2];
            }

            Ticket ticket = new Ticket(row, seatNum, price, person);

            for(int i = 0; i < tickets.length; i++){
                if(tickets[i] == null){
                    tickets[i] = ticket;
                    break;
                }
            }

            System.out.println("Your seat has been booked successfully.Thank You!");
            ticket.printInfo();
            ticket.save();

        } else {
            System.out.println("Sorry,this seat is already booked. Please select another seat. Thank You! ");
        }
    }

    private static void cancel_seat() {
        Scanner scanner = new Scanner(System.in);
        char row;
        while(true) {
            System.out.print("Enter your row letter from A-D: ");
            row = scanner.next().toUpperCase().charAt(0);

            if (row < 'A' || row > 'D') {
                System.out.println("Invalid row.Please enter a row letter between A and D.");
            } else{
                break; //Exits the method if the row is valid
            }
        }
        int rowletter = row - 'A';
        /*Substracting 'A' which means, substracting A's ascii value from user input character's ascii value to get its index value in the array*/
        System.out.print("Enter the seat number to be canceled: ");
        int seatNum;
        try{
            seatNum = scanner.nextInt();
            if (seatNum < 1 || seatNum > seat[rowletter].length + 1){
                throw new IllegalArgumentException("Seat number is out of range for the given row");
            }
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
            return; //Exit the method
        }

        if (seat[rowletter][seatNum - 1] == 1) {
            seat[rowletter][seatNum - 1] = 0;

            for(int i = 0; i<tickets.length; i++){
                if(tickets[i] != null && tickets[i].getRow() == row && tickets[i].getSeat() == seatNum){
                    tickets[i] = null;
                    break;
                }
            }

            System.out.println("Your seat is cancelled now.Thank You!");
        } else {
            System.out.println("Sorry,this seat is not booked. Please select your correct seat number to cancel. Thank You! ");
        }
    }

    private static void find_first_available() {
        for(int row = 0; row < seat.length; row++){
            for (int seatNum = 0; seatNum < seat[row].length; seatNum++){
                if(seat[row][seatNum] == 0){
                    char firstAvailable = (char)('A' + row);
                    System.out.println("First available seat found: Row" + firstAvailable + ", Seat " + (seatNum+1));
                    return;
                }
            }
        }
        System.out.println("Sorry, there's no available seats.");
    }

    private static void show_seating_plan(){
        for(int i = 0; i < seat.length; i++){
            System.out.println();
            for(int j = 0; j<seat[i].length; j++){
                if (seat[i][j] == 0){
                    System.out.print("O");
                } else{
                    System.out.print("X");
                }
            }
            System.out.println();
        }
    }

    private static void print_tickets_info(){
        int totalPrice = 0;

        System.out.println("The total number of tickets sold out is: " );
        for(Ticket ticket : tickets){
            if(ticket != null){
                ticket.printInfo();
                totalPrice += ticket.price;
            } ;

        }
        System.out.println("Total amount of sold tickets: £" + totalPrice);
    }

    private static void search_ticket(){
        Scanner scanner = new Scanner(System.in);
        char userR;
        int userS;

        System.out.print("Enter the row letter of the seat: ");
        userR = scanner.next().toUpperCase().charAt(0);

        int rowletter = userR - 'A';

        if(userR < 'A' || userR > 'D'){
            System.out.println("Invalid row.Please enter a row letter between A-D.");
            return;
        }
        try {
            System.out.print("Enter the seat number: ");
            userS = scanner.nextInt();
            if (userS < 1 || userS > seat[rowletter].length + 1){
                throw new IllegalArgumentException("Seat number is out of range for the given row");
            }
        } catch(InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid seat number.");
            return; // Exit the method
        } catch(IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return; //Exit the method
        }

        for(Ticket ticket : tickets){
            if(ticket != null && ticket.getRow() == userR && ticket.getSeat() == userS ){
                ticket.printInfo();
                break; //Exits the loop once a matching ticket is found
            } else{
                System.out.println("This seat is available.");
                break;
            }
        }

    }

}


