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

    public static YearChooser createFromYear(int year) {
        return new YearChooser(false, null, year);
    }

    public static YearChooser createFromRange(int startYear, int endYear) {
        return new YearChooser(true, new YearRange(startYear, endYear), null);
    }

    public boolean isRange() {
        return isRange;
    }

    public YearRange getRange() {
        Assert.isTrue(isRange, "Tried to get #getRange() on a non-range");
        return yearRange;
    }

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
