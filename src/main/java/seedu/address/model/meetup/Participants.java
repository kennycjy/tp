package seedu.address.model.meetup;

import seedu.address.model.person.ContactIndex;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a group of participants in a meetup by their indexes.
 */
public class Participants {

    private List<Person> participants;
    private final Set<ContactIndex> contactIndices;

    /**
     * Constructor for {@code Participants} object.
     * @param participants The set of participant indexes.
     */
    public Participants(List<Person> participants) {

        this.participants = new ArrayList<>();
        //to remove the user
        for (Person person : participants) {
            if (person.getContactIndex().getContactIndex() != 0) {
                this.participants.add(person);
            }
        }
        this.contactIndices = new HashSet<>();
    }

    public Participants() {
        this.participants = new ArrayList<>();
        this.contactIndices = new HashSet<>();
    }

    public Participants(Set<ContactIndex> indices) {
        this.participants = new ArrayList<>();
        this.contactIndices = indices;
    }

    public void setParticipants(List<Person> participants) {
        this.participants = participants;
    }

    public Set<ContactIndex> getContactIndices() {
        return this.contactIndices;
    }

    public List<Person> getParticipants() {
        return this.participants;
    }

}
