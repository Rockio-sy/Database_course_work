package org.example.cli;

import org.example.model.Client;
import org.example.model.ClientSubscription;
import org.example.model.Movie;
import org.example.model.Subscription;
import org.example.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Scanner;

@Component
public class MovieTicketCLI implements CommandLineRunner {

    // TODO: Validation.
    @Autowired
    private MovieService movieService; // Assume these services are created

    @Autowired
    private ClientService clientService;

    @Autowired
    private SubscriptionService subscriptionService;


    @Autowired
    private ClientSubscriptionService clientSubscriptionService;

    @Autowired
    private WatchlistService watchlistService;

    @Autowired
    private RatingReviewService ratingReviewService;

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\033[1;32mWelcome to the Movie Ticket Management System!\033[0m");

        while (running) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. Manage Movies");
            System.out.println("2. Manage Clients");
            System.out.println("3. Manage Subscriptions");
            System.out.println("4. Manage Client Subscriptions");
            System.out.println("5. Manage Watchlist");
            System.out.println("6. Manage Ratings and Reviews");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageMovies(scanner);
                    break;
                case 2:
                    manageClients(scanner);
                    break;
                case 3:
                    manageSubscriptions(scanner);
                    break;
                case 4:
                    manageClientSubscriptions(scanner);
                    break;
                case 5:
                    manageWatchlist(scanner);
                    break;
                case 6:
                    manageRatingsAndReviews(scanner);
                    break;
                case 7:
                    running = false;
                    System.out.println("\033[1;31mExiting... Goodbye!\033[0m");
                    break;
                default:
                    System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
            }
        }
        scanner.close();
    }

    private void manageMovies(Scanner scanner) {
        System.out.println("\n--- Manage Movies ---");
        System.out.println("1. Add a Movie");
        System.out.println("2. View All Movies");
        System.out.println("3. Update a Movie");
        System.out.println("4. Delete a Movie");
        System.out.println("5. Get a Movie by ID");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Add a Movie
                System.out.print("Enter movie title: ");
                String title = scanner.nextLine();
                System.out.print("Enter movie genre: ");
                String genre = scanner.nextLine();
                System.out.print("Enter release date (YYYY-MM-DD): ");
                String releaseDate = scanner.nextLine();
                System.out.print("Enter director: ");
                String director = scanner.nextLine();
                System.out.print("Enter rating (0-10): ");
                double rating = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                Movie movie = new Movie();
                movie.setTitle(title);
                movie.setGenre(genre);
                movie.setReleaseDate(LocalDate.parse(releaseDate));
                movie.setDirector(director);
                movie.setRating(rating);

                movieService.addMovie(movie);
                System.out.println("\033[1;32mMovie added successfully!\033[0m");
                break;

            case 2:
                // View All Movies
                System.out.println("\nAll Movies:");
                movieService.getAllMovies().forEach(System.out::println);
                break;

            case 3:
                // Update a Movie
                System.out.print("Enter movie ID to update: ");
                Long movieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new title: ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter new genre: ");
                String newGenre = scanner.nextLine();
                System.out.print("Enter new release date (YYYY-MM-DD): ");
                String newReleaseDate = scanner.nextLine();
                System.out.print("Enter new director: ");
                String newDirector = scanner.nextLine();
                System.out.print("Enter new rating (0-10): ");
                double newRating = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                Movie updatedMovie = new Movie();
                updatedMovie.setId(movieId);
                updatedMovie.setTitle(newTitle);
                updatedMovie.setGenre(newGenre);
                updatedMovie.setReleaseDate(LocalDate.parse(newReleaseDate));
                updatedMovie.setDirector(newDirector);
                updatedMovie.setRating(newRating);

                movieService.updateMovie(updatedMovie);
                System.out.println("\033[1;32mMovie updated successfully!\033[0m");
                break;

            case 4:
                // Delete a Movie
                System.out.print("Enter movie ID to delete: ");
                Long deleteMovieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                movieService.deleteMovie(deleteMovieId);
                System.out.println("\033[1;32mMovie deleted successfully!\033[0m");
                break;

            case 5:
                // Get a Movie by ID
                System.out.print("Enter movie ID: ");
                Long getMovieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                Movie foundMovie = movieService.getMovieById(getMovieId);
                if (foundMovie != null) {
                    System.out.println("\nMovie Details:");
                    System.out.println(foundMovie);
                } else {
                    System.out.println("\033[1;31mMovie not found!\033[0m");
                }
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }

    private void manageClients(Scanner scanner) {
        System.out.println("\n--- Manage Clients ---");
        System.out.println("1. Add a Client");
        System.out.println("2. View All Clients");
        System.out.println("3. Update a Client");
        System.out.println("4. Delete a Client");
        System.out.println("5. Get a Client by ID");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Add a Client
                System.out.print("Enter client name: ");
                String name = scanner.nextLine();
                System.out.print("Enter client email: ");
                String email = scanner.nextLine();
                System.out.print("Enter client phone number: ");
                String phoneNumber = scanner.nextLine();

                Client client = new Client();
                client.setName(name);
                client.setEmail(email);
                client.setPhoneNumber(phoneNumber);

                if (clientService.addClient(client)) {
                    System.out.println("\033[1;32mClient added successfully!\033[0m");
                }else{

                    System.out.println("\033[0;34mUser already exists.\033[0m");
                }
                break;

            case 2:
                // View All Clients
                System.out.println("\nAll Clients:");
                clientService.getAllClients().forEach(System.out::println);
                break;

            case 3:
                // Update a Client
                System.out.print("Enter client ID to update: ");
                Long clientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new email: ");
                String newEmail = scanner.nextLine();
                System.out.print("Enter new phone number: ");
                String newPhoneNumber = scanner.nextLine();

                Client updatedClient = new Client();
                updatedClient.setId(clientId);
                updatedClient.setName(newName);
                updatedClient.setEmail(newEmail);
                updatedClient.setPhoneNumber(newPhoneNumber);

                clientService.updateClient(updatedClient);
                System.out.println("\033[1;32mClient updated successfully!\033[0m");
                break;

            case 4:
                // Delete a Client
                System.out.print("Enter client ID to delete: ");
                Long deleteClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                clientService.deleteClient(deleteClientId);
                System.out.println("\033[1;32mClient deleted successfully!\033[0m");
                break;

            case 5:
                // Get a Client by ID
                System.out.print("Enter client ID: ");
                Long getClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                Client foundClient = clientService.getClientById(getClientId);
                if (foundClient != null) {
                    System.out.println("\nClient Details:");
                    System.out.println(foundClient);
                } else {
                    System.out.println("\033[1;31mClient not found!\033[0m");
                }
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }

    private void manageSubscriptions(Scanner scanner) {
        System.out.println("\n--- Manage Subscriptions ---");
        System.out.println("1. Add a Subscription");
        System.out.println("2. View All Subscriptions");
        System.out.println("3. Update a Subscription");
        System.out.println("4. Delete a Subscription");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Add a Subscription
                System.out.print("Enter subscription name: ");
                String name = scanner.nextLine();
                System.out.print("Enter subscription price: ");
                double price = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter subscription features: ");
                String features = scanner.nextLine();

                Subscription subscription = new Subscription();
                subscription.setName(name);
                subscription.setPrice(price);
                subscription.setFeatures(features);

                subscriptionService.addSubscription(subscription);
                System.out.println("\033[1;32mSubscription added successfully!\033[0m");
                break;

            case 2:
                // View All Subscriptions
                System.out.println("\nAll Subscriptions:");
                subscriptionService.getAllSubscriptions().forEach(System.out::println);
                break;

            case 3:
                // Update a Subscription
                System.out.print("Enter subscription ID to update: ");
                Long subscriptionId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new price: ");
                double newPrice = scanner.nextDouble();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter new features: ");
                String newFeatures = scanner.nextLine();

                Subscription updatedSubscription = new Subscription();
                updatedSubscription.setId(subscriptionId);
                updatedSubscription.setName(newName);
                updatedSubscription.setPrice(newPrice);
                updatedSubscription.setFeatures(newFeatures);

                subscriptionService.updateSubscription(updatedSubscription);
                System.out.println("\033[1;32mSubscription updated successfully!\033[0m");
                break;

            case 4:
                // Delete a Subscription
                System.out.print("Enter subscription ID to delete: ");
                Long deleteSubscriptionId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                subscriptionService.deleteSubscription(deleteSubscriptionId);
                System.out.println("\033[1;32mSubscription deleted successfully!\033[0m");
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }

    private void manageClientSubscriptions(Scanner scanner) {
        System.out.println("\n--- Manage Client Subscriptions ---");
        System.out.println("1. Assign Subscription to Client");
        System.out.println("2. View All Client Subscriptions");
        System.out.println("3. Remove Subscription from Client");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Assign Subscription to Client
                System.out.print("Enter client ID: ");
                Long clientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter subscription ID: ");
                Long subscriptionId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter start date (YYYY-MM-DD): ");
                String startDate = scanner.nextLine();
                System.out.print("Enter end date (YYYY-MM-DD): ");
                String endDate = scanner.nextLine();

                ClientSubscription clientSubscription = new ClientSubscription();
                clientSubscription.setClientId(clientId);
                clientSubscription.setSubscriptionId(subscriptionId);
                clientSubscription.setStartDate(LocalDate.parse(startDate));
                clientSubscription.setEndDate(LocalDate.parse(endDate));

                clientSubscriptionService.assignSubscriptionToClient(clientSubscription);
                System.out.println("\033[1;32mSubscription assigned to client successfully!\033[0m");
                break;

            case 2:
                // View All Client Subscriptions
                System.out.println("\nAll Client Subscriptions:");
                clientSubscriptionService.getAllClientSubscriptions().forEach(System.out::println);
                break;

            case 3:
                // Remove Subscription from Client
                System.out.print("Enter client ID: ");
                Long removeClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter subscription ID: ");
                Long removeSubscriptionId = scanner.nextLong();
                scanner.nextLine(); // Consume newline

                clientSubscriptionService.removeSubscriptionFromClient(removeClientId, removeSubscriptionId);
                System.out.println("\033[1;32mSubscription removed from client successfully!\033[0m");
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }

    private void manageWatchlist(Scanner scanner) {
        System.out.println("\n--- Manage Watchlist ---");
        System.out.println("1. Add a Movie to Watchlist");
        System.out.println("2. View Client's Watchlist");
        System.out.println("3. Remove a Movie from Watchlist");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Add a Movie to Watchlist
                System.out.print("Enter client ID: ");
                Long clientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter movie ID: ");
                Long movieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline

                watchlistService.addMovieToWatchlist(clientId, movieId);
                System.out.println("\033[1;32mMovie added to watchlist successfully!\033[0m");
                break;

            case 2:
                // View Client's Watchlist
                System.out.print("Enter client ID: ");
                Long viewClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.println("\nClient's Watchlist:");
                watchlistService.getWatchlistByClientId(viewClientId).forEach(System.out::println);
                break;

            case 3:
                // Remove a Movie from Watchlist
                System.out.print("Enter client ID: ");
                Long removeClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter movie ID: ");
                Long removeMovieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline

                watchlistService.removeMovieFromWatchlist(removeClientId, removeMovieId);
                System.out.println("\033[1;32mMovie removed from watchlist successfully!\033[0m");
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }

    private void manageRatingsAndReviews(Scanner scanner) {
        System.out.println("\n--- Manage Ratings and Reviews ---");
        System.out.println("1. Add a Rating for a Movie");
        System.out.println("2. Add a Review for a Movie");
        System.out.println("3. View Ratings and Reviews for a Movie");
        System.out.println("4. View Ratings and Reviews for a Client");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                // Add a Rating for a Movie
                System.out.print("Enter client ID: ");
                Long clientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter movie ID: ");
                Long movieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter rating (0-10): ");
                double rating = scanner.nextDouble();
                scanner.nextLine(); // Consume newline

                ratingReviewService.addRating(clientId, movieId, rating);
                System.out.println("\033[1;32mRating added successfully!\033[0m");
                break;

            case 2:
                // Add a Review for a Movie
                System.out.print("Enter client ID: ");
                Long reviewClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter movie ID: ");
                Long reviewMovieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.print("Enter review: ");
                String review = scanner.nextLine();

                ratingReviewService.addReview(reviewClientId, reviewMovieId, review);
                System.out.println("\033[1;32mReview added successfully!\033[0m");
                break;

            case 3:
                // View Ratings and Reviews for a Movie
                System.out.print("Enter movie ID: ");
                Long viewMovieId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.println("\nRatings and Reviews for Movie:");
                ratingReviewService.getRatingsAndReviewsByMovieId(viewMovieId).forEach(System.out::println);
                break;

            case 4:
                // View Ratings and Reviews for a Client
                System.out.print("Enter client ID: ");
                Long viewClientId = scanner.nextLong();
                scanner.nextLine(); // Consume newline
                System.out.println("\nRatings and Reviews by Client:");
                ratingReviewService.getRatingsAndReviewsByClientId(viewClientId).forEach(System.out::println);
                break;

            default:
                System.out.println("\033[1;31mInvalid choice! Please try again.\033[0m");
        }
    }
}