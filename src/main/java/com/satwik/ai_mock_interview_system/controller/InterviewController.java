package com.satwik.ai_mock_interview_system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class InterviewController {

    @GetMapping("/interview")
    public String interviewQuestion(@RequestParam String role) throws Exception {

        String apiKey = "";

        String prompt = "Generate one interview question for a " + role;

        String endpoint =
                "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                        + apiKey;

        URL url = new URL(endpoint);

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setDoOutput(true);

        String jsonInput =
                "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + "\" }] }] }";

        try(OutputStream os = conn.getOutputStream()) {

            byte[] input = jsonInput.getBytes("utf-8");

            os.write(input, 0, input.length);
        }

        BufferedReader br = new BufferedReader(
                new InputStreamReader(conn.getInputStream(), "utf-8"));

        StringBuilder response = new StringBuilder();

        String responseLine;

        while ((responseLine = br.readLine()) != null) {

            response.append(responseLine.trim());
        }

        return response.toString();
    }
}