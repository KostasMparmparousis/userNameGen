/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.gunet.usernamegenerator;
import gr.gunet.usernamegenerator.tools.CustomJsonReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
/**
 *
 * @author mpkos
 */
public class UserNameGen {
    private CustomJsonReader jsonReader;
    private String firstName;
    private int FNchars=0;
    private String FNtakeCharsFrom;
    private double FNpercentageOfName=0.0;
    private boolean FNcaseSensitive=true;
    private String FNplacement;

    private String lastName;
    private int LNchars=0;
    private String LNtakeCharsFrom;
    private double LNpercentageOfName=0.0;
    private boolean LNcaseSensitive=true;
    private String LNplacement;

    private String prefix;
    private String academicID;
    private int upperAmountOfNamesSuggested=10;
    private String orderBy;
    private String order;
    private String[] separators;
    private String[] prioritizeBy;
    private int lowerLimit=0;
    private int upperLimit=0;


    public UserNameGen(CustomJsonReader JsonReader){
        jsonReader=JsonReader;
        prefix = jsonReader.readPropertyAsString("prefix");
        academicID = jsonReader.readPropertyAsString("academicID");
        if (jsonReader.readPropertyAsString("upperAmountOfNamesSuggested")!=null)
            upperAmountOfNamesSuggested = Integer.parseInt(jsonReader.readPropertyAsString("upperAmountOfNamesSuggested"));
        orderBy = jsonReader.readPropertyAsString("orderBy");
        order = jsonReader.readPropertyAsString("order");
        separators= jsonReader.readPropertyAsStringArray("separators");
        prioritizeBy= jsonReader.readPropertyAsStringArray("prioritizeBy");

        JsonObject jsonObject= jsonReader.readJsonObject("firstName");
        firstName = jsonReader.readPropertyAsString("name", jsonObject);
        JsonObject FNParameters= jsonReader.readJsonObject("usePart", jsonObject);
        if (FNParameters!=null){
            if (jsonReader.readPropertyAsString("amountOfChars", FNParameters)!=null)
                FNchars = Integer.parseInt(jsonReader.readPropertyAsString("amountOfChars", FNParameters));

            FNtakeCharsFrom = jsonReader.readPropertyAsString("takeCharsFrom", FNParameters);
            if (jsonReader.readPropertyAsString("percentageOfName", FNParameters)!=null)
                FNpercentageOfName = Double.parseDouble(jsonReader.readPropertyAsString("percentageOfName", FNParameters));

            if (jsonReader.readPropertyAsString("caseSensitive", FNParameters)!=null)
                FNcaseSensitive = Boolean.parseBoolean(jsonReader.readPropertyAsString("caseSensitive", FNParameters));
        }

        FNplacement = jsonReader.readPropertyAsString("placement", jsonObject);


        jsonObject= jsonReader.readJsonObject("lastName");
        lastName = jsonReader.readPropertyAsString("name", jsonObject);
        JsonObject LNParameters= jsonReader.readJsonObject("usePart", jsonObject);

        if (LNParameters!=null){
            if (jsonReader.readPropertyAsString("amountOfChars", LNParameters)!=null)
                LNchars = Integer.parseInt(jsonReader.readPropertyAsString("amountOfChars", LNParameters));

            LNtakeCharsFrom = jsonReader.readPropertyAsString("takeCharsFrom", LNParameters);
            if (jsonReader.readPropertyAsString("percentageOfName", LNParameters)!=null)
                LNpercentageOfName = Double.parseDouble(jsonReader.readPropertyAsString("percentageOfName", LNParameters));

            if (jsonReader.readPropertyAsString("caseSensitive", LNParameters)!=null)
                LNcaseSensitive = Boolean.parseBoolean(jsonReader.readPropertyAsString("caseSensitive", LNParameters));
        }

        LNplacement = jsonReader.readPropertyAsString("placement", jsonObject);

        jsonObject= jsonReader.readJsonObject("characterLimits");
        if (jsonObject!= null){
            if (jsonReader.readPropertyAsString("lower", jsonObject)!=null)
                lowerLimit= Integer.parseInt(jsonReader.readPropertyAsString("lower", jsonObject));
            if (jsonReader.readPropertyAsString("upper", jsonObject)!=null)
                upperLimit= Integer.parseInt(jsonReader.readPropertyAsString("upper", jsonObject));
        }

        System.out.println(upperLimit);
    }   
    
}
