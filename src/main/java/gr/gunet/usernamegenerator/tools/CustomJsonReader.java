package gr.gunet.usernamegenerator.tools;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.io.FileReader;

public class CustomJsonReader implements AutoCloseable {
    private JsonObject rootObject;
    private final JsonParser parser;
    
    public CustomJsonReader(String path) throws JsonSyntaxException, java.io.IOException{
        FileReader reader = new FileReader(path);
        Object obj;
        parser = new JsonParser();
        obj = parser.parse(reader);
        rootObject = (JsonObject) obj;
    }
    
    private String formatedString(String unformatedString){
        String retVal;
        if(unformatedString == null){
            return null;
        }else{
            retVal = unformatedString.trim();
        }
        if(retVal.equals("")){
            return null;
        }else{
            return retVal;
        }
    }

    public String readPropertyAsString(String propertyName, JsonObject object){
        JsonObject temp= rootObject;
        rootObject=object;
        String returns="";
        returns=readPropertyAsString(propertyName);
        rootObject=temp;
        return returns;
    }

    public JsonObject readJsonObject(String propertyName, JsonObject object){
        JsonObject temp= rootObject;
        JsonObject returns=null;
        rootObject=object;
        returns=readJsonObject(propertyName);
        rootObject=temp;
        return returns;
    }

    public String readPropertyAsStringRaw(String propertyName){
        JsonElement element = rootObject.get(propertyName);
        if(element == null || JsonNull.INSTANCE.equals(element)){
            return null;
        }else if(element.isJsonArray()){
            JsonArray array = element.getAsJsonArray();
            if(array != null){
                return formatedString(array.toString().replaceAll("\"", ""));
                
            }else{
                return null;
            }
        }else if(element.isJsonObject()){
            String temp = element.toString();
            if(temp.equals("{}")){
                return null;
            }else{
                return temp;
            }
        }else{
            return element.getAsString();
        }
    }


    
    public String readPropertyAsString(String propertyName){
        return formatedString(readPropertyAsStringRaw(propertyName));
    }
    
    public String[] readPropertyAsStringArray(String propertyName) {
        JsonElement element = rootObject.get(propertyName);
        if(element == null || JsonNull.INSTANCE.equals(element)){
            return null;
        }else if(element.isJsonArray()){
            JsonArray array = element.getAsJsonArray();
            if(array != null){
                Iterator<JsonElement> arrayIter = array.iterator();
                String[] strArray = new String[array.size()];
                int i = 0;
                while(arrayIter.hasNext()){
                    strArray[i] = arrayIter.next().getAsString();
                    i++;
                }
                return strArray;
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    public JsonObject readJsonObject(String propertyName){
        JsonElement element = rootObject.get(propertyName);
        JsonObject object=null;
        if(element == null || JsonNull.INSTANCE.equals(element)){
            return null;
        }else if(element.isJsonObject()){
            object= element.getAsJsonObject();
            if (object!=null) return object;
            else return null;
        }
        return object;
    }
        
    public List<JsonObject> readJsonArrayAsObjectList(){
        List<JsonObject> retVal = new LinkedList();
        if(rootObject.isJsonArray()){
            JsonArray array = rootObject.getAsJsonArray();
            if(array != null){
                for(JsonElement arrayElem : array){
                    if(arrayElem.isJsonObject()){
                        retVal.add(arrayElem.getAsJsonObject());
                    }else{
                        return null;
                    }
                }
            }else{
                return null;
            }
        }else{
            retVal.add(rootObject);
        }
        return retVal;
    }
     public void close() throws Exception {
        System.out.println("Closing!");
    }
}
