package com.chatup.controllers.services.implementations;
import com.google.code.chatterbotapi.*;

public class ChatterBotService {
    private static ChatterBotService chatterBotService;

    public boolean botStatus=false;

    public static ChatterBotService getChatterBotService(){
        if (chatterBotService==null)
            chatterBotService = new ChatterBotService();
        return chatterBotService;
    }

    public String thinkBot(String input) {
        ChatterBot bot2 = null;
        try {
            ChatterBotFactory factory = new ChatterBotFactory();
            bot2 = factory.create(ChatterBotType.PANDORABOTS, "b0dafd24ee35a477");
            ChatterBotSession bot2session = bot2.createSession();
            return bot2session.think(input);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}