package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;

import org.joda.time.LocalTime;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.IndexHandler;
import seedu.address.model.Model;
import seedu.address.model.location.Location;
import seedu.address.model.person.ContactIndex;
import seedu.address.model.person.ModuleTagSet;
import seedu.address.model.person.Person;
import seedu.address.model.person.User;
import seedu.address.model.tag.ModuleTag;
import seedu.address.model.timetable.Lesson;
import seedu.address.model.timetable.Module;
import seedu.address.model.timetable.time.SchoolDay;

/**
 * Removes modules from an existing person in the address book.
 */
public class UntagCommand extends Command {

    public static final String COMMAND_WORD = "untag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes a ModuleTag to a person. \n"
            + "untag <index> m/<module> : Removes tags from the person of given index in displayed list. \n"
            + "untag n/<module> : Removes tags from your own profile instead.";

    public static final String MESSAGE_UNTAG_PERSON_SUCCESS = "Module(s) untagged to Person! \n";
    public static final String MESSAGE_UNTAG_USER_SUCCESS = "Module(s) untagged to User! \n";
    public static final String MESSAGE_NO_TAGS = "At least one Module must be provided.";

    private final ContactIndex index;
    private final Set<ModuleTag> moduleTags;

    /**
     * @param index of the person in the filtered person list to edit.
     * @param modulesToRemove set of modules to be removed.
     */
    public UntagCommand(ContactIndex index, Set<ModuleTag> modulesToRemove) {
        requireNonNull(modulesToRemove);

        this.index = index;
        this.moduleTags = modulesToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (index == null) {
            return removeUserTags(model);
        }
        return removePersonTags(model);
    }

    /**
     * Removes tags from person at given index.
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display.
     * @throws CommandException if an error occurs during command execution.
     */
    public CommandResult removePersonTags(Model model) throws CommandException {
        IndexHandler indexHandler = new IndexHandler(model);

        Person personToEdit = indexHandler.getPersonByIndex(index).orElseThrow(() ->
                new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));

        ModuleTagSet oldModules = personToEdit.getModuleTags();

        oldModules.removeAll(this.moduleTags);

        Set<ModuleTag> userModuleTags = model.getUser().getImmutableModuleTags();

        personToEdit.setCommonModules(userModuleTags);

        model.updateObservablePersonList();

        for (ModuleTag tag : moduleTags) {
            String day = tag.getDay();
            String start = tag.getStartTime();
            String end = tag.getEndTime();
            if (day == null || start == null || end == null) {
                continue;
            }

            Module mod = new Module(tag.toString());
            int startHour = Integer.parseInt(start.substring(0, 2));
            int startMin = Integer.parseInt(start.substring(2));
            int endHour = Integer.parseInt(end.substring(0, 2));
            int endMin = Integer.parseInt(end.substring(2));
            LocalTime startTime = new LocalTime(startHour, startMin);
            LocalTime endTime = new LocalTime(endHour, endMin);

            SchoolDay schoolDay = SchoolDay.valueOf(day);

            Lesson lesson = new Lesson(mod, startTime, endTime, schoolDay, Location.NUS);

            personToEdit.getLessons().remove(lesson);
        }

        return new CommandResult(String.format(MESSAGE_UNTAG_PERSON_SUCCESS
                + "Name: " + personToEdit.getName().toString() + '\n'
                + "Modules: " + personToEdit.getImmutableModuleTags().toString() + '\n'
                + "Module(s) in common: " + personToEdit.getImmutableCommonModuleTags().toString() + '\n'
                + "Lessons: " + personToEdit.getLessons().toString()));
    }

    /**
     * Removes tags from the user.
     * @param model {@code Model} which the command should operate on
     * @return feedback message of the operation result for display.
     * @throws CommandException if an error occurs during command execution.
     */
    public CommandResult removeUserTags(Model model) throws CommandException {
        User editedUser = model.getUser();

        ModuleTagSet userModuleTags = editedUser.getModuleTags();

        userModuleTags.removeAll(this.moduleTags);

        model.getObservablePersonList().forEach(person ->
                person.setCommonModules(editedUser.getImmutableModuleTags()));

        for (ModuleTag tag : moduleTags) {
            String day = tag.getDay();
            String start = tag.getStartTime();
            String end = tag.getEndTime();
            if (day == null || start == null || end == null) {
                continue;
            }

            Module mod = new Module(tag.toString());
            int startHour = Integer.parseInt(start.substring(0, 2));
            int startMin = Integer.parseInt(start.substring(2));
            int endHour = Integer.parseInt(end.substring(0, 2));
            int endMin = Integer.parseInt(end.substring(2));
            LocalTime startTime = new LocalTime(startHour, startMin);
            LocalTime endTime = new LocalTime(endHour, endMin);

            SchoolDay schoolDay = SchoolDay.valueOf(day);

            Lesson lesson = new Lesson(mod, startTime, endTime, schoolDay, Location.NUS);

            editedUser.getLessons().remove(lesson);
        }

        return new CommandResult(String.format(MESSAGE_UNTAG_USER_SUCCESS
                + "Name: " + editedUser.getName().toString() + '\n'
                + "Modules: " + editedUser.getImmutableModuleTags().toString() + '\n'
                + "Lessons: " + editedUser.getLessons().toString()));
    }

    public ContactIndex getIndex() {
        return this.index;
    }

    public Set<ModuleTag> getModules() {
        return this.moduleTags;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof UntagCommand) {
            UntagCommand otherCommand = (UntagCommand) other;
            return otherCommand.getIndex().equals(getIndex())
                    && otherCommand.getModules().equals(getModules());
        }
        return false;

    }
}
