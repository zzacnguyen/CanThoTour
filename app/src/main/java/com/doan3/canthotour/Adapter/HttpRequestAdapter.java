package com.doan3.canthotour.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.apache.http.entity.mime.MultipartEntity;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String httpPostImage(String urlString, MultipartEntity reqEntity) {
        try {
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("POST");
            urlConnection.setUseCaches(false);
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.addRequestProperty("Content-length", reqEntity.getContentLength() + "");
            urlConnection.addRequestProperty(reqEntity.getContentType().getName(), reqEntity.getContentType().getValue());

            OutputStream os = urlConnection.getOutputStream();
            reqEntity.writeTo(urlConnection.getOutputStream());
            os.close();
            urlConnection.connect();

            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();

                return result.toString();
            } else {
                System.out.println(urlConnection.getResponseCode());
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
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) { //success
                BufferedReader in = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = in.readLine()) != null) {
                    result.append(line);
                }
                in.close();

                return result.toString();
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