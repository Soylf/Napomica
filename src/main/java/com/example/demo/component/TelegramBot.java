package com.example.demo.component;


import com.example.demo.config.BotConfig;
import com.example.demo.service.CurrencyService;
import lombok.AllArgsConstructor;
import org.springframework.ai.vertexai.palm2.VertexAiPaLm2ChatModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final VertexAiPaLm2ChatModel chatModel;
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            if (messageText.equalsIgnoreCase("Что по деньгам бро, в мире?")) {
                startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
            } else if (messageText.equalsIgnoreCase("Rub") || messageText.equalsIgnoreCase("Usd")
                    || messageText.equalsIgnoreCase("Eur")) {

                String response = null;
                try {
                    response = CurrencyService.getCurrencyRate(messageText);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException("Ай бл, что происходит мне больно, сука уебок, что ты сделал!!!");
                }
                sendMessage(chatId, response);
            } else {
                sendMessage(chatId, chatModel.call(messageText));
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Хм, хуй знает " + name + ", щяс посмотрю" + "\n" +
                "Какая валюта кст, там USD, RUB, EUR?";
        sendMessage(chatId, answer);
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        try {
            execute(sendMessage);
        } catch (TelegramApiException ignored) {

        }
    }
}