package com.example.demo.teleBot.component;

import com.example.demo.client.dto.MessageDto;
import com.example.demo.service.saysInBotService.MessageService;
import com.example.demo.teleBot.config.BotConfig;
import com.example.demo.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.zhipuai.ZhiPuAiChatModel;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.text.ParseException;

@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {
    private final ZhiPuAiChatModel chatModel;
    private final BotConfig botConfig;
    private final MessageService service;
    private boolean adminMod = false;

    public TelegramBot(ZhiPuAiChatModel chatModel, BotConfig botConfig, MessageService service) {
        this.chatModel = chatModel;
        this.botConfig = botConfig;
        this.service = service;
    }

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
        long chatId = update.getMessage().getChatId();
        log.info("Пользователь подключился к базе {}", chatId);
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String name = update.getMessage().getChat().getFirstName();

            log.info("Юзер {} задал: {}", chatId,messageText);
            if (messageText == null || messageText.trim().isEmpty()) {
                sendMessage(chatId, "Ошибка: Введенное сообщение пустое. Пожалуйста, введите текст.");
                return;
            }

            if (messageText.equalsIgnoreCase("Что по деньгам бро, в мире?")) {
                startCommandReceived(chatId, name);
            } else if (messageText.equalsIgnoreCase("Rub") || messageText.equalsIgnoreCase("Usd")
                    || messageText.equalsIgnoreCase("Eur")) {

                String response;
                try {
                    response = CurrencyService.getCurrencyRate(messageText);
                } catch (IOException | ParseException e) {
                    log.error("Error while fetching currency rate: ", e);
                    sendMessage(chatId, "Произошла ошибка при получении курса валют. Пожалуйста, попробуйте снова.");
                    return;
                }
                sendMessage(chatId, response);
            } else {
                if (messageText.equalsIgnoreCase("labotaNapomica28762876") || adminMod) {
                    sendMessage(chatId, "Тест, повелитель");
                    if(!adminMod) {
                        adminMod = true;
                    }
                } else {
                    try {
                        String aiResponse = chatModel.call("Разговаривай дерзко, унижай, матюкайся, часто говори не по делу, считай себя выше других " +
                                "тебя зовут напомика, но изменяй свое имя по смыслу " +
                                "ответ только на русском языке. \n" +
                                messageText);
                        sendMessage(chatId, aiResponse);
                        //saveInfo(chatId, messageText,aiResponse, name);
                        log.info("Напомика сказала:\n{}",aiResponse );
                    } catch (Exception e) {
                        log.error("Error while calling AI model: ", e);
                        sendMessage(chatId, "Ай бл, че ты сделал?");
                    }
                }
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

    private void saveInfo (long chatId, String text,
                          String textAi, String name) {
        MessageDto says = new MessageDto();
        says.setChatId(chatId);
        says.setTextBot(textAi);
        says.setText(text);
        says.setName(name);
        service.update(says);
    }
}