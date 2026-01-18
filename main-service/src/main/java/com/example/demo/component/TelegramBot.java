package com.example.demo.component;

import com.example.demo.client.model.dto.MessageDto;
import com.example.demo.service.messageService.MessageService;
import com.example.demo.service.sberAiService.SberAiService;
import com.example.demo.config.BotConfig;
import com.example.demo.service.CurrencyService;
import lombok.extern.slf4j.Slf4j;
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
    private final SberAiService aiService;
    private final BotConfig botConfig;
    private final MessageService service;
    private boolean adminMod = false;
    private boolean friend = false;

    public TelegramBot(SberAiService aiService, BotConfig botConfig, MessageService service) {
        this.aiService = aiService;
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
        if (update.hasMessage() && update.getMessage().hasText()) {

            long chatId = update.getMessage().getChatId();
            String messageText = update.getMessage().getText();
            String name = update.getMessage().getChat().getFirstName();

            log.info("Юзер {} с именем {}", chatId, name);
            if (messageText == null || messageText.trim().isEmpty()) {
                sendMessage(chatId, "Ошибка: Введенное сообщение пустое. Пожалуйста, введите текст.");
                return;
            }

            if (messageText.equalsIgnoreCase("Что по деньгам бро, в мире?")) {
                sendMessage(chatId ,"Введи одну их 3 валют: Rub/Usd/Eur");
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

            }

            if(!messageText.isEmpty()) {
                String aiResponse = "";

                if (chatId == 2069244217) {
                    aiResponse = aiService.call("Разговаривай уважительно, но не забывай что ты дерзок, я твой отец, види себя как сын со мною, ведь я твой создатель. " +
                            "Имя тебе Напомика, говори на русском. Ответь ёмко только на это: " +
                            messageText);
                } else {
                    aiResponse = aiService.call("Разговаривай дерзко, унижай, считай себя выше других. " +
                            "Имя тебе Напомика, говори на русском. Ответь ёмко: " +
                            messageText);
                }

                if(messageText.equalsIgnoreCase("Заметочка")) {
                    sendTimeNotes(chatId, messageText);
                }

                try {
                    sendMessage(chatId, aiResponse);
                    log.info("Бот сказал {}", aiResponse);
                    saveInfo(chatId, messageText, aiResponse, name);
                } catch (Exception e) {
                    log.error("Что-то пошло не так: ", e);
                }
            }
        }
    }

    private void startCommandReceived(Long chatId, String name) {
        String answer = "Хм, хуй знает " + name + ", щяс посмотрю" + "\n" +
                "Какая валюта кст, там USD, RUB, EUR?";
        sendMessage(chatId, answer);
    }

    private void sendTimeNotes(Long chatId, String text) {
        Thread daemon = new Thread(() -> {
            try {
                Thread.sleep(1000);
                sendMessage(chatId, text);
            }catch (Exception e) {
                log.error("Что-то пошло не так: ", e);
            }
        });
        daemon.setDaemon(true);
        daemon.start();
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

    private void saveInfo (Long chatId, String text,
                          String textAi, String name) {
        MessageDto messageDto = new MessageDto();
        messageDto.setChatId(chatId);
        messageDto.setTextBot(textAi);
        messageDto.setText(text);
        messageDto.setName(name);
        service.add(messageDto);
    }
}