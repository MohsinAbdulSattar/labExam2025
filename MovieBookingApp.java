package moviebookingapp;

class MovieBookingApp {
    private final String movieName; 
    private int totalSeats; 

    public MovieBookingApp(String movieName, int totalSeats) {
        this.movieName = movieName;
        this.totalSeats = totalSeats;
    }
    public synchronized void booking(String user, int seatsToBook) {
        try {
            if (seatsToBook <= totalSeats) {
                System.out.println(user + " successfully booked " + seatsToBook + " seat(s).\n");
                totalSeats -= seatsToBook;
                System.out.println("Remaining seats: " + totalSeats + "\n");
            } else {
                throw new IllegalArgumentException("Not enough seats available for " + user + ". Requested: " + seatsToBook + ", Available: " + totalSeats);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Error:!!!! " + e.getMessage() + "\n");
        }
    }

    public String getMovieName() {
        return movieName;
    }
    public static void main(String[] args) {
        MovieBookingApp bookingApp = new MovieBookingApp("Blockbuster Movie", 20);

        Thread userA = new Thread(() -> bookingApp.booking("User A", 10));
        Thread userB = new Thread(() -> bookingApp.booking("User B", 12));

        System.out.println("Welcome to the movie booking system for " + bookingApp.getMovieName() + "!\n");
        userA.start();
        userB.start();

        try {
            userA.join();
            userB.join();
        } catch (InterruptedException e) {
            System.out.println("Thread interrupted: " + e.getMessage());
        }
        System.out.println("Final remaining seats: " + bookingApp.totalSeats);
    }
}