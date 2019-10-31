package duke.logic.parsers.commandparser;

import duke.commons.exceptions.ParseException;
import duke.logic.commands.Command;
import duke.logic.commands.MarkDoneCommand;
import duke.logic.parsers.ParserUtil;

/**
 * Parses the user inputs into suitable format for DoneCommand.
 */
public class DoneParser extends CommandParser {

    private int index;

    /**
     * Parses user input into index.
     * @param input The User input
     */
    public DoneParser(String input) throws ParseException {
        index = ParserUtil.getIntegerIndexInList(0, 1, input);
    }

    /**
     * Constructs DoneCommand object.
     * @return DoneCommand object
     */
    public Command parse() {
        return new MarkDoneCommand(index);
    }
}
