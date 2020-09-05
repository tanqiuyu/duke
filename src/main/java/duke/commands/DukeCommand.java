package duke.commands;

import duke.storage.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public abstract class DukeCommand {

    //VARIABLES-----------------------------------------
    protected ArrayList<String> inputs;
    boolean confirmExit = false;

    //CONSTRUCTORS--------------------------------------
    public DukeCommand(ArrayList<String> inputs) {

        this.inputs = inputs;
    }

    public DukeCommand() {}

    //METHODS-------------------------------------------



    //GET STATEMENTS------------------------------------
    public boolean getConfirmExit() {
        return this.confirmExit;
    }


    //ABSTRACT METHODS----------------------------------
    public abstract void execute(DukeList dukeList, DukeStorage dukeStorage) throws ParseException, CommandException, IOException;

}