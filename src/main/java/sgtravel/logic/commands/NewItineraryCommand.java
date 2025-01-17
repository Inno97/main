package sgtravel.logic.commands;

import sgtravel.commons.Messages;
import sgtravel.commons.exceptions.ApiException;
import sgtravel.commons.exceptions.FileNotSavedException;
import sgtravel.commons.exceptions.ItineraryIncorrectDaysException;
import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.Model;
import sgtravel.model.planning.Itinerary;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Creates a new custom itinerary.
 */
public class NewItineraryCommand extends Command {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private Itinerary itinerary;

    /**
     * Constructs a NewItineraryCommand with the users details.
     * @param itinerary The itinerary to add.
     */
    public NewItineraryCommand(Itinerary itinerary) {
        this.itinerary = itinerary;
    }

    /**
     * Executes this command on the given task list and user interface.
     *
     * @param model The model object containing information about the user.
     * @throws ApiException If the api request fails.
     * @throws ParseException If the information cannot be parsed into an itinerary.
     * @throws FileNotSavedException If the data cannot be saved.
     */
    @Override
    public CommandResultText execute(Model model) throws ApiException, ParseException, FileNotSavedException,
            ItineraryIncorrectDaysException {
        if (itinerary.getList().size() != itinerary.getNumberOfDays()) {
            throw new ItineraryIncorrectDaysException();
        }
        model.setNewItinerary(itinerary);
        logger.log(Level.FINE, "New itinerary is saved");
        model.save();
        return new CommandResultText(Messages.ITINERARY_SUCCESS + itinerary.getName());
    }
}
