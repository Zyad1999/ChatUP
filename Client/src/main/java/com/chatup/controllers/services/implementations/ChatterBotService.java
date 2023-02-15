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
            String response = bot2session.think(input);
            if(response==""){
                response = "Sorry, I don't have answer to that";
            }
            return response;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}