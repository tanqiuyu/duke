package duke.parser;


import duke.commands.CommandException;
import duke.ui.DukeUI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * An interface that read and understand user inputs representing dates and times.
 *
 * @author tanqiuyu
 * @since 2020-09-16
 */
public interface DateParser {

    SimpleDateFormat UNDERSTOOD_DAYM = new SimpleDateFormat("dd");
    SimpleDateFormat UNDERSTOOD_MONTH = new SimpleDateFormat("MMM");
    SimpleDateFormat UNDERSTOOD_YEAR = new SimpleDateFormat("yyyy");
    SimpleDateFormat UNDERSTOOD_DAYW_FULL = new SimpleDateFormat("EEEEE");
    SimpleDateFormat UNDERSTOOD_DAYW_PART = new SimpleDateFormat("E");
    SimpleDateFormat UNDERSTOOD_TIME = new SimpleDateFormat("HH:mm");

    /**
     * This method is used to understand any textual input that could indicate a specific date.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForDayW(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }

        Date now = new Date();
        Date ytd = new Date(now.getTime() - 86400000);
        Date tmr = new Date(now.getTime() + 86400000);

        String[] today = {"TODAY", "TDY"};
        String[] yesterday = {"YESTERDAY", "YTD"};
        String[] tomorrow = {"TOMORROW", "TMR", "TML", "TMRW"};

        for(String word: today) {
            if (input.toUpperCase().equals(word)) {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(now));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(now));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(now));
                return true;
            }
        }

        for(String word: yesterday) {
            if (input.toUpperCase().equals(word)) {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(ytd));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(ytd));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(ytd));
                return true;
            }
        }

        for(String word: tomorrow) {
            if (input.toUpperCase().equals(word)) {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(tmr));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(tmr));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(tmr));
                return true;
            }
        }

        for (int i = 1; i < 8; i++) {
            Date next = new Date(now.getTime() + (i * 86400000));
            String nextDay_Full = UNDERSTOOD_DAYW_FULL.format(next).toUpperCase();
            String nextDay_Part = UNDERSTOOD_DAYW_PART.format(next).toUpperCase();
            if (input.toUpperCase().equals(nextDay_Full) || input.toUpperCase().equals(nextDay_Part)) {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(next));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(next));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(next));
                return true;
            }
        }

        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific day in the month.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForDayM(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("1ST", "1");
        input = input.replace("2ND", "2");
        input = input.replace("3RD", "3");
        input = input.replace("1TH", "1");
        input = input.replace("2TH", "2");
        input = input.replace("3TH", "3");
        input = input.replace("4TH", "4");
        input = input.replace("5TH", "5");
        input = input.replace("6TH", "6");
        input = input.replace("7TH", "7");
        input = input.replace("8TH", "8");
        input = input.replace("9TH", "9");
        input = input.replace("0TH", "0");

        try {
            if (Integer.parseInt(input) <= 31 && Integer.parseInt(input) >= 1) {
                understoodDate.replace("day", input);
                return true;
            }
        } catch (Exception ignored) { }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific month in the year.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForMonth(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        String[] months = {"MMMMM", "MMM", "MM"};
        ArrayList<SimpleDateFormat> formats = new ArrayList<>();

        for (String month : months) {
            formats.add(new SimpleDateFormat(month));
        }

        Date now = new Date();
        if (input.toUpperCase().equals("THIS MONTH")) {
            understoodDate.replace("month", UNDERSTOOD_MONTH.format(now));
            understoodDate.replace("year", UNDERSTOOD_YEAR.format(now));
            return true;
        } else {
            for (SimpleDateFormat format : formats) {
                try {
                    understoodDate.replace("month", UNDERSTOOD_MONTH.format(format.parse(input)));
                    return true;
                } catch (ParseException ignored) { }
            }
        }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific year.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForYear(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("'", "");
        SimpleDateFormat format = new SimpleDateFormat("yy");

        Date now = new Date();
        if (input.toUpperCase().equals("THIS YEAR")) {
            understoodDate.replace("year", UNDERSTOOD_YEAR.format(now));
            return true;
        } else {
            try {
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(format.parse(input)));
                return true;
            } catch (ParseException ignored) { }
        }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific day in a specific month.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForDayMonth(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("-", "~");
        input = input.replace(".", "~");
        input = input.replace("'", "~");

        String[] days = {"dd"};
        String[] months = {"MM", "MMM", "MMMMM"};
        ArrayList<SimpleDateFormat> formats = new ArrayList<>();

        for (String day : days) {
            for (String month : months) {
                formats.add(new SimpleDateFormat(day + "~" + month));
                if (!month.equals("MM")) {
                    formats.add(new SimpleDateFormat(month + "~" + day));
                    formats.add(new SimpleDateFormat(month + day));
                    formats.add(new SimpleDateFormat(day + month));
                }
            }
        }

        for (SimpleDateFormat format : formats) {
            try {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(format.parse(input)));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(format.parse(input)));
                return true;
            } catch (ParseException ignored) { }
        }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific month in a specific year.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForMonthYear(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("-", "~");
        input = input.replace(".", "~");
        input = input.replace("'", "~");

        String[] months = {"MMM", "MMMMM"};
        String[] years = {"yy"};
        ArrayList<SimpleDateFormat> formats = new ArrayList<>();

        for (String month : months) {
            for (String year : years) {
                formats.add(new SimpleDateFormat(month + "~" + year));
                formats.add(new SimpleDateFormat(year + "~" + month));
            }
        }

        for (SimpleDateFormat format : formats) {
            try {
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(format.parse(input)));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(format.parse(input)));
                return true;
            } catch (ParseException ignored) { }
        }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific date in full.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForFullDate(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("-", "~");
        input = input.replace(".", "~");
        input = input.replace("'", "~");
        input = input.replace("/", "~");

        String[] days = {"dd"};
        String[] months = {"MM", "MMM", "MMMMM"};
        String[] years = {"yy"};
        ArrayList<SimpleDateFormat> formats = new ArrayList<>();

        for (String month: months) {
            for(String year: years) {
                for (String day: days) {
                    formats.add(new SimpleDateFormat(day + "~" + month + "~" + year));
                    formats.add(new SimpleDateFormat(year + "~" + month + "~" + day));
                    if (!month.equals("MM")) {
                        formats.add(new SimpleDateFormat(month + day + "~" + year));
                        formats.add(new SimpleDateFormat(year + "~" + month + day));
                        formats.add(new SimpleDateFormat(day + "~" + month + year));
                        formats.add(new SimpleDateFormat(month + year + "~" + day));
                        formats.add(new SimpleDateFormat(day + month + "~" + year));
                        formats.add(new SimpleDateFormat(year + "~" + day + month));
                        formats.add(new SimpleDateFormat(day + month + year));
                    }
                }
            }
        }
        for (SimpleDateFormat format : formats) {
            try {
                understoodDate.replace("day", UNDERSTOOD_DAYM.format(format.parse(input)));
                understoodDate.replace("month", UNDERSTOOD_MONTH.format(format.parse(input)));
                understoodDate.replace("year", UNDERSTOOD_YEAR.format(format.parse(input)));
                return true;
            } catch (ParseException ignored) { }
        }
        return false;
    }

    /**
     * This method is used to understand any textual input that could indicate a specific time.
     *
     * @param input The textual input provided by the user in verbatim.
     * @param understoodDate The {@code HashMap} object used to store the results.
     * @return boolean Whether the understanding has been successful.
     */
    static boolean checkForTime(String input, HashMap<String, String> understoodDate) {

        if(input == null) { return false; }
        input = input.replace("-", "~");
        input = input.replace(".", "~");
        input = input.replace("'", "~");
        input = input.replace(":", "~");

        String[] times = {"hh~mma", "hha", "HHmm'HS'", "HHmm'H'", "HH~mm"};
        ArrayList<SimpleDateFormat> formats = new ArrayList<>();

        for(String time: times) {
            formats.add(new SimpleDateFormat(time));
        }

        for (SimpleDateFormat format: formats) {
            try {
                understoodDate.replace("time", UNDERSTOOD_TIME.format(format.parse(input)));
                return true;
            } catch (ParseException ignored) { }
        }
        return false;
    }

