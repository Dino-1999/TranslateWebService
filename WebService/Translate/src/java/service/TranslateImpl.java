/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dictionary.Dictionary;
import dictionary.Word;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebService;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

@WebService(endpointInterface = "service.Translate")
public class TranslateImpl implements Translate {
    WordObj wordObj= new WordObj();
    @Override
    public String translate(String word, String fLanguage, String lLanguage) {
        try {
            wordObj.setWord(word);
            wordObj.setfLanguage(fLanguage);
            wordObj.setlLanguage(lLanguage);
            JAXBContext jax=JAXBContext.newInstance("dictionary");
            DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
            DocumentBuilder db=dbf.newDocumentBuilder();
            Document doc=db.parse("Dictionary.xml");
            Unmarshaller unmarshaller=jax.createUnmarshaller();
            Dictionary dictionary = (Dictionary) unmarshaller.unmarshal(new File("Dictionary.xml"));
            List<Word> words=dictionary.getWord();
            if (fLanguage.toLowerCase().equals("english")) {
            for (Word word1 : words) {
                String eng = word1.getEnglishWord();
                word = word.toLowerCase();
                if(eng.equals(word)){
                    String cap=word1.getGermanWord().substring(0, 1).toUpperCase();
                    String res=word1.getGermanWord().substring(1);
                    wordObj.setResult(cap+res);
                    System.out.println("Translation: "+wordObj.getResult());
                    return wordObj.getResult();
                }   
            }
        }else if(fLanguage.toLowerCase().equals("german")){
            for (Word word1 : words){
                String ger=word1.getGermanWord();
                word=word.toLowerCase();
                if(ger.equals(word)){
                    String cap=word1.getEnglishWord().substring(0, 1).toUpperCase();
                    String res=word1.getEnglishWord().substring(1);
                    wordObj.setResult(cap+res);
                    System.out.println("Translation: " + wordObj.getResult());
                    return wordObj.getResult();
                }
            }
        }
        } catch (JAXBException | SAXException | IOException | ParserConfigurationException ex) {
            Logger.getLogger(TranslateImpl.class.getName()).log(Level.SEVERE, null, ex);
            return ex.toString();
        }
        return wordObj.getResult();
    }
}
