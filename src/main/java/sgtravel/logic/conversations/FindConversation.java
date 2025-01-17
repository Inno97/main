package sgtravel.logic.conversations;

import sgtravel.commons.Messages;

/**
 * Handles the conversation occurring when a find command is entered.
 */
public class FindConversation extends Conversation {
    private static final String COMMAND = "find";
    private String keyword;

    /**
     * Initialises the FindConversation object.
     */
    public FindConversation() {
        super();
        prompt = Messages.PROMPT_FIND_STARTER;
    }

    /**
     * Executes Prompt and returns a String reply.
     *
     * @param input The user input.
     */
    @Override
    public void execute(String input) {
        keyword = input;
        buildResult();
        setFinished(true);
    }

    /**
     * Builds the result of the conversation string.
     */
    @Override
    protected void buildResult() {
        assert  (keyword != null);
        result = COMMAND + " " + keyword;
    }
}
