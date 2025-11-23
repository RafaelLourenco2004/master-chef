package com.example.demo.adapters.agents;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.Utils.JsonSchema;
import com.example.demo.model.exceptions.InvalidApiResponseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

@Service
public class ChefAgent implements Agent {

    private String token;
    private String url;
   
    private OkHttpClient client;

    public ChefAgent() {
        this(System.getenv("TOKEN"), System.getenv("URL"), new OkHttpClient());
    }

    public ChefAgent(String token, String url, OkHttpClient client) {
        this.token = token;
        this.url = url;
        this.client = client;
    }

    @Override
    public String get_receipts(String prompt) throws Exception {
        Map<String, Object> body = Map.of(
                "model", "openai/gpt-4.1-nano",
                "response_format", Map.of(
                        "type", "json_schema",
                        "json_schema", JsonSchema.getRecipetSchema()),
                "messages", List.of(
                        Map.of(
                                "role", "user",
                                "content", prompt)));

        ObjectMapper mapper = new ObjectMapper();
        String requestBody = mapper.writeValueAsString(body);

        Request request = new Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer " + token)
            .addHeader("Content-Type", "application/json")
            .post(RequestBody.create(MediaType.parse("application/json"), requestBody))
            .build();

        Response response = client.newCall(request).execute();

        if (!response.isSuccessful()){
            throw new InvalidApiResponseException("Não foi possivel gerar uma resposta valida;");
        }

        return response.body().string();
    }

}