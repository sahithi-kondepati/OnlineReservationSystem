import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

class User {
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public boolean checkPassword(String password) {
        return this.password.equals(password);
    }
}

class Reservation {
    private String pnr;
    private String passengerName;
    private int trainNumber;
    private String classType;
    private String journeyDate;
    private String startStation;
    private String endStation;

    public Reservation(String passengerName, int trainNumber, String classType, String journeyDate, String startStation, String endStation) {
        this.pnr = UUID.randomUUID().toString().substring(0, 8);
        this.passengerName = passengerName;
        this.trainNumber = trainNumber;
        this.classType = classType;
        this.journeyDate = journeyDate;
        this.startStation = startStation;
        this.endStation = endStation;
    }

    public String getPnr() {
        return pnr;
    }

    @Override
    public String toString() {
        return "PNR: " + pnr + "\nPassenger Name: " + passengerName + "\nTrain Number: " + trainNumber + "\nClass: " + classType + "\nJourney Date: " + journeyDate + "\nStart Station: " + startStation + "\nEnd Station: " + endStation;
    }
}

public class OnlineReservation {
    private static ArrayList<User> userList = new ArrayList<>();
    private static ArrayList<Reservation> reservationList = new ArrayList<>();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        initializeUsers();
        System.out.println("==== Welcome to Online Reservation System ====");
        if (login()) {
            boolean exit = false;
            while (!exit) {
                System.out.println("\nMain Menu:");
                System.out.println("1. Make a reservation");
                System.out.println("2. Cancel a reservation");
                System.out.println("3. View reservations");
                System.out.println("4. Exit");
                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1:
                        makeReservation();
                        break;
                    case 2:
                        cancelReservation();
                        break;
                    case 3:
                        viewReservation();
                        break;
                    case 4:
                        System.out.println("Thank you for coming.");
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid Choice. Please try again.");
                }
            }
        } else {
            System.out.println("Login failed. Exiting the system.");
        }
    }

    private static void initializeUsers() {
        userList.add(new User("admin", "1234"));
        userList.add(new User("user", "5678"));
    }

    private static boolean login() {
        System.out.print("Enter Username: ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();
        for (User user : userList) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) {
                System.out.println("Login Successful.");
                return true;
            }
        }
        System.out.println("Invalid username or password.");
        return false;
    }

    private static void makeReservation() {
        System.out.print("Enter passenger name: ");
        String name = sc.nextLine();
        System.out.print("Enter train number: ");
        int trainNumber = sc.nextInt();
        sc.nextLine();
        
        switch (trainNumber) {
            case 17250:
                System.out.println("Tirupati Express");
                break;
            case 11272:
                System.out.println("Vindhyachal Express");
                break;
            case 12625:
                System.out.println("Kerala Express");
                break;
            default:
                System.out.println("Generic Train");
        }

        System.out.print("Enter the Class (AC/Sleeper): ");
        String classType = sc.nextLine();
        System.out.print("Enter Journey Date (DD/MM/YYYY): ");
        String journeyDate = sc.nextLine();
        System.out.print("Enter Start Station: ");
        String startStation = sc.nextLine();
        System.out.print("Enter End Station: ");
        String endStation = sc.nextLine();

        Reservation reservation = new Reservation(name, trainNumber, classType, journeyDate, startStation, endStation);
        reservationList.add(reservation);
        System.out.println("Reservation Successful.");
        System.out.println("Your PNR: " + reservation.getPnr());
    }

    private static void cancelReservation() {
        System.out.print("Enter PNR to cancel: ");
        String pnr = sc.nextLine();
        Reservation reservationToCancel = null;
        
        for (Reservation reservation : reservationList) {
            if (reservation.getPnr().equals(pnr)) {
                reservationToCancel = reservation;
                break;
            }
        }

        if (reservationToCancel != null) {
            System.out.println("Reservation Details:");
            System.out.println(reservationToCancel);
            System.out.print("Are you sure you want to cancel this reservation? (yes/no): ");
            String confirmation = sc.nextLine();
            if (confirmation.equalsIgnoreCase("yes")) {
                reservationList.remove(reservationToCancel);
                System.out.println("Reservation cancelled successfully.");
            } else {
                System.out.println("Cancellation Aborted.");
            }
        } else {
            System.out.println("PNR not found. Please try again.");
        }
    }

    private static void viewReservation() {
        if (reservationList.isEmpty()) {
            System.out.println("No reservation found.");
        } else {
            System.out.println("All reservations:");
            for (Reservation reservation : reservationList) {
                System.out.println("--------------");
                System.out.println(reservation);
            }
        }
    }
}
