package voyatrip.ui;

/**
 * This class store all the user interface messages that will be displayed to the user.
 */
public class Message {
    // user message
    static final String WELCOME_MESSAGE = "Welcome to VoyaTrip! How can I help you today?";
    static final String GOODBYE_MESSAGE = "Goodbye! Wish you have a lovely trip!";
    static final String ADD_TRIP_MESSAGE = "The following trip has been added to your list! ";
    static final String DELETE_TRIP_MESSAGE = "The following trip has been deleted from your list! ";
    static final String ADD_ACCOMMODATION_MESSAGE = "The following accommodation has been added to your list! ";
    static final String DELETE_ACCOMMODATION_MESSAGE = "The following accommodation has been deleted from your list! ";
    static final String ADD_TRANSPORTATION_MESSAGE = "The following transportation has been added to your list! ";
    static final String DELETE_TRANSPORTATION_MESSAGE = "The following transportation has been deleted from" +
            " your list! ";
    static final String MODIFY_ACCOMMODATION_MESSAGE = "The following accommodation has been modified to: ";
    static final String ADD_ACTIVITY_MESSAGE = "The following activity has been added to your list! ";
    static final String NEXT_COMMAND_MESSAGE = "What would you like to do next? ";
    static final String EMPTY_TRIP_LIST_MESSAGE = "There is no trip in your list. Please add a trip first.";

    // Trip Modification
    static final String MODIFY_TRIP_MESSAGE = "The following trip has been modified to: ";
    static final String BUDGET_PER_DAY_MESSAGE = "The budget for each day is: ";
    static final String EXCEED_TOTAL_BUDGET_MESSAGE = "Please checkout your total budget."
            + "It seems like you have exceeded your total budget.";
    static final String TOTAL_BUDGET_AGAINST_BUDGET_SUM_MESSAGE
            = "The current budget sum percentage against the total budget is: ";
    static final String INVALID_MODIFICATION_DATES_MESSAGE = "Invalid modification dates. Please try again.";

    // exception
    static final String TRIP_NOT_FOUND_MESSAGE = "Trip not found. Please try again.";
    static final String INVALID_COMMAND_MESSAGE = "Invalid command. Please try again.";
    static final String INDEX_OUT_OF_BOUNDS_MESSAGE = "Invalid index. Please try again.";

    // Getters
    public static String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    public static String getGoodbyeMessage() {
        return GOODBYE_MESSAGE;
    }

    public static String getInvalidCommandMessage() {
        return INVALID_COMMAND_MESSAGE;
    }
}
