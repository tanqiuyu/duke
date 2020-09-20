package duke.notes;


import duke.budget.Budget;
import duke.commands.CommandException;
import duke.commands.DateException;
import duke.ui.DukeUI;
import java.util.Date;

/**
 * An abstract class that forms the basis of the various types of notes in {@code Duke}
 * that extends the {@code Note} class. The class also provides common methods that would
 * be used across these notes.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
public abstract class Note implements Task {

    //VARIABLES-----------------------------------------
    protected int serialNum;
    protected String description;
    protected Date addDate;
    protected Date doneDate = null;
    protected boolean isDone = false;


    //CONSTRUCTORS--------------------------------------
    /**
     * This method is used to initialise the attributes common to the various type of notes
     * that extends the {@code Note} class.
     *
     * @param serialNum The serial number automatically assigned for identification purposes.
     * @param description The description of the note.
     * @param addDate The date and time the note was added.
     */
    public Note(int serialNum, String description, Date addDate) {
        this.serialNum = serialNum;
        this.description = description;
        this.addDate = addDate;
    }

    /**
     * This method is used to initialise the various type of notes
     * that extends the {@code Note} class.
     */
    public Note() {}


    //SET STATEMENTS------------------------------------
    /**
     * This method is used to mark an outstanding {@code Note} object as completed.
     *
     * @param doneDate The date and time the {@code Note} had concluded.
     * @return boolean True if the operation is successful.
     * @exception CommandException If there are errors in the command input.
     * @exception DateException If there are errors in the formats or substance of {@code Date} objects.
     */
    public boolean markAsDone(Date doneDate) throws CommandException, DateException {
        if(this.isDone) {
            System.out.println("\tNote #" + this.serialNum + " was already done!");
            return false;
        } else {
            this.isDone = true;
            this.doneDate = doneDate;
            System.out.println("\tNoted! I've marked Note #" + this.serialNum + " as done.");
            return true;
        }
    }


    /**
     * This method is used to assign or reassign serial numbers to {@code Note} objects.
     *
     * @param serialNum The serial number automatically assigned for identification purposes.
     */
    public void setSerialNum(int serialNum) {
        this.serialNum = serialNum;
    }

    /**
     * This method is used to set or edit the description of {@code Note} objects.
     *
     * @param description The description of the {@code Note} object.
     */
    public void setDescription(String description) {
        this.description = description;
    }


    //GET STATEMENTS------------------------------------
    /**
     * This method is used to retrieve serial numbers of the {@code Note} object.
     *
     * @return int The serial number automatically assigned for identification purposes.
     */
    public int getSerialNum() {
        return (this.serialNum);
    }

    /**
     * This method is used to retrieve the description of the {@code Note} object.
     *
     * @return String The description of the {@code Note} object.
     */
    public String getDescription() {
        return (this.description);
    }

    /**
     * This method is used to retrieve the {@code Date} object reflecting
     * the date the {@code Note} object was added.
     *
     * @return Date The {@code Date} object reflecting the date the {@code Note} object was added.
     */
    public Date getAddDate() {
        return (this.addDate);
    }

    /**
     * This method is used to retrieve the completion status of the {@code Note} object.
     *
     * @return boolean True if the {@code Note} object was completed.
     */
    public boolean getIsDone() {
        return (this.isDone);
    }

    /**
     * This method is used to retrieve the {@code Date} object reflecting
     * the date the {@code Note} object was completed.
     *
     * @return Date The {@code Date} object reflecting the date the {@code Note} object was completed.
     */
    public Date getDoneDate() {
        return (this.doneDate);
    }

    /**
     * This method is used to print details of the {@code Note} object.
     *
     * @exception CommandException If there are errors in the command input.
     */
    public void printList() throws CommandException {
        System.out.print("\t" + String.format("%3d", this.serialNum));
        System.out.print(". ");
        System.out.print(this.getTaskIcon());
        System.out.print(this.getStatusIcon() + " ");
        DukeUI.listWrap(this.description, 25, this.addDate);
        printDetails();
    }

    /**
     * This method is used to print additional details of the {@code Note} object.
     */
    public void printDetails(){
        if (this.isDone) {
            System.out.println("\t\t\tDone     : " +
                    TASK_TIME.format(this.doneDate));
        }
    }

    /**
     * This method is used to retrieve the status icon reflecting the completion status of the {@code Note} object.
     *
     * @return String The status icon reflecting the completion status of the {@code Note} object.
     */
    public String getStatusIcon() {
        if(this.isDone){
            return ("[\u2713]");
        } else {
            return ("[\u2718]");
        }
    }

    /**
     * This method is used to retrieve the task icon reflecting the type of sub-class of {@code Note} objects.
     *
     * @return String The task icon reflecting the type of sub-class of {@code Note} objects.
     */
    public String getTaskIcon() throws CommandException {
        return NoteType.getTaskIcon(this.getObjectClass());
    }

    //ABSTRACT METHODS----------------------------------
    /**
     * This method is used to delete existing {@code Note} object and make associated adjustments.
     */
    public abstract void deleteExistingNote();

    /**
     * This method is used to retrieve the {@code Budget} object attached to the {@code Note} object, if any.
     *
     * @return Budget The {@code Budget} object attached to the {@code Note} object, if any.
     */
    public abstract Budget getBudgetObject();

    /**
     * This method returns the class name of the {@code Note} object as a {@code String}.
     *
     * @return String The class name of the {@code Note} object.
     */
    public abstract String getObjectClass();
}