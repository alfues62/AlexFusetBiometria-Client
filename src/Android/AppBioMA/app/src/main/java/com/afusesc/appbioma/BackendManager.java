package com.afusesc.appbioma;

import android.util.Log;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

public class BackendManager {

    private static final String ETIQUETA_LOG = ">>>>";
    private static final String URL_BASE = "http://192.168.1.59:8080"; // Cambia esta URL según tu servidor
    private OkHttpClient client;

    // Constructor
    public BackendManager() {
        this.client = new OkHttpClient();
    }

    /**
     * Método para enviar un número al backend.
     *
     * @param numero El número (minor) a enviar.
     */
    public void enviarNumeroAlBackend(int numero) {
        // Crear el cuerpo de la solicitud con el número en formato POST
        RequestBody formBody = new FormBody.Builder()
                .add("numero", String.valueOf(numero))
                .build();

        // Crear la solicitud POST
        Request request = new Request.Builder()
                .url(URL_BASE)
                .post(formBody)
                .build();

        // Ejecutar la solicitud en un hilo separado
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Manejo de error al enviar la solicitud
                Log.e(ETIQUETA_LOG, "Error al enviar el número al backend", e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Manejo de la respuesta del servidor
                if (!response.isSuccessful()) {
                    Log.e(ETIQUETA_LOG, "Error en la respuesta del servidor: " + response);
                } else {
                    Log.d(ETIQUETA_LOG, "Número enviado con éxito al backend");
                    // Aquí podrías procesar la respuesta si es necesario
                    String responseData = response.body().string();
                    Log.d(ETIQUETA_LOG, "Respuesta del servidor: " + responseData);
                }
            }
        });
    }
}
