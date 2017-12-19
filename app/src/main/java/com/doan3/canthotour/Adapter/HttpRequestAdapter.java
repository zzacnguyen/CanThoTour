package com.doan3.canthotour.Adapter;

import android.os.Environment;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by zzacn on 11/14/2017.
 */

public class HttpRequestAdapter {
    private static HttpURLConnection urlConnection;

    public static String httpGet(String url) {
        StringBuilder result = new StringBuilder();
        try {
            URL obj = new URL(url);
            urlConnection = (HttpURLConnection) obj.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestMethod("GET");
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return result.toString();
    }

    public static String httpGetImage(String url, String folderName, String fileName) {
        String filePath = null;
        try {
            URL con = new URL(url);
            HttpURLConnection urlConnection = (HttpURLConnection) con.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            File SDCardRoot = new File(Environment.getExternalStorageDirectory()
                    + "/" + folderName);
            if (SDCardRoot.exists()) {
                SDCardRoot.mkdirs();
            }
            File file = new File(SDCardRoot, fileName);

            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            int totalSize = urlConnection.getContentLength();
            int downloadedSize = 0;
            byte[] buffer = new byte[inputStream.available()];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
            }
            fileOutput.close();
            if (downloadedSize == totalSize)
                filePath = file.getPath();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            filePath = null;
            e.printStackTrace();
        }
        return filePath;
    }

    public static String httpPost(String url, JSONObject json) {
        try {
            URL obj = new URL(url);
            urlConnection = (HttpURLConnection) obj.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("POST");
            DataOutputStream dataOutputStream = new DataOutputStream(urlConnection.getOutputStream());
            dataOutputStream.writeBytes(json.toString());
            dataOutputStream.flush();
            dataOutputStream.close();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
    }

    public static String httpPut(String url, JSONObject json) {
        try {
            URL obj = new URL(url);
            urlConnection = (HttpURLConnection) obj.openConnection();
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestMethod("PUT");
            OutputStreamWriter streamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
            streamWriter.write(json.toString());
            streamWriter.flush();
            streamWriter.close();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String httpDelete(String url) {
        try {
            URL obj = new URL(url);
            urlConnection = (HttpURLConnection) obj.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestMethod("DELETE");
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return "success";
            } else {
                return "failure";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        } finally {
            urlConnection.disconnect();
        }
    }
}