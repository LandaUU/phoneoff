package com.example.phoneoff;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.security.cert.CertificateException;
import java.util.ArrayList;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public class DBManager {


    private interface API {
        @GET("/Phone/GetPhones")
        Call<String> getProducts();

        @POST("/Phone/Authentication")
        Call<Auth> Auth(@Body UserAuth user);

        @POST("/Phone/GetOrder")
        Call<ArrayList<Order>> GetOrder();
    }

    private static OkHttpClient.Builder getUnsafeOkHttpClient() {

        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());

            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return builder;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void GetProducts(ArrayList<Product> products) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .baseUrl("https://phoneoff.westeurope.cloudapp.azure.com")
                .client(getUnsafeOkHttpClient().build())
                .build();

        API api = retrofit.create(API.class);

        Call<String> call = api.getProducts();

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                try {
                    if (response.isSuccessful()) {

                        JSONArray array = new JSONArray(response.body());
                        Gson gs = new Gson();

                        for (int i = 0; i < array.length(); i++) {
                            String prodString = array.getString(i);
                            Product product = gs.fromJson(prodString, Product.class);
                            products.add(product);
                        }
                        Log.i("DBManager", "ArrayList заполнен!");
                    } else {
                        throw new Exception("response.isSuccesful is false");
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static Auth Auth(String Login, String Password) {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://phoneoff.westeurope.cloudapp.azure.com")
                .client(getUnsafeOkHttpClient().build())
                .build();

        API api = retrofit.create(API.class);

        Call<Auth> call = api.Auth(new UserAuth(Login, Password));

        final Auth[] result = {new Auth()};

        call.enqueue(new Callback<Auth>() {
            @Override
            public void onResponse(Call<Auth> call, Response<Auth> response) {
                try {
                    if (response.isSuccessful()) {
                        result[0] = response.body();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<Auth> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return result[0];
    }
}
