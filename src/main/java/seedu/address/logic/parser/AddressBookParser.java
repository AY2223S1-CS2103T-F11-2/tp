package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddExamCommand;
import seedu.address.logic.commands.AddModuleCommand;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.AddTaskCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearModulesCommand;
import seedu.address.logic.commands.ClearTasksCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteModuleCommand;
import seedu.address.logic.commands.DeleteTaskCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditModuleCommand;
import seedu.address.logic.commands.EditTaskCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterTasksCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindModulesCommand;
import seedu.address.logic.commands.FindTasksCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.LinkExamCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListExamTasksCommand;
import seedu.address.logic.commands.ListModulesCommand;
import seedu.address.logic.commands.ListTasksCommand;
import seedu.address.logic.commands.MarkCommand;
import seedu.address.logic.commands.SortTaskCommand;
import seedu.address.logic.commands.UnlinkExamCommand;
import seedu.address.logic.commands.UnmarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;



/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case EditTaskCommand.COMMAND_WORD:
            return new EditTaskCommandParser().parse(arguments);

        case EditModuleCommand.COMMAND_WORD:
            return new EditModuleCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case MarkCommand.COMMAND_WORD:
            return new MarkCommandParser().parse(arguments);

        case UnmarkCommand.COMMAND_WORD:
            return new UnmarkCommandParser().parse(arguments);

        case AddModuleCommand.COMMAND_WORD:
            return new AddModuleCommandParser().parse(arguments);

        case LinkExamCommand.COMMAND_WORD:
            return new LinkExamCommandParser().parse(arguments);

        case UnlinkExamCommand.COMMAND_WORD:
            return new UnlinkExamCommandParser().parse(arguments);

        case SortTaskCommand.COMMAND_WORD:
            return new SortTaskCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ListCommand.COMMAND_WORD:
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            return new HelpCommand();

        case ListTasksCommand.COMMAND_WORD:
            return new ListTasksCommand();

        case FindTasksCommand.COMMAND_WORD:
            return new FindTaskCommandParser().parse(arguments);

        case FindModulesCommand.COMMAND_WORD:
            return new FindModulesCommandParser().parse(arguments);

        case ListModulesCommand.COMMAND_WORD:
            return new ListModulesCommand();

        case AddTaskCommand.COMMAND_WORD:
            return new AddTaskCommandParser().parse(arguments);

        case DeleteTaskCommand.COMMAND_WORD:
            return new DeleteTaskCommandParser().parse(arguments);

        case DeleteModuleCommand.COMMAND_WORD:
            return new DeleteModuleCommandParser().parse(arguments);

        case ClearTasksCommand.COMMAND_WORD:
            return new ClearTasksCommand();

        case ClearModulesCommand.COMMAND_WORD:
            return new ClearModulesCommand();

        case AddTagCommand.COMMAND_WORD:
            return new AddTagCommandParser().parse(arguments);

        case FilterTasksCommand.COMMAND_WORD:
            return new FilterTasksCommandParser().parse(arguments);

        case AddExamCommand.COMMAND_WORD:
            return new AddExamCommandParser().parse(arguments);

        case ListExamTasksCommand.COMMAND_WORD:
            return new ListExamTasksCommandParser().parse(arguments);

        default:
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
