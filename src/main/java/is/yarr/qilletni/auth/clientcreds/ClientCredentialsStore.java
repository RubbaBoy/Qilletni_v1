package is.yarr.qilletni.auth.clientcreds;

/**
 * Stores and manages global {@link ClientCredentials}.
 */
public interface ClientCredentialsStore {

    /**
     * Gets the internal {@link ClientCredentials}, refreshing if necessary.
     *
     * @return The valid client credentials
     */
    ClientCredentials getClientCredentials();

}
