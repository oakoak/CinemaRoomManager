package cinema;

import java.security.PublicKey;
import java.util.Arrays;
import java.util.Scanner;

public class Cinema {
    enum CinemaState {
        NONE,EXIT,BUY,SHOW
    }

    char [][] cinema;
    int rows, seats, purchasedTickets, currentIncome, totalIncome;
    CinemaState state;

    public Cinema (int rows, int seats) {
        this.cinema = new char[rows][seats];
        this.state = CinemaState.NONE;
        this.rows = rows;
        this.seats = seats;
        this.purchasedTickets = 0;
        this.currentIncome = 0;
        this.totalIncome = this.rows * this.seats <= 60 ? this.rows * this.seats * 10 :
                (this.rows / 2 * 10 + (this.rows / 2 + this.rows % 2) * 8) * this.seats;

        for (int i = 0; i < this.cinema.length; i++) {
            for (int j = 0; j < this.cinema[i].length; j++) {
                this.cinema[i][j] = 'S';
            }
        }
    }

    public void PrintCinema () {
        String strCinema = "Cinema:\n ";

        for (int i = 1; i <= this.cinema[0].length; i++)
            strCinema += " " + i;

        strCinema += "\n";

        for (int i = 0; i < this.cinema.length; i++) {
            String strRows = String.valueOf(i + 1);
            for (int j = 0; j < this.cinema[0].length; j++) {
                strRows += " " + this.cinema[i][j];
            }
            strCinema += strRows + "\n";
        }
        System.out.println(strCinema);
    }

    public void buyTicket () {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        int rowNumber = scanner.nextInt();
        System.out.println("Enter a seat number in that row:");
        int seatNumber = scanner.nextInt();
        int cost;

        if (this.rows < rowNumber || this.seats < seatNumber || rowNumber <= 0 || seatNumber <= 0) {
            System.out.println("Wrong input!");
            this.buyTicket();
            return;
        }

        if (this.cinema[rowNumber - 1][seatNumber - 1] == 'B') {
            System.out.println("That ticket has already been purchased");
            this.buyTicket();
            return;
        }


        this.cinema[rowNumber - 1][seatNumber - 1] = 'B';

        if (this.rows * this.seats <= 60 || this.rows / 2 >= rowNumber) {
            cost = 10;
        } else {
            cost = 8;
        }
        this.currentIncome += cost;
        this.purchasedTickets++;

        System.out.println("Ticket price: $" + cost + "\n");
    }

    public void printStatistic () {
        System.out.printf("Number of purchased tickets: %d\nPercentage: %.2f%%\nCurrent income: $%d\nTotal income: $%d\n",
                this.purchasedTickets,(float) this.purchasedTickets / (this.rows * this.seats) * 100
                , this.currentIncome, this.totalIncome);
    }

    public void action (String action) {

        switch (action) {
            case "1":
                this.PrintCinema();
                break;
            case "2":
                this.buyTicket();
                break;
            case "3":
                this.printStatistic();
                break;
            case "0":
                this.state = CinemaState.EXIT;
                break;
        }
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of rows:");
        int rows = scanner.nextInt();
        System.out.println("Enter the number of seats in each row:");
        int seats = scanner.nextInt();

        Cinema cinema = new Cinema(rows, seats);

        while (cinema.state != CinemaState.EXIT) {
            System.out.println("1. Show the seats\n2. Buy a ticket\n3. Statistics\n0. Exit");
            cinema.action(scanner.next());
        }
   }
}