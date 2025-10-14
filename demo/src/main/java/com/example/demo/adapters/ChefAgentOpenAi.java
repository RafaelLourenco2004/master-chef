package com.example.demo.adapters;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Utils.JsonParsing;
import com.example.demo.model.Receipt;
import com.openai.client.OpenAIClient;
import com.openai.client.okhttp.OpenAIOkHttpClient;
import com.openai.models.ChatModel;
import com.openai.models.ResponseFormatJsonObject;
import com.openai.models.chat.completions.ChatCompletion;
import com.openai.models.chat.completions.ChatCompletionCreateParams;

@Service
public class ChefAgentOpenAi implements ChefAgent {

    private OpenAIClient client;

    public ChefAgentOpenAi() {
        this.client = OpenAIOkHttpClient.fromEnv();
    }

    @Override
    public List<Receipt> get_receipts(String prompt) throws Exception{
        ChatCompletionCreateParams params = ChatCompletionCreateParams.builder()
                .model(ChatModel.GPT_5_MINI)
                .addUserMessage(prompt)
                .responseFormat(ChatCompletionCreateParams.ResponseFormat
                        .ofJsonObject(ResponseFormatJsonObject.builder().build()))
                .build();

        ChatCompletion completion = client.chat().completions().create(params);
        String json = completion.choices().get(0).message().content().get();
        return JsonParsing.parseJson(json, Receipt.class);
    }

}
