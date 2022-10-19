package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PRIORITY_STATUS;

import java.util.stream.Stream;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddTagCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.DeadlineTag;
import seedu.address.model.tag.PriorityTag;

/**
 * AddTagCommandParser parses the arguments and supplies these arguments
 * to the AddTagCommand.
 */
public class AddTagCommandParser implements Parser<Command> {
    @Override
    public Command parse(String args) throws ParseException {
        String[] indexWithTags = args.strip().split(" ", 2);
        if (indexWithTags.length != 2) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagCommand.MESSAGE_USAGE));
        }
        Index index = ParserUtil.parseIndex(indexWithTags[0]);
        String tags = " " + indexWithTags[1];
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(tags, PREFIX_PRIORITY_STATUS, PREFIX_DEADLINE);
        if (!areAnyPrefixesPresent(argMultiMap, PREFIX_PRIORITY_STATUS, PREFIX_DEADLINE)
                || !argMultiMap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                    AddTagCommand.MESSAGE_USAGE));
        }
        PriorityTag priorityTag = null;
        DeadlineTag deadlineTag = null;
        String priorityStatus = argMultiMap.getValue(PREFIX_PRIORITY_STATUS).orElse(null);
        String deadline = argMultiMap.getValue(PREFIX_DEADLINE).orElse(null);
        if (priorityStatus != null) {
            priorityTag = ParserUtil.parsePriorityTag(priorityStatus);
        }
        if (deadline != null) {
            deadlineTag = ParserUtil.parseDeadlineTag(deadline);
        }
        return new AddTagCommand(priorityTag, deadlineTag, index);
    }

    private static boolean areAnyPrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
