package is.yarr.qilletni.components.spotify;

import java.util.Optional;

/**
 * Stores data for playing from a Spotify search query.
 */
public class SpotifySearchData implements SpotifyCollectionData {

    private String searchData;
    private YearChooser yearChooser;
    private String genre;

    @Override
    public SpotifyCollectionType getCollectionType() {
        return SpotifyCollectionType.SEARCH;
    }

    @Override
    public boolean isInitialized() {
        return searchData != null;
    }

    @Override
    public boolean isShuffleSupported() {
        return false;
    }

    /**
     * Gets the data that is being searched.
     *
     * @return The data being searched
     */
    public String getSearchData() {
        return searchData;
    }

    /**
     * Sets the data being searched.
     *
     * @param searchData The data being searched
     */
    public void setSearchData(String searchData) {
        this.searchData = searchData;
    }

    /**
     * Gets, if set, the {@link YearChooser} to refine the search by songs' year.
     *
     * @return The {@link YearChooser}, if present
     */
    public Optional<YearChooser> getYearChooser() {
        return Optional.ofNullable(yearChooser);
    }

    /**
     * Sets the {@link YearChooser} to refine the search by songs' year. If nothing is set, this search property will
     * be ignored.
     *
     * @param yearChooser The {@link YearChooser}
     */
    public void setYearChooser(YearChooser yearChooser) {
        this.yearChooser = yearChooser;
    }

    /**
     * Clears any {@link YearChooser} set by {@link #setYearChooser(YearChooser)}, making {@link #getYearChooser()}
     * return an empty optional.
     */
    public void clearYearChooser() {
        this.yearChooser = null;
    }

    /**
     * Gets the genre to search songs by.
     *
     * @return The genre, if present
     */
    public Optional<String> getGenre() {
        return Optional.ofNullable(genre);
    }

    /**
     * Sets the genre to refine the search by the genre of songs. If nothing is set, this search property will be
     * ignored.
     *
     * @param genre The genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * Clears any genre set by {@link #setGenre(String)}, making {@link #getGenre()} return an empty optional.
     */
    public void clearGenre() {
        this.genre = null;
    }
}