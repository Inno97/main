package duke.logic.conversations;

import duke.commons.MessagesPrompt;

public class FindConversation extends Conversation {
    private static final String command = "find";
    private String keyword;

    public FindConversation() {
        super();
        prompt = MessagesPrompt.FIND_PROMPT_STARTER;
    }

    @Override
    public void execute(String input) {
        keyword = input;
        buildResult();
    }

    @Override
    protected void buildResult() {
        if (keyword != null) {
            result = command + " " + keyword;
            setFinished(true);
        } else {
            attempts++;
        }
    }
}