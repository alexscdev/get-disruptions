package com.example.demo.utilities;

import com.deepl.api.TextResult;
import com.deepl.api.Translator;
import com.example.demo.config.SubscriptionKey;


public class Translate {
	
	static Translator translator;
	static String subscriptionKey = SubscriptionKey.DeeplSubscriptionKey;

    public static String translate(String texto) {
        
        try {
            String authKey = subscriptionKey; 
            translator = new Translator(authKey);
            TextResult result = translator.translateText(texto, "nl", "en-GB");
            //System.out.println(result.getText());
            return result.getText();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
    }
}