    /**
     * This method is used to understand textual input representing dates and times.
     *
     * @param userInput The textual input provided by the user in verbatim.
     * @return Date The understood date and time in the format of a {@code Date} object.
     */
    static Date understandDateInput(String userInput) throws ParseException, CommandException {

        Date now = new Date();
        Date understoodDateTime;
        HashMap<String, String> dateHash = new HashMap<>();
        dateHash.put("day", null);
        dateHash.put("month", null);
        dateHash.put("year", null);
        dateHash.put("time", null);

        String[] userInputs = userInput.toUpperCase().split(" ");
        ArrayList<String> inputs = new ArrayList<>();
        for(String input: userInputs){ inputs.add(input.trim()); }


        for(int i=0; i<inputs.size(); i++) {
            String input = inputs.get(i);
            if (checkForDayW(input, dateHash)) {
                inputs.remove(i);
                break;
            }
        }

        if(dateHash.get("day") == null && dateHash.get("month") == null && dateHash.get("year") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForFullDate(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        if (dateHash.get("day") == null && dateHash.get("month") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForDayMonth(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        if(dateHash.get("month") == null && dateHash.get("year") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForMonthYear(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        if(dateHash.get("day") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForDayM(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        if(dateHash.get("month") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForMonth(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        for(int i=0; i<inputs.size(); i++) {
            String input = inputs.get(i);
            if (checkForTime(input, dateHash)) {
                inputs.remove(i);
                break;
            }
        }

        if(dateHash.get("year") == null) {
            for(int i=0; i<inputs.size(); i++) {
                String input = inputs.get(i);
                if(checkForYear(input, dateHash)) {
                    inputs.remove(i);
                    break;
                }
            }
        }

        if(dateHash.get("year") == null) {
            dateHash.replace("year", UNDERSTOOD_YEAR.format(now));

            Date cutOffMin = new Date(now.getTime() - ((long)90*86400000));
            Date cutOffMax = new Date(now.getTime() + ((long)276*86400000));

            String dateString = dateHash.get("day") + "-" + dateHash.get("month") + "-" +
                    dateHash.get("year");
            Date testDate = DukeUI.INPUT_DATE.parse(dateString);

            if(testDate.before(cutOffMin)) {
                dateHash.replace("year", Integer.toString((Integer.parseInt(UNDERSTOOD_YEAR.format(now)) + 1)));
            } else if(testDate.after(cutOffMax)) {
                dateHash.replace("year", Integer.toString((Integer.parseInt(UNDERSTOOD_YEAR.format(now)) - 1)));
            }
        }

        if(dateHash.get("time") == null) {
            dateHash.replace("time", "00:00");
        }

        String dateTimeString = dateHash.get("day") + "-" + dateHash.get("month") + "-" +
                dateHash.get("year") + " " + dateHash.get("time");

        try {
            understoodDateTime = DukeUI.INPUT_TIME.parse(dateTimeString);
        } catch (ParseException e){
            throw new CommandException("I can't understand the date and time you are trying to specify.");
        }

        return understoodDateTime;
    }
}
