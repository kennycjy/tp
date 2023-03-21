package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.regex.Pattern;

/**
 * Represents a ModuleTag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class ModuleTag extends Tag implements Comparable<ModuleTag> {

    public static final String MESSAGE_CONSTRAINTS =
            "NUS Modules should have 2 - 3 letter prefix, followed by 4 digits and optional 1 - 3 alphabets";
    public static final String VALIDATION_REGEX = "[A-Z]{2,4}[0-9]{4}[A-Z]{0,3}";

    private String day;
    private String startTime;
    private String endTime;
    private String location;
    private String latitude;
    private String longitude;


    /**
     * Constructs a {@code ModuleTag}.
     *
     * @param tagName A string for tag names.
     */
    public ModuleTag(String[] tagName) {
        super(tagName[0]);
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName[0]), MESSAGE_CONSTRAINTS);
        if (tagName.length == 4) {
            this.day = tagName[1];
            this.startTime = tagName[2];
            this.endTime = tagName[3];
            return;
        }
        // probably need to sanitize arguments somewhere either have it length 1 or length 4 etc
        this.day = null;
        this.startTime = null;
        this.endTime = null;
    }

    /**
     * Constructs a {@code ModuleTag}.
     * Old constructor to protect against other methods that use this call.
     * @param tagName A valid tag name.
     */
    public ModuleTag(String tagName) {
        super(tagName);
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.day = null;
        this.startTime = null;
        this.endTime = null;
    }

    @Override
    boolean isValidTagName(String test, String regex) {
        return Pattern.matches(VALIDATION_REGEX, test);
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ModuleTag // instanceof handles nulls
                && tagName.equals(((ModuleTag) other).tagName)); // state check
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    /**
     * Format state as text for viewing.
     */
    public String toString() {
        return tagName;
    }

    @Override
    public int compareTo(ModuleTag otherModuleTag) {
        return tagName.compareTo(otherModuleTag.tagName);
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getDay() {
        return day;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
