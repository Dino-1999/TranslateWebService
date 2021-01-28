package service;

import dictionary.Dictionary;
import dictionary.Word;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Scanner;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.ws.Service;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class Main {

    public static void main(String[] args) throws MalformedURLException, JAXBException, ParserConfigurationException, SAXException, IOException {
        JAXBContext jx = JAXBContext.newInstance("dictionary");
        Unmarshaller unmarshaller = jx.createUnmarshaller();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse("Dictionary.xml");
        Dictionary dictionary = (Dictionary) unmarshaller.unmarshal(new File("Dictionary.xml"));
        Scanner scanner = new Scanner(System.in);
        List<Word> words = dictionary.getWord();
        URL url = new URL("http://localhost:8080//Translate/TranslateImplService?wsdl");
        QName qname = new QName("http://service/", "TranslateImplService");
        Service service = Service.create(url, qname);
        QName port = new QName("http://service/", "TranslateImplPort");
        Translate translate = service.getPort(port, Translate.class);
        System.out.println("Enter a family member in german or english:");
        String word = scanner.next();
        System.out.println("Enter translate from language:");
        String fLanguage=scanner.next();
        System.out.println("Enter translate to language:");
        String lLanguage = scanner.next();
        String result=null;
        if (fLanguage.toLowerCase().equals("english")) {
            for (Word word1 : words) {
                String eng = word1.getEnglishWord();
                word = word.toLowerCase();
                if(eng.equals(word)){
                    String cap=word1.getGermanWord().substring(0, 1).toUpperCase();
                    String res=word1.getGermanWord().substring(1);
                    result= cap+res;
                    System.out.println("Translation: "+cap+res);
                }   
            }
        }else if(fLanguage.toLowerCase().equals("german")){
            for (Word word1 : words){
                String ger=word1.getGermanWord();
                word=word.toLowerCase();
                if(ger.equals(word)){
                    String cap=word1.getEnglishWord().substring(0, 1).toUpperCase();
                    String res=word1.getEnglishWord().substring(1);
                    result=cap+res;
                    System.out.println("Translation: " + cap + res);
                }
            }
        }
    }

}
