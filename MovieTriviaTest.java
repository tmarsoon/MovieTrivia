import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;
import movies.Movie;

class MovieTriviaTest {

	// instance of movie trivia object to test
	MovieTrivia mt;
	// instance of movieDB object
	MovieDB movieDB;

	@BeforeEach
	void setUp() throws Exception {
		// initialize movie trivia object
		mt = new MovieTrivia();

		// set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");

		// get instance of movieDB object from movie trivia object
		movieDB = mt.movieDB;
	}

	@Test
	void testSetUp() {
		assertEquals(6, movieDB.getActorsInfo().size(),
				"actorsInfo should contain 6 actors after reading moviedata.txt.");
		assertEquals(7, movieDB.getMoviesInfo().size(),
				"moviesInfo should contain 7 movies after reading movieratings.csv.");

		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName(),
				"\"meryl streep\" should be the name of the first actor in actorsInfo.");
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size(),
				"The first actor listed in actorsInfo should have 3 movies in their moviesCasted list.");
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0),
				"\"doubt\" should be the name of the first movie in the moviesCasted list of the first actor listed in actorsInfo.");

		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName(),
				"\"doubt\" should be the name of the first movie in moviesInfo.");
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating(),
				"The critics rating for the first movie in moviesInfo is incorrect.");
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating(),
				"The audience rating for the first movie in moviesInfo is incorrect.");
	}

	@Test
	void testInsertActor() {

		// try to insert new actor with new movies
		mt.insertActor("test1", new String[] { "testmovie1", "testmovie2" }, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"After inserting an actor, the size of actorsInfo should have increased by 1.");
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName(),
				"After inserting actor \"test1\", the name of the last actor in actorsInfo should be \"test1\".");
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
				"Actor \"test1\" should have 2 movies in their moviesCasted list.");
		assertEquals("testmovie1",
				movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0),
				"\"testmovie1\" should be the first movie in test1's moviesCasted list.");

		// try to insert existing actor with new movies
		mt.insertActor("   Meryl STReep      ", new String[] { "   DOUBT      ", "     Something New     " },
				movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size(),
				"Since \"meryl streep\" is already in actorsInfo, inserting \"   Meryl STReep      \" again should not increase the size of actorsInfo.");

		// look up and inspect movies for existing actor
		// note, this requires the use of properly implemented selectWhereActorIs method
		// you can comment out these two lines until you have a selectWhereActorIs
		// method
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
				"After inserting Meryl Streep again with 2 movies, only one of which is not on the list yet, the number of movies \"meryl streep\" appeared in should be 4.");
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"),
				"After inserting Meryl Streep again with a new Movie \"     Something New     \", \"something new\" should appear as one of the movies she has appeared in.");
		
		// TODO add additional test case scenarios
		//typical case
		//going off of the first test, I insert a second test that takes in an additional movie and increases the actor by 1. 
		mt.insertActor("test2", new String[] { "testmovie1", "testmovie2", "testmovie3" }, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size(),
				"After inserting an actor, the size of actorsInfo should have increased by 1.");
		//typical case
		assertEquals("test2", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName(),
				"After inserting actor \"test1\", the name of the last actor in actorsInfo should be \"test1\".");
		//typical case
		assertEquals(3, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size(),
				"Actor \"test1\" should have 3 movies in their moviesCasted list.");
		//typical case
		assertEquals("testmovie3",movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(2),
				"\"testmovie3\" should be the first movie in test1's moviesCasted list.");
		
		//edge case
		mt.insertActor("test2", new String[] { "TEstMovie5", "TeStMoViE6"}, movieDB.getActorsInfo());
		assertNotEquals(9, movieDB.getActorsInfo().size(),				
				"Since \"test2\" is already in actorsInfo, inserting \"test2\" again should not increase the size of actorsInfo.");
		assertEquals("testmovie3", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(2));
	
	}


	@Test
	void testInsertRating() {

		// try to insert new ratings for new movie
		mt.insertRating("testmovie", new int[] { 79, 80 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"After inserting ratings for a movie that is not in moviesInfo yet, the size of moviesInfo should increase by 1.");
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName(),
				"After inserting a rating for \"testmovie\", the name of the last movie in moviessInfo should be \"testmovie\".");
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating(),
				"The critics rating for \"testmovie\" is incorrect.");
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating(),
				"The audience rating for \"testmovie\" is incorrect.");
		

		// try to insert new ratings for existing movie
		mt.insertRating("doubt", new int[] { 100, 100 }, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size(),
				"Since \"doubt\" is already in moviesInfo, inserting ratings for it should not increase the size of moviesInfo.");

		// look up and inspect movies based on newly inserted ratings
		// note, this requires the use of properly implemented selectWhereRatingIs
		// method
		// you can comment out these two lines until you have a selectWhereRatingIs
		// method
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size(),
				"After inserting a critic rating of 100 for \"doubt\", there should be 1 movie in moviesInfo with a critic rating greater than 99.");
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"),
				"After inserting the rating for \"doubt\", \"doubt\" should appear as a movie with critic rating greater than 99.");

		// TODO add additional test case scenarios
		
		/**
		 * This edge case takes in a movie called "errormovie". The movie got a high audience rating and a lower critic rating. 
		 * For this reason, the edge case below, checks if the critic rating is a "good movie" above 85 considering the audience
		 * rating is so high. That is not the case. So I adjust the audience rating to make it lower. 
		 */
		 	//typical case
			mt.insertRating("errormovie", new int[] { 70, 95 }, movieDB.getMoviesInfo());
		    assertEquals(9, movieDB.getMoviesInfo().size(),
		    		"After inserting ratings for a movie that is not in moviesInfo yet, the size of moviesInfo should increase by 1.");
		    assertEquals("errormovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName(),
		    		"After inserting a rating for \"errormovie\", the name of the last movie in moviessInfo should be \"errormovie\".");
		    assertEquals(70, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating(),
		            "The critic rating for the movie is correct.");
		    
		    //edge case
		    //we expect the critic rating to be above 85 considering it had such a high audience rating, but thats not the case. 
		    assertFalse(mt.selectWhereRatingIs('>', 85, true, movieDB.getMoviesInfo()).contains("errormovie"),
					"After inserting the rating for \"errormovie\", \"errormovie\" should appear as a movie with critic/audience rating less than 85.");
		    //we assume that the data gathered for the audience was inaccurate and adjusted it to 84. 
		    mt.insertRating("errormovie", new int[] { 70, 84 }, movieDB.getMoviesInfo());
		    assertEquals(84, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating(),
		            "The audience rating should be adjusted to be below 85.");
		}
		
	

	@Test
	void testSelectWhereActorIs() {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size(),
				"The number of movies \"meryl streep\" has appeared in should be 3.");
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0),
				"\"doubt\" should show up as first in the list of movies \"meryl streep\" has appeared in.");

		// TODO add additional test case scenarios
		// Typical Case
		/**
		 * @param actorNotFound is a variable referencing an object that calls the selectWhereActorIs method with its parameters
		 * that takes in Andrew Garfield to see how many movies in the .txt file he has acted in. Since he isn't in the list,
		 * it should be 0.
		 * @param 
		 * 
		 */
		//typical case
		//Amy Adams has been in 4 movies and "Arrival" is the 4th movie in the list so get(3) is the index.  
		assertEquals(4, mt.selectWhereActorIs("Amy Adams", movieDB.getActorsInfo()).size(),
				"The number of movies \"Amy Adams\" has appeared in should be 4.");
		assertEquals("arrival", mt.selectWhereActorIs("amy adams", movieDB.getActorsInfo()).get(3),
				"\"arrival\" should show up as first in the list of movies \"amy adams\" has appeared in.");
		
        // edge case
        //Andrew Garfield is an actor who played in the Amazing Spider-Man, Hacksaw Ridge, etc.
        ArrayList<String> actorNotFound = mt.selectWhereActorIs("Andrew Garfield", movieDB.getActorsInfo());
        assertNotEquals(1, actorNotFound.size(),
                "The method should return an empty ArrayList because Andrew Garfield is not found in the list.");
        assertFalse(actorNotFound.contains("Andrew Garfield"),
        		 "The method should not contain \"Andrew Garfield\" in the list since he is not found in the database.");
        
        //edge case
        //white space and camelcase test for Merly Streep
        ArrayList<String> camelAndSpace = mt.selectWhereActorIs("            mEryL sTReeP      ", movieDB.getActorsInfo());
        assertNotEquals(4, camelAndSpace.size(),
                "The number of movies \"Meryl Streep\" has appeared in is 3, not 4.");
        assertEquals("sophie's choice", camelAndSpace.get(1),
                "The method should correctly match \"mEryL sTReeP\" with \"Meryl Streep\" and return the expected movie at the index.");
        	
    }

	

	@Test
	void testSelectWhereMovieIs() {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size(),
				"There should be 2 actors in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"),
				"\"meryl streep\" should be an actor who appeared in \"doubt\".");
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" should be an actor who appeared in \"doubt\".");

		// TODO add additional test case scenarios
		//typical case
		/**
		 * 
		 */
		assertNotEquals(3, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).size(),
				"There should be 2 actors in \"the post\", not 3.");
		assertNotEquals(true, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" should not be an actor who appeared in \"the post\".");
		assertNotEquals(true, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("brad pitt"),
				"\"brad pitt\" should not be an actor who appeared in \"the post\".");
		
		//Edge case
		/**
		 * @param result is checking if the output,
		 * is empty when the movie name has leading and trailing space/s IN BETWEEN the title. I'm expecting the result to be empty because 
		 * the selectWhereMovieIs method trims the movie name FIRST from the leading trailing ends, not in between.
		 * So essentially, the idea here is that no matches will occur because of the spacing in between. 
		 */
		ArrayList<String> result = mt.selectWhereMovieIs(" dou        bt ", movieDB.getActorsInfo());
	    //expecting results to be false. 
		assertFalse(result.contains("meryl streep"), "\"meryl streep\" should not be found with leading and trailing spaces.");
	    assertFalse(result.contains("amy adams"), "\"amy adams\" should not be found with leading and trailing spaces.");
	    
	    //edge case
	    /**
		 * @param result2 does the exact opposite of "result" above. When there is leading and trailing white space/s
		 *  You expect that there should be 2 actors in "Doubt" (even with spaces) 
		 * because the selectWhereMovieIs method trims the input movie name, and "Doubt" (with or without spaces) should match the movie names in the actor's list. 
		 * Therefore, the result list should contain "Meryl Streep" and "Amy Adams," and its size should be 2, which is why these assertions are true.
		 */
	    ArrayList<String> result2 = mt.selectWhereMovieIs("   doubt      ", movieDB.getActorsInfo());
	    assertEquals(2, result2.size(), "There should be 2 actors in \"doubt\".");
	    assertTrue(result2.contains("meryl streep"), "\"meryl Streep\" should be an actor who appeared in \"doubt\".");
	    assertTrue(result2.contains("amy adams"), "\"amy adams\" should be an actor who appeared in \"doubt\".");
	}

	@Test
	void testSelectWhereRatingIs() {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size(),
				"There should be 6 movies where critics rating is greater than 0.");
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size(),
				"There should be no movie where audience rating is equal to 65.");
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size(),
				"There should be 2 movies where critics rating is less than 30.");

		// TODO add additional test case scenarios
		/**
		 * @param additionalmovies is used to create my own database to test against the method. 
		 * here I create movies that have there own ratings. 
		*/
		ArrayList<Movie> additionalmovies = new ArrayList<>();
		additionalmovies.add(new Movie("Movie1", 10, 50));
		additionalmovies.add(new Movie("Movie2", 20, 65));
		additionalmovies.add(new Movie("Movie3", 0, 70));
		additionalmovies.add(new Movie("Movie4", 40, 30));

	    /**
	     * @param addmovies is used to test against the movies that had been added and see
	     * if the critic ratings is above 9. If so, how many movies and which ones are in that list. 
	     */
	    //edge case
	    ArrayList<String> addMovies = mt.selectWhereRatingIs('>', 9, true, additionalmovies);
	    // Assert the size and presence of movies
	    assertEquals(3, addMovies.size(), "There should now be 3 movies with critics rating greater than 9.");
	    assertTrue(addMovies.contains("Movie1"), "\"Movie1\" should be in the list.");
	    assertTrue(addMovies.contains("Movie4"), "\"Movie4\" should be in the list.");
	    assertTrue(addMovies.contains("Movie2"), "\"Movie2\" should be in the list.");
	    assertFalse(addMovies.contains("Movie3"), "\"Movie3\" should not be in the list.");
	    /**
	     * @param addmovies2 is used to test against the movies that had been added and see
	     * if the critic ratings is less than 11. If so, how many movies and which ones are in that list. 
	     */
	    
	    //edge case
	    ArrayList<String> addMovies2 = mt.selectWhereRatingIs('<', 11, true, additionalmovies);
	    // Assert the size and presence of movies
	    assertEquals(2, addMovies2.size(), "There should be 2 movies with critics rating less than 11.");
	    assertTrue(addMovies2.contains("Movie1"), "\"Movie1\" should be in the list.");
	    assertFalse(addMovies2.contains("Movie4"), "\"Movie4\" should not be in the list.");
	    assertFalse(addMovies2.contains("Movie2"), "\"Movie2\" should not be in the list.");
	    assertTrue(addMovies2.contains("Movie3"), "\"Movie3\" should be in the list.");
	    
	    /**
	     * @param addmovies3 is used to test against the movies that had been added and see
	     * if the critic ratings is equal to 9. We expect 0 movies to be equal to this value.  
	     */
	    //edge case
	    ArrayList<String> addMovies3 = mt.selectWhereRatingIs('=', 9, true, additionalmovies);
	    // Assert the size and presence of movies
	    assertNotEquals(2, addMovies3.size(), "There should now be 3 movies with critics rating greater than 9.");
	    assertFalse(addMovies3.contains("Movie1"), "\"Movie1\" should not be in the list.");
	    assertFalse(addMovies3.contains("Movie4"), "\"Movie4\" should not be in the list.");
	    assertFalse(addMovies3.contains("Movie2"), "\"Movie2\" should not be in the list.");
	    assertFalse(addMovies3.contains("Movie3"), "\"Movie3\" should not be in the list.");
	}
	    
	
	

	@Test
	void testGetCoActors() {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size(),
				"\"meryl streep\" should have 2 co-actors.");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"),
				"\"tom hanks\" was a co-actor of \"meryl streep\".");
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"),
				"\"amy adams\" was a co-actor of \"meryl streep\".");

		// TODO add additional test case scenarios
		//typical case
		//this is for Tom Hank's co-actors
	    assertEquals(1, mt.getCoActors("tom hanks", movieDB.getActorsInfo()).size(),
	            "\"tom hanks\" should have 2 co-actors.");
	    assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("meryl streep"),
	            "\"meryl streep\" was a co-actor of \"tom hanks\".");
	    assertFalse(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("andrew garfield"),
	            "\"andrew garfield\" wasn't a co-actor of \"tom hanks\".");
	    assertFalse(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("amy adams"),
	            "\"amy adams\" wasn't a co-actor of \"tom hanks\".");
	    //edge case
	    //using my favorite actor (andrew garfield) testing if he was co-actors with any from the list
	    assertNotEquals(4, mt.getCoActors("andrew garfield", movieDB.getActorsInfo()).size(),
	            "\"andrew garfield\" shouldn't have 4 co-actors.");
	    assertFalse(mt.getCoActors("andrew garfield", movieDB.getActorsInfo()).contains("brad pitt"),
	            "\"brad pitt\" wasn't a co-actor of \"andrew garfield\".");
	    assertFalse(mt.getCoActors("andrew garfield", movieDB.getActorsInfo()).contains("tom hanks"),
	            "\"tom hanks\" wasn't a co-actor of \"andrew garfield\".");
	    assertFalse(mt.getCoActors("andrew garfield", movieDB.getActorsInfo()).contains("amy adams"),
	            "\"amy adams\" wasn't a co-actor of \"andrew garfield\".");
		//edge case 
	    //this is for is an actor isn't in the list, they cannot have a co-cator obviously. 
		assertEquals(0, mt.getCoActors("actorNotExisting", movieDB.getActorsInfo()).size(),
	            "\"actorNotExisting\" should not have any co-actors because they themselves aren't in the list.");
	}
	

	@Test
	void testGetCommonMovie() {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size(),
				"\"tom hanks\" and \"meryl streep\" should have 1 movie in common.");
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"),
				"\"the post\" should be a common movie between \"tom hanks\" and \"meryl streep\".");

		// TODO add additional test case scenarios
		 //typical case
		//testing if maryl streep and amy adams share a movie
		/**
		 * @param result1 is used for testing to see if meryl streep and amy adams share a movie
		 * @param result2 is an edge case where we don't expect 5 common movies and the two actors weren't
		 * in the post together. 
		 * @param result3 I used to see actors that didn't play in a common movie. 
		 */
	    ArrayList<String> result1 = mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo());
	    assertEquals(1, result1.size(), "No common movies should be found between \"meryl streep\" and \"amy adams\".");

	    // edge case
	    //testing if actors are in multiple common movies
	    ArrayList<String> result2 = mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo());
	    assertNotEquals(5, result2.size(), "There should be 1 common movies between \"meryl streep\" and \"amy adams\".");
	    assertTrue(result2.contains("doubt"), "\"doubt\" should be in the list.");
	    assertFalse(result2.contains("the post"), "\"the post\" should be in the list.");

	    //typical case
	    //seeing if an actor isn't in a common movie
	    ArrayList<String> result3 = mt.getCommonMovie("brad pitt", "robin williams", movieDB.getActorsInfo());
	    assertEquals(0, result3.size(), "No common movies should be found between \"brad pitt\" and \"robin williams\".");
	}
	 
		

	@Test
	void testGoodMovies() {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size(),
				"There should be 3 movies that are considered good movies, movies with both critics and audience rating that are greater than or equal to 85.");
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"),
				"\"jaws\" should be considered a good movie, since it's critics and audience ratings are both greater than or equal to 85.");

		// TODO add additional test case scenarios
		 //typical case
		 assertNotEquals(0, mt.goodMovies(movieDB.getMoviesInfo()).size(),
		            "There should be some movies that are considered good movies.");
		    
		    assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("popeye"),
		            "\"popeye\" should not be considered a good movie, as it does not meet the criteria.");
		//edge cases
		    /**
		     * In these edge cases I decided to create three tests that will test different scenarios.
		     * @param zeroGoodMovies is a variable holding reference to an object where each movie is less then 85, so it is considered to be bad. 
		     * @param allGoodMoovies is a variable holding reference to an object where each movie is more than 85, so it is considered to be good. 
		     * @param bothMovies is a variable holding reference to an object where the movies can be either good or bad.
		     * 
		     * reference: Java Concepts, Early Objects (by Cay Hortsmann) - I used this because I wanted to add movies that had different ratings. 
		     * I wanted to create a data type of an array called "Movie". The textbook told me (since technically Movie isn't a data type of it's own) that
		     * by hovering over the word, I can actually import a package that will allow this to run. 
		     * In addition, I used this as reference as well, https://docs.oracle.com/javase/tutorial/java/package/usepkgs.html.
		     */
			
		    //only bad movies
		    ArrayList<Movie> zeroGoodMovies = new ArrayList<>();
		    zeroGoodMovies.add(new Movie("Movie1", 80, 70));
		    zeroGoodMovies.add(new Movie("Movie2", 75, 80));
		    zeroGoodMovies.add(new Movie("Movie3", 70, 75));
		    assertEquals(0, mt.goodMovies(zeroGoodMovies).size(), "There should be no good movies.");

		    // only good movies
		    ArrayList<Movie> allGoodMovies = new ArrayList<>();
		    allGoodMovies.add(new Movie("Movie4", 90, 92));
		    allGoodMovies.add(new Movie("Movie5", 88, 89));
		    allGoodMovies.add(new Movie("Movie6", 85, 85));
		    assertEquals(3, mt.goodMovies(allGoodMovies).size(), "All movies should be considered good.");
		    assertTrue(mt.goodMovies(allGoodMovies).contains("Movie4"), "Movie4 should be considered a good movie.");

		    //both good and bad movies
		    ArrayList<Movie> bothMovies = new ArrayList<>();
		    /*here, this test should only produce 1 good movie. The reason being is because for a movie to 
		     *be considered "good", both critic and audience ratings must be above 85.
		     */
		    bothMovies.add(new Movie("Movie7", 81, 90));
		    bothMovies.add(new Movie("Movie8", 98, 82)); 
		    bothMovies.add(new Movie("Movie9", 86, 99)); 
		    assertEquals(1, mt.goodMovies(bothMovies).size(), "There should be one good movie.");
		    assertTrue(mt.goodMovies(bothMovies).contains("Movie9"), "Movie9 should be considered a good movie.");

		    
		
	}

	@Test
	void testGetCommonActors() {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size(),
				"There should be one actor that appeared in both \"doubt\" and \"the post\".");
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"),
				"The actor that appeared in both \"doubt\" and \"the post\" should be \"meryl streep\".");

		// TODO add additional test case scenarios
		// typical case 
		//testing the movies with no common actors
		assertEquals(0, mt.getCommonActors("popeye", "arrival", movieDB.getActorsInfo()).size(),
	            "There should be no common actors between \"popeye\" and \"arrival.\"");
		// typical case
		//testing to see if the same actor played in both "doubt" and "the post"
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size(),
		    mt.getCommonActors("doubt", "arrival", movieDB.getActorsInfo()).size(),
		    "The number of common actors between \"doubt\" and \"the post\" should not be equal to the number of common actors between \"doubt\" and \"arrival.\"");
		assertFalse(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size() < mt.getCommonActors("doubt", "arrival", movieDB.getActorsInfo()).size(),
			    "There should not be fewer common actors between \"doubt\" and \"the post\" than between \"doubt\" and \"arrival.\"");
	    // edge case
		//using a movie that doesn't exist in the list. For this reason we expect 0. 
	    assertEquals(0, mt.getCommonActors("doubt", "non-existent-movie", movieDB.getActorsInfo()).size(),
	            "\"non-existent-movie\" does not exist in the movie database, so there should be no common actors.");

	}

	@Test
	void testGetMean() {
		/**
		 * Testing the 'getMean' method by adding ratings and
		 * calculating mean ratings before and after adding a new rating using hypothetical cases.
		 */
		// TODO add ALL test case scenarios!
		ArrayList<Movie> moviesInfo = new ArrayList<>();
	    moviesInfo.add(new Movie("test", 95, 89));
	    moviesInfo.add(new Movie("test2", 61, 49));
	    //typical test1
	    // Calculate the mean ratings before adding a new rating
	    double[] resultBefore = MovieTrivia.getMean(moviesInfo);
	    // Add a new movie with ratings
	    moviesInfo.add(new Movie("test3", 75, 60));
	    // Calculate the mean ratings again
	    double[] resultAfter = MovieTrivia.getMean(moviesInfo);

	    //this below is the expected critic mean before adding again
	    assertEquals(78.0, resultBefore[0], 0.001); 
	    //this is the audience mean before adding again.
	    assertEquals(69.0, resultBefore[1], 0.001); 
	    // this is the expected critic mean after adding
	    assertEquals(77.0, resultAfter[0], 0.001); 
	    //this is the expected audience mean after adding
	    assertEquals(66.0, resultAfter[1], 0.001); 
	    
	    
	    //typical test2
	    ArrayList<Movie> moviesInfo1 = new ArrayList<>();
	    moviesInfo1.add(new Movie("test4", 85, 72));
	    moviesInfo1.add(new Movie("test5", 70, 60));

	    double[] resultBefore1 = MovieTrivia.getMean(moviesInfo1);
	    // Add a new movie with ratings
	    moviesInfo1.add(new Movie("test6", 90, 78));

	    double[] resultAfter1 = MovieTrivia.getMean(moviesInfo1);
	    //this below is the expected critic mean before adding again
	    assertEquals(77.5, resultBefore1[0], 0.001);
	    //this is the audience mean before adding again.
	    assertEquals(66.0, resultBefore1[1], 0.001); 
	    // this is the expected critic mean after adding
	    assertEquals(81.6667, resultAfter1[0], 0.001); 
	    //this is the expected audience mean after adding
	    assertEquals(70.0, resultAfter1[1], 0.001); 
	    
	    
	    
	    //typical test3
	    ArrayList<Movie> moviesInfo2 = new ArrayList<>();
	    moviesInfo2.add(new Movie("test7", 60, 50));

	    double[] resultBefore2 = MovieTrivia.getMean(moviesInfo2);

	    // Add a new movie with ratings
	    moviesInfo2.add(new Movie("test8", 70, 65));
	    double[] resultAfter2 = MovieTrivia.getMean(moviesInfo2);
	    //this below is the expected critic mean before adding again
	    assertEquals(60.0, resultBefore2[0], 0.001);
	    //this is the audience mean before adding again.
	    assertEquals(50.0, resultBefore2[1], 0.001); 
	    // this is the expected critic mean after adding
	    assertEquals(65.0, resultAfter2[0], 0.001);
	    //this is the expected audience mean after adding
	    assertEquals(57.5, resultAfter2[1], 0.001); 
	    
	    
	    //edge case4
	    ArrayList<Movie> moviesInfo3 = new ArrayList<>();
	    moviesInfo3.add(new Movie("test9", 70, 60));
	    moviesInfo3.add(new Movie("test10", 80, 75));
	    moviesInfo3.add(new Movie("test11", 90, 85));
	    
	    double[] resultBefore3 = MovieTrivia.getMean(moviesInfo3);

	    // Add new movies with ratings
	    moviesInfo3.add(new Movie("test12", 75, 70));
	    moviesInfo3.add(new Movie("test13", 85, 80));
	    moviesInfo3.add(new Movie("test14", 95, 90));

	    double[] resultAfter3 = MovieTrivia.getMean(moviesInfo3);
	    //this below is the expected critic mean before adding again
	    assertEquals(80.0, resultBefore3[0], 0.001); 
	    //this is the audience mean before adding again.
	    assertEquals(73.3333, resultBefore3[1], 0.001);
	    // this is the expected critic mean after adding
	    assertEquals(82.5, resultAfter3[0], 0.001); 
	    //this is the expected audience mean after adding
	    assertEquals(76.66666666666667, resultAfter3[1], 0.001); 
	}
	   
  

	}
	

