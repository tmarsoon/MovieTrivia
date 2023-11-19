import java.util.ArrayList;


import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	
		
		}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	
	// TODO add additional methods as specified in the instructions PDF
	/**
	 * this method is for Inserting an actor and their movies into the list of actors.
	 * @param actor is the name of the actor.
	 * @param movies will be an array of movies that the actor was in.
	 * @param actorsInfo is the list of all actors and their movie information.
	 */
	public void insertActor (String actor, String [] movies, ArrayList <Actor> actorsInfo) {
	//The first thing to do is to convert all actor inputs to lowercase and remove whitespace.
	//This will take care of all input that have different casing and leading/trailing whitespace.
	String formattedActor = actor.toLowerCase().trim();
	Actor newActor = null;
	//now I need to first see if the actor exist
	for (Actor existingActor : actorsInfo) {
	//ignore if the actor exist but still append the movies. I'm going to use an equal method here. 
		 
		     //if a new actor exists, get the name and make sure its lower case/whitespace is fixed   
			 if (existingActor.getName().equals(formattedActor)) {
		            //if the existing actor is in the list, make it equal to the newActor and break.
		        	newActor = existingActor;
		            break;
		        }
		    }

		    // If the actor doesn't exist, create a new actor
		 	//newActor is going to be true
		    if (newActor == null) {
		        newActor = new Actor(formattedActor);
		        actorsInfo.add(newActor);
		    }

		    // Add movies to the actor's movie list, avoiding duplicates
		    for (String movie : movies) {
		        String formattedMovie = movie.toLowerCase().trim();
		        if (!newActor.getMoviesCast().contains(formattedMovie)) {
		            newActor.getMoviesCast().add(formattedMovie);
		        }
		    }
		}
			
	/**
	 * this method is for inserting/updating the ratings for the movies. 
	 * @param movie is the name of the movie to insert/update
	 * @param ratings is an array containing the critic and audience rating (0-100).
	 * @param moviesInfo is for the list of all movies and their rating information.
	 */
	public void insertRating(String movie, int[] ratings, ArrayList<Movie> moviesInfo) {
	    // Convert movie name to lowercase and remove leading/trailing whitespace
	    movie = movie.toLowerCase().trim();
	    //iterating through the list of movies
	    for (Movie existingMovie : moviesInfo) {
	    	//checking if the movie already exists
	        if (existingMovie.getName().equalsIgnoreCase(movie)) {
	            // Movie already exists, update its ratings
	            if (ratings.length == 2 && ratings[0] >= 0 && ratings[0] <= 100 && ratings[1] >= 0 && ratings[1] <= 100) {
	                existingMovie.setCriticRating(ratings[0]);
	                existingMovie.setAudienceRating(ratings[1]);
	            }
	            return; // Movie updated, exit the method
	        }
	    }

	    // Movie doesn't exist, create a new movie and add it to moviesInfo
	    if (ratings.length == 2 && ratings[0] >= 0 && ratings[0] <= 100 && ratings[1] >= 0 && ratings[1] <= 100) {
	        Movie newMovie = new Movie(movie, ratings[0], ratings[1]);
	        moviesInfo.add(newMovie);
	    }
	}
	/**
	 * this method is for selecting the movies that a specified actor has been in. 
	 * @param actor is just the name of the actor.
	 * @param actorsInfo is for listing all actors and their movie information.
	 * @return An ArrayList containing the names of movies in which the actor has acted.
	 */
	public ArrayList<String> selectWhereActorIs(String actor, ArrayList<Actor> actorsInfo) {
	    ArrayList<String> moviesActedIn = new ArrayList<>();
	    // Convert actor name to lowercase and remove leading/trailing whitespace
	    actor = actor.toLowerCase().trim();
	    //iterating through the list of actors
	    for (Actor existingActor : actorsInfo) {
	        //checking if the name matches
	    	if (existingActor.getName().equalsIgnoreCase(actor)) {
	            //if it does, add it and exit the loop
	    		moviesActedIn.addAll(existingActor.getMoviesCast());
	            break; // Actor found, no need to check further
	        }
	    }
	    //returning an ArrayList
	    return moviesActedIn;
	}
	/**
	 * selecting the actors who have appeared in a specified movie.
	 * @param movie is the name of the movie we are filter by.
	 * @param actorsInfo is listing of all actors and their movie information
	 * @return an ArrayList containing the names of actors who appeared in the specified movie.
	 */
	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList<Actor> actorsInfo) {
		//Initializing an ArrayList and filitering through the names of actors in the specified movie
		ArrayList<String> actorsInMovie = new ArrayList<>();
	    // Convert movie name to lowercase and remove leading/trailing whitespace
	    movie = movie.toLowerCase().trim();
	    for (Actor actor : actorsInfo) {
	        //if the name is casted in the movie, i'll add it
	    	if (actor.getMoviesCast().contains(movie)) {
	            actorsInMovie.add(actor.getName());
	        }
	    }
	    //returning an ArrayList
	    return actorsInMovie;
	}
	
	/**
	 *
	 * @param comparison is used for rating comparison with all comparison operators. 
	 * @param targetRating is just for comparing ratings. 
	 * @param isCritic in this code is used for considering if its a critic ratings or audience ratings.
	 * Please note, in my code below it is assumed the "isCritic" is set to true. 
	 * @param moviesInfo is a filtering tactic I use to simply just filter or shuffle through the movies. 
	 * @return selectedmovies is simply an ArrayList that will be returned.
	 */
	public ArrayList<String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList<Movie> moviesInfo) {
	    ArrayList<String> selectedMovies = new ArrayList<>();

	    for (Movie movie : moviesInfo) {
	        int rating = isCritic ? movie.getCriticRating() : movie.getAudienceRating();
	        //Im going to use case's that will serve as each operand for the comparison varible.
	        //If the if statement is called, it will break out of the case and return the selectedmovies ArrayList
	        switch (comparison) {
	            case '=':
	                if (rating == targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	            case '>':
	                if (rating > targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	            case '<':
	                if (rating < targetRating) {
	                    selectedMovies.add(movie.getName());
	                }
	                break;
	            default:
	                // no comparison is used so it's simply an empty list. 
	                return new ArrayList<>();
	        }
	    }
	    //ArrayList 
	    return selectedMovies;
	}
	/**
	 * In this method we are trying to find the co-actors
	 * by analyzing the movies they were in together.
	 * @param actor name of the actor for which the co-actors are together
	 * @param actorsInfo is listing all actors and their movies
	 * @return returning an ArrayList containing the names of co-actors.
	 */
	public ArrayList<String> getCoActors(String actor, ArrayList<Actor> actorsInfo) {
		//Initializing an ArrayList to store the names of co-actors
		ArrayList<String> coActors = new ArrayList<>();
		 //whitespace (trailing) is fixed and I convert everything to lower case
		String formattedActor = actor.trim().toLowerCase();
		//iterating through the list of actors
	    for (Actor currentActor : actorsInfo) {
	    	//checking if the current actor is different than the same as the input actor
	    	if (!currentActor.getName().trim().toLowerCase().equals(formattedActor)) {
	    		//getting the list of movies that the current actor has worked in and iterating through it
	    		ArrayList<String> currentActorMovies = currentActor.getMoviesCast();
	            for (String movie : currentActorMovies) {
	                for (Actor a : actorsInfo) {
	                	 //checking if the actor has the same name as the input actor and was in the same movie
	                	if (a.getName().equalsIgnoreCase(actor) && a.getMoviesCast().contains(movie)) {
	                       //if so ill add it
	                		coActors.add(currentActor.getName());
	                        break; // No need to continue checking other movies for this actor
	                    }
	                }
	            }
	        }
	    }
	    //returning the ArrayList
	    return coActors;
	}
	/**
	
	 * @param actor is the name of the actor for whom co-actors are to be found.
	 * @param actorsInfo is the list of all actors and their movie information.
	 * @return is just used as an ArrayList containing the names of co-actors.
	 */
	public ArrayList<String> getCommonMovie(String actor1, String actor2, ArrayList<Actor> actorsInfo) {
		//Initializing an ArrayList to store the names of co-actors
		ArrayList<String> commonMovies = new ArrayList<>();
	    //whitespace (trailing) is fixed and I convert everything to lower case
		String formattedActor1 = actor1.trim().toLowerCase();
	    //Iterate through the list of actors
	    for (Actor actor : actorsInfo) {
	    	 //Here I am check if the current actor is not the same as the actor that was input
	    	if (actor.getName().trim().toLowerCase().equals(formattedActor1)) {
	            
	    		//Get the list of movies that the current actor was in
	    		ArrayList<String> coActors = getCoActors(actor.getName(), actorsInfo);
	            //now ill iterate through the movies they were in
	            if (coActors.contains(actor2)) {
	            	//checking if co-actor was in it
	                for (String movie : actor.getMoviesCast()) {
	                    // Check if actor2 worked in the same movie
	                    boolean workedTogether = false;
	                    for (Actor otherActor : actorsInfo) {
	                        if (otherActor.getName().equalsIgnoreCase(actor2) && otherActor.getMoviesCast().contains(movie)) {
	                            workedTogether = true;
	                            //no need to continue checking for other movies so I use a break here 
	                            break;
	                        }
	                    }
	                    if (workedTogether) {
	                        commonMovies.add(movie);
	                    }
	                }
	            }
	        }
	    }
	    //returning an ArrayList of coActor names
	    return commonMovies;
	   
	 
	}
	/**
	 * Here we are finding the list of goodmovies with the given critic and audience ratings.
	 * @param moviesInfo is what im using again to filter through the movies
	 * @return returns an ArrayList containing the movie names. 
	 */
	public ArrayList<String> goodMovies(ArrayList<Movie> moviesInfo) {
		 //Initializing an ArrayList to store the names of goodMovies
		ArrayList<String> goodMovies = new ArrayList<>();
		//iterating through list of movies
	    for (Movie movie : moviesInfo) {
	        //seeing if both critic and audience ratings are high than 85.
	    	if (movie.getCriticRating() >= 85 && movie.getAudienceRating() >= 85) {
	            //if so, this will qualify as a good movie
	    		goodMovies.add(movie.getName());
	        }
	    }
	    //return the array list
	    return goodMovies;
	}
	/**
	 * here we are finding common actors who have appeared in both specified movies.
	 * @param movie1 first movie to compare.
	 * @param movie2 second movie to compare.
	 * @param actorsInfo is the list of all actors and their movies.
	 * @return returns an ArrayList of common actors.
	 */
	public ArrayList<String> getCommonActors(String movie1, String movie2, ArrayList<Actor> actorsInfo) {
		//Initializing an ArrayList to store the names of common actors
		ArrayList<String> commonActors = new ArrayList<>();
		//formatting the movie names to lowercase and then trimming the whitespace
		String formattedMovie1 = movie1.trim().toLowerCase();
	    String formattedMovie2 = movie2.trim().toLowerCase();
	    //iterating through the list of actors
	    for (Actor actor : actorsInfo) {
	        //getting the list of movies an actor has been in
	    	ArrayList<String> moviesCast = actor.getMoviesCast();
	    	//checking if actor is present in both specified movies
	        if (moviesCast.contains(formattedMovie1) && moviesCast.contains(formattedMovie2)) {
	            //if so, add their name
	        	commonActors.add(actor.getName());
	        }
	    }
	    //returning the ArrayList
	    return commonActors;
	}
	
	public static double[] getMean(ArrayList<Movie> moviesInfo) {
	    double[] meanRatings = new double[2];
	    double totalCriticRating = 0;
	    double totalAudienceRating = 0;

	    for (Movie movie : moviesInfo) {
	        totalCriticRating += movie.getCriticRating();
	        totalAudienceRating += movie.getAudienceRating();
	    }

	    meanRatings[0] = totalCriticRating / moviesInfo.size();
	    meanRatings[1] = totalAudienceRating / moviesInfo.size();

	    return meanRatings;
	}
}
