package sgtravel.logic.parsers.commandparsers;

import sgtravel.commons.exceptions.ParseException;
import sgtravel.logic.commands.Command;
import sgtravel.logic.commands.RouteEditCommand;
import sgtravel.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for RouteEditCommand.
 */
public class RouteEditParser extends CommandParser {
    private String input;
    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    /**
     * Constructs the RouteEditParser.
     *
     * @param input The user input.
     */
    public RouteEditParser(String input) {
        this.input = input;
    }

    /**
     * Parses the user input and constructs RouteEditCommand object.
     *
     * @return The RouteEditCommand object.
     * @throws ParseException If RouteEditCommand object cannot be created.
     */
    @Override
    public Command parse() throws ParseException {
        int firstIndex = ParserUtil.getIntegerIndexInList(ZERO, THREE, input);
        String firstEventIndex = ParserUtil.getFieldInList(ONE, THREE, input);
        String secondEventIndex = ParserUtil.getFieldInList(TWO, THREE, input);
        return new RouteEditCommand(firstIndex, firstEventIndex, secondEventIndex);
    }
}
