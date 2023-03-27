package seedu.address.model.commitment;

import java.util.ArrayList;
import java.util.Objects;

import org.joda.time.LocalTime;

import seedu.address.model.location.Location;
import seedu.address.model.time.Day;
import seedu.address.model.time.HourBlock;
import seedu.address.model.time.TimePeriod;

/**
 * A Commitment is somewhere that a person needs to be at a
 * particular time.
 */
public class Commitment {
    protected final Location location;
    protected final TimePeriod timePeriod;

    /**
     * Constructor for a {@code Commitment} object.
     */
    public Commitment(Location location, TimePeriod timePeriod) {
        this.location = location;
        this.timePeriod = timePeriod;
    }

    public Location getLocation() {
        return location;
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public Day getDay() {
        return timePeriod.getSchoolDay();
    }

    public LocalTime getStartTime() {
        return timePeriod.getStartTime();
    }

    public LocalTime getEndTime() {
        return timePeriod.getEndTime();
    }

    /**
     * Checks whether the Commitment can fit into a list of slots.
     */
    public boolean canFitIntoDaySchedule(ArrayList<HourBlock> slots) {
        boolean canFit = true;
        for (int i = getStartTime().getHourOfDay() - 8; i < getEndTime().getHourOfDay() - 8; i++) {
            canFit &= slots.get(i).isFree();
        }
        return canFit;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Commitment)) {
            return false;
        }

        Commitment commitment = (Commitment) other;
        return location.equals(commitment.location)
                && timePeriod.equals(commitment.timePeriod);
    }

    @Override
    public String toString() {
        return "Commitment{"
                + getDay()
                + ", " + getStartTime()
                + " to " + getEndTime()
                + " at " + location + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(timePeriod, location);
    }
}