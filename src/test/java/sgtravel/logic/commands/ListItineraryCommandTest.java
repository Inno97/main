package sgtravel.logic.commands;

import sgtravel.ModelStub;
import sgtravel.commons.exceptions.SingaporeTravelException;
import sgtravel.logic.commands.results.CommandResultText;
import sgtravel.model.planning.Itinerary;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListItineraryCommandTest {

    @Test
    void execute() throws SingaporeTravelException, FileNotFoundException {
        ModelStub model = new ModelStub();
        LocalDateTime startDate = LocalDateTime.of(2020, 9, 9, 9, 9);
        LocalDateTime endDate = LocalDateTime.of(2020, 9, 13, 9, 9);

        String [] itineraryDetails = {"itinerary ", startDate.toString(), endDate.toString()};

        RecommendationsCommand recommendationsCommand = new RecommendationsCommand(itineraryDetails);

        recommendationsCommand.execute(model);

        AddSampleItineraryCommand addSampleItineraryCommand = new AddSampleItineraryCommand("New List");

        addSampleItineraryCommand.execute(model);

        ListItineraryCommand listItineraryCommand = new ListItineraryCommand();

        CommandResultText commandResultText = listItineraryCommand.execute(model);

        String result = commandResultText.getMessage();

        StringBuilder stringBuilder = new StringBuilder();

        HashMap<String, Itinerary> itineraryHashMap = model.getItineraryTable();
        int i = 1;
        for (String name : itineraryHashMap.keySet()) {
            stringBuilder.append(i++).append(". ").append(name).append("\n");
        }

        assertEquals(result, "Your Saved Itineraries are :" + "\n" + stringBuilder.toString());

    }

}
