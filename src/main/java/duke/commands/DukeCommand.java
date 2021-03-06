package duke.commands;

import duke.parser.DateException;
import duke.storage.DukeList;
import duke.storage.DukeStorage;
import java.io.IOException;
import java.text.ParseException;

/**
 * An abstract class that forms the basis of command objects in {@code Duke}.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
public abstract class DukeCommand {

    //VARIABLES-----------------------------------------
    protected CmdType cmdType;
    boolean confirmExit = false;

    //CONSTRUCTORS--------------------------------------
    /**
     * This method constructs a {@code DukeCommand} object.
     *
     * @param cmdType The type of {@code DukeCommand} being constructed.
     */
    public DukeCommand(String cmdType) throws CommandException {
        this.cmdType =  CmdType.getKey(cmdType);
    }

    /**
     * This method initialises a {@code DukeCommand} object.
     */
    @SuppressWarnings("unused")
    public DukeCommand() {}

    //METHODS-------------------------------------------


    //GET STATEMENTS------------------------------------
    /**
     * This method initialises a {@code DukeCommand} object.
     *
     * @return boolean True if the command demands that {@code Duke} terminates operation.
     */
    public boolean getConfirmExit() {
        return this.confirmExit;
    }


    //ABSTRACT METHODS----------------------------------
    /**
     * This method executes the function of the {@code DukeCommand} object.
     *
     * @param dukeNotes The {@code DukeList} object that holds the notes managed by {@code Duke}.
     * @param dukeStorage The {@code DukeStorage} object that holds access to the saved files of {@code Duke}.
     * @exception ParseException If there are errors in the formats of command attributes.
     * @exception CommandException If there are errors in the command input.
     * @exception IOException If the command attributes specified does not exist.
     * @exception DateException If there are errors in the formats or substance of {@code Date} objects.
     */
    public abstract void execute(DukeList dukeNotes, DukeStorage dukeStorage)
            throws ParseException, CommandException, IOException, DateException, InterruptedException;

}
