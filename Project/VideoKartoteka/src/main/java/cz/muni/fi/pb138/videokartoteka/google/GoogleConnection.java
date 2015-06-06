
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.videokartoteka.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents unique connection to Google account
 * 
 * @author matus
 */
public class GoogleConnection {

    // Application specific conection data
    private static final String CLIENT_ID = "200863197931.apps.googleusercontent.com";
    private static final String CLIENT_SECRET = "aKKa4YGHM-YsJkZvzpxpE8wM";
    private static final String REDIRECT_URI= "urn:ietf:wg:oauth:2.0:oob";
    // private fields
    private final HttpTransport httpTransport;
    private final JsonFactory jsonFactory;
    private final GoogleAuthorizationCodeFlow flow;
    private final String url;
    private GoogleTokenResponse response;
    private GoogleCredential credential;
    private Drive service;
    private boolean connected;

    public GoogleConnection() {
        httpTransport = new NetHttpTransport();
        jsonFactory = new JacksonFactory();

        flow = new GoogleAuthorizationCodeFlow.Builder(
                httpTransport, jsonFactory, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE))
                .setAccessType("online")
                .setApprovalPrompt("auto").build();

        url = flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();

        connected = false;
    }

    /**
     * Returns URL to OAuth 2 authorization service
     * 
     * @return URL to OAuth 2 authorization service
     */
    public String getAuthorizationUrl() {
        return this.url;
    }

    /**
     * Returns whether the connection to Google account was established.
     * 
     * @return true if connection is established. false otherwise.
     */
    public boolean isConnected() {
        return this.connected;
    }

    /**
     * Tries to establish a connection to Google account specified by given code.
     * 
     * @param code authorization code.
     * @return true if successfully connected. false otherwise
     */
    public boolean connect(String code) {
        boolean result = true;

        try {
            response = flow.newTokenRequest(code).setRedirectUri(REDIRECT_URI).execute();
            credential = new GoogleCredential().setFromTokenResponse(response);
            this.connected = true;
        } catch (com.google.api.client.auth.oauth2.TokenResponseException ex) {
            Logger.getLogger(GoogleConnection.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        } catch (IOException ex) {
            Logger.getLogger(GoogleConnection.class.getName()).log(Level.SEVERE, null, ex);
            result = false;
        }

        return result;
    }

    /**
     * Builds instance of GoogleDriveService associated with this connection.
     * 
     * @return GoogleDriveService instance.
     */
    public GoogleDriveService buildService() {
        if (!connected || credential == null) {
            return null;
        }
        if (service == null) {
            service = new Drive.Builder(httpTransport, jsonFactory, credential)
                    .setApplicationName("Videokartoteka")
                    .build();
        }

        return new GoogleDriveService(this, service);
    }
    
    /**
     * Refreshes authorization token, after it expires.
     * 
     * @return true if authorization token was successfully refreshed. false otherwise.
     */
    public boolean refresh() {
        boolean result = false;
        if (credential != null) {
            try {
                credential.refreshToken();
                result = true;
            } catch (IOException ex) {
                Logger.getLogger(GoogleConnection.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return result;
    }

    public void close() {
        this.connected = false;
    }
}