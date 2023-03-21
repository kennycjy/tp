package seedu.address.model.person;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.timetable.Lesson;

/**
 * This class was added to facilitate adding lessons to a person.
 */
public class LessonSet {
    private final Set<Lesson> lessons;

    public LessonSet() {
        this.lessons = new HashSet<>();
    }

    public void add(Lesson lesson) {
        lessons.add(lesson);
    }

    public void addAll(Collection<? extends Lesson> lessons) {
        this.lessons.addAll(lessons);
    }

    public void remove(Lesson lesson) {
        this.lessons.remove(lesson);
    }

    public void removeAll(Collection<? extends Lesson> lessons) {
        this.lessons.removeAll(lessons);
    }

    @Override
    public String toString() {
        return this.lessons.toString();
    }
}
