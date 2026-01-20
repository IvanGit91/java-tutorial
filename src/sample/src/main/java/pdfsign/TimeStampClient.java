package pdfsign;

import org.bouncycastle.tsp.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Client for requesting timestamps from a Time Stamping Authority (TSA).
 * Implements RFC 3161 Time-Stamp Protocol over HTTP.
 *
 * <p>Supports both authenticated and anonymous TSA access.
 *
 * <p>Example usage:
 * <pre>
 * TimeStampClient client = new TimeStampClient("https://freetsa.org/tsr", null, null);
 * byte[] digest = MessageDigest.getInstance("SHA-256").digest(data);
 * byte[] timestamp = client.stamp(digest);
 * </pre>
 */
public class TimeStampClient {

    private static final String CONTENT_TYPE_TIMESTAMP_QUERY = "application/timestamp-query";
    private static final int DEFAULT_TIMEOUT_MS = 30000;

    private final String tsaUrl;
    private final String username;
    private final String password;
    private final int timeoutMs;

    /**
     * Creates a TimeStampClient with default timeout.
     *
     * @param tsaUrl   the URL of the Time Stamping Authority
     * @param username username for Basic authentication (null for anonymous)
     * @param password password for Basic authentication (null for anonymous)
     */
    public TimeStampClient(String tsaUrl, String username, String password) {
        this(tsaUrl, username, password, DEFAULT_TIMEOUT_MS);
    }

    /**
     * Creates a TimeStampClient with custom timeout.
     *
     * @param tsaUrl    the URL of the Time Stamping Authority
     * @param username  username for Basic authentication (null for anonymous)
     * @param password  password for Basic authentication (null for anonymous)
     * @param timeoutMs connection and read timeout in milliseconds
     */
    public TimeStampClient(String tsaUrl, String username, String password, int timeoutMs) {
        if (tsaUrl == null || tsaUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("TSA URL cannot be null or empty");
        }
        this.tsaUrl = tsaUrl;
        this.username = username;
        this.password = password;
        this.timeoutMs = timeoutMs;
    }

    /**
     * Requests a timestamp for the given digest.
     *
     * @param digest the SHA-256 hash to timestamp
     * @return the encoded timestamp token bytes
     * @throws TSPException if the TSA returns an error or invalid response
     * @throws IOException  if network communication fails
     */
    public byte[] stamp(byte[] digest) throws Exception {
        if (digest == null || digest.length == 0) {
            throw new IllegalArgumentException("Digest cannot be null or empty");
        }

        TimeStampRequestGenerator requestGenerator = new TimeStampRequestGenerator();
        requestGenerator.setCertReq(true);

        BigInteger nonce = BigInteger.valueOf(System.currentTimeMillis());
        TimeStampRequest request = requestGenerator.generate(TSPAlgorithms.SHA256, digest, nonce);
        byte[] requestBytes = request.getEncoded();

        byte[] responseBytes = sendRequest(requestBytes);

        TimeStampResponse response = new TimeStampResponse(responseBytes);
        response.validate(request);

        TimeStampToken token = response.getTimeStampToken();
        if (token == null) {
            String status = response.getStatusString();
            throw new TSPException("No timestamp token in response. Status: " +
                    (status != null ? status : "unknown"));
        }

        return token.getEncoded();
    }

    /**
     * Sends the timestamp request to the TSA.
     *
     * @param requestBytes the encoded timestamp request
     * @return the TSA response bytes
     * @throws IOException if network communication fails
     */
    private byte[] sendRequest(byte[] requestBytes) throws IOException {
        URL url = new URL(tsaUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", CONTENT_TYPE_TIMESTAMP_QUERY);
            connection.setRequestProperty("Content-Length", String.valueOf(requestBytes.length));
            connection.setConnectTimeout(timeoutMs);
            connection.setReadTimeout(timeoutMs);

            // Add Basic authentication if credentials provided
            if (username != null && password != null) {
                String auth = username + ":" + password;
                String encoded = Base64.getEncoder().encodeToString(auth.getBytes(StandardCharsets.UTF_8));
                connection.setRequestProperty("Authorization", "Basic " + encoded);
            }

            // Send request
            try (OutputStream out = connection.getOutputStream()) {
                out.write(requestBytes);
                out.flush();
            }

            // Check response code
            int responseCode = connection.getResponseCode();
            if (responseCode != HttpURLConnection.HTTP_OK) {
                throw new IOException("TSA returned HTTP " + responseCode + ": " + connection.getResponseMessage());
            }

            // Read response
            try (InputStream in = connection.getInputStream()) {
                return in.readAllBytes();
            }
        } finally {
            connection.disconnect();
        }
    }

    /**
     * Returns the TSA URL.
     */
    public String getTsaUrl() {
        return tsaUrl;
    }
}
