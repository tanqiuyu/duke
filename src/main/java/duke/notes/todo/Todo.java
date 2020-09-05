package duke.notes.todo;

import duke.budget.*;
import duke.commands.*;
import duke.notes.*;
import java.util.Date;

public class Todo extends Note {

    //VARIABLES-----------------------------------------
    protected static int tasksOutstanding;
    protected static int tasksCompleted;


    //CONSTRUCTORS--------------------------------------
    public Todo(int serialNum, String description, Date addDate) {
        super(serialNum, description, addDate);
        tasksOutstanding++;
    }

    public Todo() {
        tasksOutstanding++;
    }

    public Todo(int serialNum, String description, Date addDate, Date doneDate,
                 boolean isDone) {
        this.serialNum = serialNum;
        this.description = description;
        this.addDate = addDate;
        this.doneDate = doneDate;
        this.isDone = isDone;
        tasksCompleted++;
    }

    public Todo(int serialNum, String description, Date addDate,
                boolean isDone) {
        this.serialNum = serialNum;
        this.description = description;
        this.addDate = addDate;
        this.isDone = isDone;
        tasksOutstanding++;
    }


    //SET STATEMENTS------------------------------------
    @Override
    public boolean markAsDone(Date doneDate) throws CommandException {
        if(super.markAsDone(doneDate)) {
            tasksOutstanding--;
            tasksCompleted++;
            this.printList();
            return true;
        } else {
            this.printList();
            return false;
        }
    }

    public void deleteExistingNote() {
        if(isDone){
            System.out.print("\tTask #" + this.serialNum + " was already done!");
            System.out.println("\t...deleting the task anyway.");
            tasksCompleted--;
        } else {
            System.out.println("\tNoted! I've deleted Task #" + this.serialNum + ".");
            tasksOutstanding--;
        }
    }


    //GET STATEMENTS------------------------------------
    public static int getTasksOutstanding() {
        return (tasksOutstanding);
    }

    public static int getTasksCompleted() {
        return (tasksCompleted);
    }

    public String getTaskIcon() throws CommandException {
        return NoteType.getTaskIcon("Todo");
    }

    public String getSaveText() {
        String text = "Todo/" +
                this.serialNum + "/" +
                this.description.toString() + "/" +
                INPUT_DATE.format(this.addDate) + "/" +
                this.isDone;

        if(isDone) {
            text = text + "/" + INPUT_DATE.format(this.doneDate) + "\n";
        } else {
            text = text + "\n";
        }
        return text;
    }


    //ABSTRACT METHODS----------------------------------
    public Budget getBudgetObject() {
        return null;
    }
}