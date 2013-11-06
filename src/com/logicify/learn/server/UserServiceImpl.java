package com.logicify.learn.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.logicify.learn.client.UserService;
import com.logicify.learn.client.UserServiceException;
import com.logicify.learn.shared.GeneralResponse;
import com.logicify.learn.shared.User;
import com.logicify.learn.shared.UserList;

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

    private class Params {
        String url = null;
        String rawData = null;
        String contentType = null;
        String requestType = null;
    }

    @Override
    public GeneralResponse getUserList(String url) throws UserServiceException {
        Params params = new Params();
        params.url = url;
        params.requestType = "GET";
        params.contentType = "application/json";
        return makeRequestJSON(params, UserList.class);
    }

    @Override
    public GeneralResponse deleteUser(String url) throws UserServiceException {
        Params params = new Params();
        params.url = url;
        params.requestType = "DELETE";
        params.contentType = "application/json";
        return makeRequestJSON(params, String.class);
    }

    @Override
    public GeneralResponse updateUser(String url, String userRawData) throws UserServiceException {
        Params param = new Params();
        param.url = url;
        param.requestType = "PUT";
        param.rawData = userRawData;
        param.requestType = "application/x-www-form-urlencoded";
        return makeRequestJSON(param, String.class);
    }

    @Override
    public GeneralResponse saveUser(String url, String userRawData) throws UserServiceException {
        Params param = new Params();
        param.url = url;
        param.requestType = "POST";
        param.rawData = userRawData;
        param.requestType = "application/x-www-form-urlencoded";
        return makeRequestJSON(param, User.class);
    }

    /**
     * @return result of request
     * @throws UserServiceException
     */
    @SuppressWarnings("unused")
    private <T> GeneralResponse<T> makeRequestJSON(Params params, Class<T> type) throws UserServiceException {
        try {
            URL u = new URL(params.url);
            connection = (HttpURLConnection) u.openConnection();

            //config connection
            connection.setRequestMethod(params.requestType);
            connection.setRequestProperty("Content-Type", params.contentType);
            connection.setRequestProperty("charset", "utf-8");
            connection.setUseCaches(false);

            //send data
            if (params.rawData != null) {
                connection.setRequestProperty("Content-Length", "" + Integer.toString(params.rawData.getBytes().length));
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(params.rawData);
                writer.flush();
                writer.close();
            }

            //take response
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String responseLine;

            while ((responseLine = reader.readLine()) != null) {
                response.append(responseLine);
            }

            reader.close();

            // parse response as JSON object

            //define JSON parser
            ObjectMapper mapper = new ObjectMapper();

            //parse response
            GeneralResponse<T> parsedResponse = mapper.readValue(response.toString(),
                    new TypeReference<T>() { });

            //check if we have an error response code
            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {

                //take error server message
                throw new UserServiceException("Server code: "
                        + responseCode
                        + "\n Error Message: "
                        + parsedResponse.error);
            }


            return parsedResponse;
        } catch (MalformedURLException e) {
            throw new UserServiceException(e.getMessage());
        } catch (JsonMappingException e) {
            try {
                int responseCode = connection.getResponseCode();
                throw new UserServiceException("Server code: " + responseCode + "\n Error Message: " + e.getMessage());
            } catch (IOException e1) {
                throw new UserServiceException(e.getMessage());
            }
        } catch (IOException e) {
            throw new UserServiceException(e.getMessage());
        } finally {
            connection.disconnect();
        }
    }
}