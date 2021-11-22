/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.gunet.usernamegenerator;
import gr.gunet.usernamegenerator.tools.CustomJsonReader;
import java.io.IOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;

/**
 *
 * @author mpkos
 */
public class Main {
    public static void main(String[] args) throws Exception {
        try (CustomJsonReader jsonReader = new CustomJsonReader("requests/request3.json")){
            UserNameGen userNameGen= new UserNameGen(jsonReader);
            // String prefix = jsonReader.readPropertyAsString("prefix");
            // if(prefix == null || prefix.trim().equals("")){
            //     System.out.println("No prefix provided");
            // }
            // else{
            //     System.out.println(prefix);
            // }
        }
        catch(IOException e){
            e.printStackTrace();
        }
            

    }
}
