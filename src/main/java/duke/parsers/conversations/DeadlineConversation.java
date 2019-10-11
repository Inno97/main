package duke.parsers.conversations;

import duke.commons.MessagesPrompt;
import duke.ui.Ui;

public class DeadlineConversation extends Conversation {
    private String command = "deadline";
    private String description;
    private String date;

    @Override
    public void execute(String input, Ui ui) {
        switch (state) {
        case 0:
            prompt = MessagesPrompt.DEADLINE_PROMPT_STARTER;
            state++;
            break;
        case 1:
            prompt = MessagesPrompt.DEADLINE_PROMPT_DATE;
            description = input;
            state++;
            break;
        case 2:
            prompt = MessagesPrompt.DEADLINE_PROMPT_SUCCESS;
            date = input;

            buildResult();
            isDone = true;
            break;
        default:
            prompt = MessagesPrompt.PROMPT_ERROR;
            break;
        }
        if (attempts > 4) {
            prompt = MessagesPrompt.PROMPT_TOO_MANY_ATTEMPTS;
            isDone = true;
            isCancelled = true;
        }
    }

    @Override
    void buildResult() {
        result = command + " & " + description + " & " + date;
    }
}
