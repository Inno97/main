package sgtravel.ui.calendar;

import sgtravel.commons.exceptions.DuplicateEventException;
import sgtravel.logic.commands.results.CommandResultCalender;
import sgtravel.model.lists.EventList;
import sgtravel.model.Event;
import sgtravel.ui.UiPart;

import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents a calender window in the UI.
 */
public class CalendarWindow extends UiPart<Stage> {
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private static final String FXML = "CalendarWindow.fxml";
    private YearMonth currentYearMonth;
    private int numOfDays;
    private int dayOfTheFirstDay;
    private String currentMonth;
    private List<EventList> filteredEvents;
    private static EventList events;
    private static final int MAX_ROW = 6;
    private static final int MAX_COL = 7;

    @FXML
    private VBox pane;
    @FXML
    private Text calendarTitle;
    @FXML
    private GridPane gridCalendar;

    /**
     * Creates a new CalendarWindow.
     *
     * @param root Stage to use as the root of the CalendarWindow.
     * @param events The EventList for the calender.
     */
    private CalendarWindow(Stage root, EventList events) {
        super(FXML, root);
        root.getScene().getStylesheets().addAll(this.getClass().getResource("/css/calendarStyle.css").toExternalForm());
        setCalendarLayout(events);
    }

    /**
     * Creates a new CalendarWindow.
     */
    public CalendarWindow(CommandResultCalender commandResult) {
        this(new Stage(), commandResult.getEvents());
    }

    /**
     * Shows the Calendar window.
     */
    public void show() {
        getRoot().show();
    }

    /**
     * Changes to the previous month.
     */
    @FXML
    private void previousMonth() {
        currentYearMonth = currentYearMonth.minusMonths(1);
        refreshCalendar();
    }

    /**
     * Changes to the next month.
     */
    @FXML
    private void nextMonth() {
        currentYearMonth = currentYearMonth.plusMonths(1);
        refreshCalendar();
    }

    /**
     * Refreshes the calendar.
     */
    private void refreshCalendar() {
        updateVariables();
        setCalendarTitle(currentYearMonth.getYear(), currentMonth);
        fillCalendarDays();
    }

    /**
     * Sets the title of the calendar according to a specific month and year.
     *
     * @param year The year.
     * @param month The month.
     */
    private void setCalendarTitle(int year, String month) {
        calendarTitle.setText(" " + month + " " + year + " ");
        pane.setId(currentMonth);
    }

    /**
     * Fills up the calendar with the necessary information.
     */
    private void fillCalendarDays() {
        gridCalendar.getChildren().clear();
        boolean flag = false;
        int day = 1;
        for (int i = 0; i < MAX_ROW && day <= numOfDays; i++) {
            for (int j = 0; j < MAX_COL && day <= numOfDays; j++) {
                if (dayOfTheFirstDay == j) {
                    flag = true;
                }
                if (flag) {
                    gridCalendar.add(CalendarCard.getCalendarCard(day, filteredEvents.get(day)), j, i);
                    ++day;
                }
            }
        }
    }

    /**
     * Updates the relevant variables to contain information of the particular month.
     */
    private void updateVariables() {
        setCalendarTitle(currentYearMonth.getYear(), currentMonth);
        currentMonth = currentYearMonth.getMonth().toString();
        numOfDays = currentYearMonth.lengthOfMonth();
        findRelevantTasks();
        dayOfTheFirstDay = currentYearMonth.atDay(1).getDayOfWeek().getValue() % 7;
    }

    /**
     * Finds the tasks that needs to be displayed.
     */
    private void findRelevantTasks() {
        filteredEvents = new ArrayList<>();
        for (int i = 0; i <= numOfDays; ++i) {
            filteredEvents.add(new EventList());
        }
        events.sort();
        for (Event event : events) {
            tryAddingTask(event);
        }
    }

    /**
     * Tries to add a task to the current calendar.
     *
     * @param task A task from the Duke's task list.
     */
    private void tryAddingTask(Event task) {
        LocalDate startDate = task.getStartDate().toLocalDate();
        LocalDate endDate = task.getEndDate().toLocalDate();
        while (!startDate.isAfter(endDate)) {
            if (isSameYearMonth(startDate)) {
                try {
                    filteredEvents.get(startDate.getDayOfMonth()).add(task);
                } catch (DuplicateEventException e) {
                    logger.log(Level.WARNING, "Duplicated tasks should not exists.");
                }
            }
            startDate = startDate.plusDays(1);
        }
    }

    /**
     * Focuses on the Calendar window.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    /**
     * Sets the calendar layout.
     *
     * @param events The EventList from SGTravel containing all the events.
     */
    private void setCalendarLayout(EventList events) {
        setCalendarBasics(events);
        refreshCalendar();
    }

    /**
     * Sets the basic information of the calendar; current month, year and tasks.
     */
    private void setCalendarBasics(EventList events) {
        ZoneId zoneId = ZoneId.systemDefault(); //GMT +8
        currentYearMonth = YearMonth.now(zoneId).minusMonths(0);
        CalendarWindow.events = events;
    }

    /**
     * Returns true if the Calendar window is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Checks if it is the same year and month.
     *
     * @param date The date object.
     * @return true If it is the same year and month.
     */
    private boolean isSameYearMonth(Object date) {
        return currentYearMonth.getYear() == ((LocalDate) date).getYear()
                && currentYearMonth.getMonth() == ((LocalDate) date).getMonth();
    }
}
