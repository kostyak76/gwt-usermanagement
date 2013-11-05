package com.logicify.learn.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.logicify.learn.client.UserService;
import com.logicify.learn.client.UserServiceException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: kostyak
 * Date: 11/1/13
 * Time: 5:06 PM
 */
public class UserServiceImpl extends RemoteServiceServlet implements UserService {
    HttpURLConnection connection;

    @Override
    public String getUserList(String url) throws UserServiceException {
        return makeSimpleRequest("GET", url);
    }

    @Override
    public String deleteUser(String url) throws UserServiceException {
        return makeSimpleRequest("DELETE", url);
    }

    @Override
    public String updateUser(String url, String userRawData) throws UserServiceException {
        return makeRequest("PUT", url, userRawData);
    }

    @Override
    public String saveUser(String url, String userRawData) throws UserServiceException {
        return makeRequest("POST", url, userRawData);
    }

    /**
     * expect POST, PUT
     *
     * @param requestType  type of request (POST, PUT)
     * @param url  url string
     * @param rawData  data in encoded view
     * @return  result of request
     * @throws UserServiceException
     */
    private String makeRequest(String requestType, String url, String rawData) throws UserServiceException{
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();

            //config connection
            //connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("charset", "utf-8");
            connection.setRequestProperty("Content-Length", "" + Integer.toString(rawData.getBytes().length));
            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //send data
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            writer.write(rawData);
            writer.flush();
            writer.close();

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                //take message from server
                //try to get error message
                throw new UserServiceException("Error in server response" + responseCode);
            }

            //take response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine);
            }

            reader.close();

            return response.toString();
        } catch (MalformedURLException e) {
            throw new UserServiceException(e.getMessage());
        } catch (IOException e) {
            throw new UserServiceException(e.getMessage());
        } finally {
            connection.disconnect();
        }

    }

    /**
     * expect GET, DELETE
     *
     * @param requestType type of the request
     * @param url         url of the request
     * @return
     */
    private String makeSimpleRequest(String requestType, String url) throws UserServiceException {
        try {
            URL u = new URL(url);
            connection = (HttpURLConnection) u.openConnection();

            //config connection
            connection.setRequestProperty("Content-type", "application/json");
            connection.setRequestMethod(requestType);

            //get data
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                //take message from server
                //try to get error message
                throw new UserServiceException("Error in server response" + responseCode);
            }

            //successfully
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            return (response.toString());

        } catch (MalformedURLException e) {
            throw new UserServiceException(e.getMessage());
        } catch (IOException e) {
            throw new UserServiceException(e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
}