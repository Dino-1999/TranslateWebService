
package service;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface Translate {
    
    @WebMethod
    public String translate(String word,String fLanguage,String lLanguage,String result);
}
