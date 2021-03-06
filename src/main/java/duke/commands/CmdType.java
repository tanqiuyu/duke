package duke.commands;

/**
 * This enum lists all the generic commands available in {@code Duke}.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
@SuppressWarnings("unused")
public enum CmdType {

    AUTOSAVE("#autosave", "#ats", null, 0),
    COMMANDS("#commands", "#cmd", null, 0),
    DELETE("#delete", "#del", null, 0),
    EDITEND("#editend", "#ede", NoteType.EVENT, 0),
    EDITSTART("#editstart", "#eds", NoteType.EVENT, 0),
    EDITTARGET("#edittarget", "#edt", NoteType.DEADLINE, 0),
    EDITDESC("#editdesc", "#edd", null, 0),
    EXITDUKE("#exitduke", "#xit", null, 0),
    EXTDLINE("#extend", "#xtd", null, 0),
    LISTBILLS("#listbills", "#lbp", NoteType.BILL, 1),
    LISTBIRTHDAYS("#listbirthdays", "#lbd", NoteType.BIRTHDAY, 1),
    LISTBUDGETS("#listbudgets", "#lbg", NoteType.NOTE, 1),
    LISTDEADLINES("#listdeadlines", "#ldl", NoteType.DEADLINE, 1),
    LISTEVENTS("#listevents", "#lev", NoteType.EVENT, 1),
    LISTSHOPLISTS("#listshoplists", "#lsl", NoteType.SHOPLIST, 0),
    LISTTASKS("#listtasks", "#ltk", NoteType.TASK, 1),
    LISTWEDDINGS("#listweddings", "#lwd", NoteType.WEDDING, 1),
    LISTNOTES("#listnotes", "#lnt", NoteType.NOTE, 1),
    LISTNXT24("#listnxt24", "#n24", NoteType.NOTE, 1),
    LISTNXT48("#listnxt48", "#n48", NoteType.NOTE, 2),
    LISTNXT72("#listnxt72", "#n72", NoteType.NOTE, 3),
    MARKDONE("#markdone", "#mkd", null, 0),
    SAVEDUKE("#saveduke", "#sav", null, 0),
    TRANSFER("#transfer", "#txf", null, 0),
    UNDO("#undo", "#und", null, 0),
    WIPEDUKE("#wipeduke", "#wpe", null, 0),
    NEWNOTE("na", "na",null,0);

    private final String COMMAND;
    private final String SHORT_COMMAND;
    private final NoteType RELEVANT_NOTE_TYPE;
    private final int TIMELINE_DAYS;

    /**
     * This method constructs the various {@code CmdType} enum items.
     *
     * @param command The standard text required to activate the command.
     * @param shortCommand The short text required to activate the command.
     * @param relevantNoteType The {@code NoteType} associated with the command.
     * @param timelineDays The timeline associated with the {@code ListCommand} objects.
     */
    CmdType(String command, String shortCommand, NoteType relevantNoteType, int timelineDays) {
        this.COMMAND = command;
        this.SHORT_COMMAND = shortCommand;
        this.RELEVANT_NOTE_TYPE = relevantNoteType;
        this.TIMELINE_DAYS = timelineDays;
    }

    /**
     * This method returns the associated {@code CmdType}
     * for the generic command or short command text provided.
     *
     * @param cmdType The command text entered by the user.
     * @return CmdType The generic command to execute.
     * @exception CommandException If no matching command could be found.
     */
    public static CmdType getKey(String cmdType) throws CommandException {

        for(CmdType type: CmdType.values()){
            if(cmdType.equals(type.COMMAND) || cmdType.equals(type.SHORT_COMMAND) || cmdType.equals(type.toString())){
                return type;
            }
        }
        throw new CommandException("There is no " + cmdType + " type of command in Duke, yet.");
    }

    /**
     * This method returns the corresponding standard command text
     * for the generic command or short command text provided.
     *
     * @param cmdType The generic command or short command text entered by the user.
     * @return String The corresponding standard command text.
     * @exception CommandException If no matching standard command text could be found.
     */
    public static String getCOMMAND(String cmdType) throws CommandException {

        for(CmdType type: CmdType.values()){
            if(cmdType.equals(type.COMMAND) || cmdType.equals(type.SHORT_COMMAND) || cmdType.equals(type.toString())){
                return type.COMMAND;
            }
        }
        throw new CommandException("There is no " + cmdType + " type of command in Duke, yet.");
    }

    /**
     * This method returns the corresponding short command text
     * for the generic command or short command text provided.
     *
     * @param cmdType The generic command or standard command text entered by the user.
     * @return String The corresponding short command text.
     * @exception CommandException If no matching short command text could be found.
     */
    public static String getSHORT_COMMAND(String cmdType) throws CommandException {

        for(CmdType type: CmdType.values()){
            if(cmdType.equals(type.COMMAND) || cmdType.equals(type.SHORT_COMMAND) || cmdType.equals(type.toString())){
                return type.SHORT_COMMAND;
            }
        }
        throw new CommandException("There is no " + cmdType + " type of command in Duke, yet.");
    }

    /**
     * This method returns the relevant {@code NoteType}
     * for the generic command or short command text provided.
     *
     * @param cmdType The generic command or standard command text entered by the user.
     * @return NoteType The corresponding relevant NoteType.
     * @exception CommandException If no matching NoteType could be found.
     */
    public static NoteType getRELEVANT_NOTE_TYPE(String cmdType) throws CommandException {

        for(CmdType type: CmdType.values()){
            if(cmdType.equals(type.COMMAND) || cmdType.equals(type.SHORT_COMMAND) || cmdType.equals(type.toString())){
                return type.RELEVANT_NOTE_TYPE;
            }
        }
        throw new CommandException("There is no " + cmdType + " type of command in Duke, yet.");
    }

    /**
     * This method returns the relevant timeline element applicable to {@code ListCommand} objects
     * for the generic command or short command text provided.
     *
     * @param cmdType The generic command or standard command text entered by the user.
     * @return int The corresponding relevant timeline element applicable, returns {@code 0} if not applicable.
     * @exception CommandException If no matching timeline element could be found.
     */
    public static int getTIMELINE_DAYS(String cmdType) throws CommandException {

        for(CmdType type: CmdType.values()){
            if(cmdType.equals(type.COMMAND) || cmdType.equals(type.SHORT_COMMAND) || cmdType.equals(type.toString())){
                return type.TIMELINE_DAYS;
            }
        }
        throw new CommandException("There is no " + cmdType + " type of command in Duke, yet.");
    }
}
