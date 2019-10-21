package duke.logic.commands.results;

import duke.model.RouteList;
import duke.model.TaskList;
import duke.model.events.Task;
import duke.model.locations.Route;
import duke.model.locations.RouteNode;

public class CommandResultText extends CommandResult {
    /**
     * Constructs a basic CommandResultImage object.
     *
     * @param message Message for ui to display.
     */
    public CommandResultText(String message) {
        this.message = message;
    }

    /**
     * Alternative constructor that helps to create text for a list of tasks.
     */
    public CommandResultText(TaskList tasks) {
        message = "Here are the list of tasks:\n";
        int i = 1;
        for (Task t : tasks) {
            message += (i + ". " + t + "\n");
            i += 1;
        }
    }

    /**
     * Alternative constructor that helps to create text for a list of Routes.
     */
    public CommandResultText(RouteList routes) {
        message = "Here is the information of Routes:\n";
        for (Route route: routes.getRoutes()) {
            message += route.getDescription() + "\n";
        }
    }

    /**
     * Alternative constructor that helps to create text for a Route.
     */
    public CommandResultText(Route route) {
        message = "Here is the information of the Route:\n" + route.getName() + "\n";
        for (RouteNode node: route.getNodes()) {
            message += node.getAddress() + " " + node.getCoordinate() + "\n";
        }
    }

    /**
     * Alternative constructor that helps to create text for a Route Node.
     */
    public CommandResultText(RouteNode node) {
        message = "Here is the information of the Route node:\n" + node.getAddress() + node.getCoordinate()
                + "\n" + node.getDescription() + "\n(" + node.getType().toString() + ")\n";
    }
}
