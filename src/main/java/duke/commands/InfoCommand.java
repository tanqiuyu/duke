package duke.commands;

import duke.storage.DukeList;
import duke.storage.DukeStorage;
import duke.ui.DukeUI;

/**
 * An extension of the {@code DukeCommand} object that prints all the commands available in {@code Duke}.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
public class InfoCommand extends DukeCommand {

    //VARIABLES-----------------------------------------
    private String infoType;

    //CONSTRUCTORS--------------------------------------
    /**
     * This method constructs a {@code InfoCommand} object.
     *
     * @param cmdType The type of {@code DukeCommand} being constructed.
     */
    public InfoCommand(String cmdType, String infoType) throws CommandException {

        super(cmdType);
        this.infoType = infoType;
    }

    /**
     * This method initialises a {@code InfoCommand} object.
     */
    @SuppressWarnings("unused")
    public InfoCommand() { super(); }


    //METHODS-------------------------------------------
    /**
     * This method executes the function of the {@code InfoCommand} object.
     *
     * @param dukeNotes The {@code DukeList} object that holds the notes managed by {@code Duke}.
     * @param dukeStorage The {@code DukeStorage} object that holds access to the saved files of {@code Duke}.
     */
    public void execute(DukeList dukeNotes, DukeStorage dukeStorage) {

        DukeUI.printDivider();
        DukeUI.showCommandList(infoType);
        DukeUI.printDivider();
    }
}
