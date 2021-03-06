package duke.commands;

import duke.notes.Note;
import duke.notes.event.Event;
import duke.notes.event.Birthday;
import duke.notes.event.Wedding;
import duke.notes.task.Bill;
import duke.notes.task.Deadline;
import duke.notes.task.Shoplist;
import duke.notes.task.Task;
import duke.parser.DateException;
import duke.parser.DateParser;
import duke.storage.DukeList;
import duke.storage.DukeStorage;
import duke.ui.DukeUI;
import java.util.ArrayList;
import java.util.Date;
import java.text.ParseException;

/**
 * An extension of the {@code DukeCommand} object that evaluates user input to create new {@code Note} objects.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
public class NewNoteCommand extends DukeCommand {

    //VARIABLES-----------------------------------------
    private NoteType noteType;
    private ArrayList<String> inputs;

    //CONSTRUCTORS--------------------------------------
    /**
     * This method constructs a {@code NewNoteCommand} object.
     *
     * @param noteType The type of {@code Note} being created.
     * @param inputs The list of user input elements to be used to create new {@code Note} objects.
     */
    public NewNoteCommand(String noteType, ArrayList<String> inputs) throws CommandException {
        super("NEWNOTE");
        this.noteType = NoteType.getKey(noteType);
        this.inputs = inputs;
    }

    /**
     * This method initialises a {@code NewNoteCommand} object.
     */
    @SuppressWarnings("unused")
    public NewNoteCommand() { super(); }

    //METHODS-------------------------------------------
    /**
     * This method checks for clashes between new and existing {@code Event} objects, as well as the current date-time.
     *
     * @param notes The {@code ArrayList} object that holds the notes managed by {@code Duke}.
     * @param start The {@code Date} object indicating the start date of the new {@code Event} object.
     * @param end The {@code Date} object indicating the end date of the new {@code Event} object.
     * @exception DateException If there are errors in the formats or substance of {@code Date} objects.
     */
    static void checkForClashes(ArrayList<Note> notes, Date start, Date end)
            throws DateException {

        Date now = new Date();
        if(start.before(now)){
            throw new DateException(start, "StartB4Now");
        }
        if(end.before(start)){
            throw new DateException(end, "EndB4Start");
        }

        for(Note note: notes){
            if(!(note instanceof Event)) {
                continue;
            }
            Date noteStart = ((Event) note).getStartDate();
            Date noteEnd = ((Event) note).getEndDate();
            if((start.after(noteStart) || start.equals(noteStart)) &&
                    (start.before(noteEnd) || start.equals(noteEnd))) {
                throw new DateException(start, "EventsClash", (Event) note);
            }
            if((end.after(noteStart) || end.equals(noteStart)) &&
                    (end.before(noteEnd) || end.equals(noteEnd))) {
                throw new DateException(end, "EventsClash", (Event) note);
            }
            if((start.before(noteStart) || start.equals(noteStart)) &&
                (end.after(noteEnd) || end.equals(noteEnd))) {
                throw new DateException(end, "EventsClash", (Event) note);
            }
        }
    }

    /**
     * This method checks for the validity of dollar amounts used in the {@code Budget} class of objects.
     *
     * @param amount The dollar amount to be checked for validity.
     * @exception CommandException If the dollar amount is less than or equals to zero.
     */
    static void checkValidAmount(double amount) throws CommandException {
        if(amount <= 0) {
            throw new CommandException("The dollar amount specified must be more than zero.");
        }
    }

    /**
     * This method checks for the validity of {@code Date} objects used in the {@code Task} class of objects.
     *
     * @param description The description to be checked for validity.
     * @exception CommandException If the description is blank or empty.
     */
    static void checkValidDescription(String description) throws CommandException {

        if(description.isBlank() || description.isEmpty()) {
            throw new CommandException("The description provided cannot be blank or empty.");
        }
    }

    /**
     * This method checks for the validity of {@code Date} objects used in the {@code Task} class of objects.
     *
     * @param date The {@code Date} object to be checked for validity.
     * @exception DateException If the {@code Date} object is before the present date-time.
     */
    static void checkValidTargetDate(Date date) throws DateException {

        Date now = new Date();
        if(date.before(now)) {
            throw new DateException(date, "TargetDate");
        }
    }

    /**
     * This method executes the function of the {@code NewNoteCommand} object.
     *
     * @param dukeNotes The {@code DukeList} object that holds the notes managed by {@code Duke}.
     * @param dukeStorage The {@code DukeStorage} object that holds access to the saved files of {@code Duke}.
     * @exception CommandException If there are errors in the command input.
     * @exception ParseException If there are errors reading previously saved files.
     */
    public void execute(DukeList dukeNotes, DukeStorage dukeStorage)
            throws CommandException, ParseException, DateException {


        Date addDate = new Date();
        int nextSerialNum = dukeNotes.getNotes().size() + 1;
        ArrayList<Note> notes = new ArrayList<>();
        switch (noteType.toString()) {
        case "BILL" -> {
            String description = inputs.get(1);
            Date targetDate = DateParser.understandDateInput(inputs.get(2));
            double itemBudget = Double.parseDouble(inputs.get(3));

            checkValidDescription(description);
            checkValidTargetDate(targetDate);
            checkValidAmount(itemBudget);

            Note note1 = new Bill(nextSerialNum, description, targetDate, itemBudget, addDate);
            notes.add(note1);
        }
        case "BIRTHDAY" -> {
            String description = inputs.get(1);
            String giftDescription = "Birthday gift for " + description;
            Date startDate = DateParser.understandDateInput(inputs.get(2));
            Date endDate = DateParser.understandDateInput(inputs.get(3));
            double itemBudget = Double.parseDouble(inputs.get(4));

            checkValidDescription(description);
            checkForClashes(dukeNotes.getNotes(), startDate, endDate);
            checkValidAmount(itemBudget);

            Note note1 = new Shoplist(nextSerialNum, giftDescription, itemBudget, addDate);
            notes.add(note1);
            Note note2 = new Birthday(nextSerialNum+1, description, startDate, endDate, addDate);
            notes.add(note2);
        }
        case "DEADLINE" -> {
            String description = inputs.get(1);
            Date targetDate = DateParser.understandDateInput(inputs.get(2));

            checkValidDescription(description);
            checkValidTargetDate(targetDate);

            Note note1 = new Deadline(nextSerialNum, description, targetDate, addDate);
            notes.add(note1);
        }
        case "EVENT" -> {
            String description = inputs.get(1);
            Date startDate = DateParser.understandDateInput(inputs.get(2));
            Date endDate = DateParser.understandDateInput(inputs.get(3));

            checkValidDescription(description);
            checkForClashes(dukeNotes.getNotes(), startDate, endDate);

            Note note1 = new Event(nextSerialNum, description, startDate, endDate, addDate);
            notes.add(note1);
        }
        case "SHOPLIST" -> {
            String description = inputs.get(1);
            double itemBudget = Double.parseDouble(inputs.get(2));

            checkValidDescription(description);
            checkValidAmount(itemBudget);

            Note note1 = new Shoplist(nextSerialNum, description, itemBudget, addDate);
            notes.add(note1);
        }
        case "TASK" -> {
            String description = inputs.get(1);

            checkValidDescription(description);

            Note note1 = new Task(nextSerialNum, description, addDate);
            notes.add(note1);
        }
        case "WEDDING" -> {
            String description = inputs.get(1);
            Date startDate = DateParser.understandDateInput(inputs.get(2));
            Date endDate = DateParser.understandDateInput(inputs.get(3));
            double itemBudget = Double.parseDouble(inputs.get(4));

            checkValidDescription(description);
            checkForClashes(dukeNotes.getNotes(), startDate, endDate);
            checkValidAmount(itemBudget);

            Note note1 = new Wedding(nextSerialNum, description, startDate, endDate, itemBudget, addDate);
            notes.add(note1);
        }
        }
        DukeUI.printDivider();
        for(int i=0; i < notes.size(); i++) {
            DukeUI.addConfirm(notes.get(i).getObjectClass());
            dukeNotes.getNotes().add(notes.get(i));
            dukeNotes.getNotes().get(nextSerialNum + i - 1).printList();
        }
        DukeUI.printOutstanding();
        DukeUI.autoSaveConfirmation(new SaveCommand().autoSave(dukeNotes, dukeStorage));
        DukeUI.suggestListNotes();
        DukeUI.printDivider();
    }
}
