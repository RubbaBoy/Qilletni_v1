package is.yarr.qilletni.components.spotify;

import org.springframework.util.Assert;

/**
 * A class to contain either a single year or a range of two years, used for searching.
 */
public class YearChooser {

    private final boolean isRange;
    private final YearRange yearRange;
    private final Integer year;

    private YearChooser(boolean isRange, YearRange yearRange, Integer year) {
        this.isRange = isRange;
        this.yearRange = yearRange;
        this.year = year;
    }

    /**
     * Creates a {@link YearChooser} as a non-range, single year object.
     *
     * @param year The year this should identify with
     * @return The created {@link YearChooser}
     */
    public static YearChooser createFromYear(int year) {
        return new YearChooser(false, null, year);
    }

    /**
     * Creates a {@link YearChooser} as a ranged year object, containing a start and end year.
     *
     * @param startYear The start year
     * @param endYear The end year, which must be greater than or equal to the start year
     * @return The created {@link YearChooser}
     */
    public static YearChooser createFromRange(int startYear, int endYear) {
        return new YearChooser(true, new YearRange(startYear, endYear), null);
    }

    /**
     * Gets if this object represents a range, or a single year. If {@code true}, {@link #getRange()} can be invoked
     * to get the range. Otherwise, {@link #getYear()} can be invoked.
     *
     * @return If a range is present
     */
    public boolean isRange() {
        return isRange;
    }

    /**
     * Gets the {@link YearRange} this represents, assuming {@link #isRange()} returns {@code true}.
     *
     * @return The {@link YearRange}
     */
    public YearRange getRange() {
        Assert.isTrue(isRange, "Tried to get #getRange() on a non-range");
        return yearRange;
    }

    /**
     * Gets the year this represents, assuming {@link #isRange()} returns {@code false}.
     *
     * @return The single year
     */
    public Integer getYear() {
        Assert.isTrue(!isRange, "Tried to get #getYear() on a range");
        return year;
    }

    /**
     * Represents a range of years.
     *
     * @param startYear The starting year
     * @param endYear The ending year
     */
    public record YearRange(int startYear, int endYear) {
        public YearRange {
            Assert.isTrue(startYear <= endYear, "startYear must be less than or equal to endYear");
        }
    }
}
