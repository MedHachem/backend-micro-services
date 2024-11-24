package com.islam.quranmanager.helper;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import javax.sound.sampled.*;
import java.io.InputStream;

public class AudioStreamPlayer {
    private final OkHttpClient client;

    public AudioStreamPlayer() {
        this.client = new OkHttpClient();
    }
    public void playAudioFromStream(String audioUrl) {
        Request request = new Request.Builder()
                .url(audioUrl)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                try (InputStream audioStream = response.body().byteStream()) {
                    // Lire l'audio depuis le flux
                    playAudio(audioStream);
                }
            } else {
                System.err.println("Erreur lors de la récupération de l'audio : " + response.code());
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de la lecture de l'audio : " + e.getMessage());
        }
    }

    private void playAudio(InputStream inputStream) throws Exception {
        // Charger l'audio depuis le flux
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(inputStream);
        AudioFormat format = audioInputStream.getFormat();

        // Configurer la ligne de sortie
        DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
        SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

        audioLine.open(format);
        audioLine.start();

        // Lire et diffuser les données audio
        byte[] buffer = new byte[4096];
        int bytesRead;
        while ((bytesRead = audioInputStream.read(buffer)) != -1) {
            audioLine.write(buffer, 0, bytesRead);
        }

        // Fermer la ligne après la lecture
        audioLine.drain();
        audioLine.close();
    }
}
