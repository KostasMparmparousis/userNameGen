/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.gunet.usernamegenerator;
import gr.gunet.usernamegenerator.tools.CustomJsonReader;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import java.util.Vector;
import java.util.Collections;
/**
 *
 * @author mpkos
 */
public class UserNameGen {
    private CustomJsonReader jsonReader;
    private String firstName="";

    private int FNchars=0;
    private String FNtakeCharsFrom="";

    private double FNpercentageOfName=0.0;
    private String FNtakePercentFrom="";

    private String FNplacement="";

    private String lastName="";

    private int LNchars=0;
    private String LNtakeCharsFrom="";

    private double LNpercentageOfName=0.0;
    private String LNtakePercentFrom="";

    private String LNplacement="";

    private String prefix="";
    private String academicID="";
    private int upperAmountOfNamesSuggested=0;
    private String orderBy="";
    private String order="";
    Vector<String> separators;
    private String[] prioritizeBy;
    private int lowerLimit=0;
    private int upperLimit=0;
    GeneratingMethods gen;


    public UserNameGen(CustomJsonReader JsonReader){
        separators = new Vector<String>();
        jsonReader=JsonReader;
        prefix = jsonReader.readPropertyAsString("prefix");
        academicID = jsonReader.readPropertyAsString("academicID");
        if (jsonReader.readPropertyAsString("upperAmountOfNamesSuggested")!=null)
            upperAmountOfNamesSuggested = Integer.parseInt(jsonReader.readPropertyAsString("upperAmountOfNamesSuggested"));
        orderBy = jsonReader.readPropertyAsString("orderBy");
        order = jsonReader.readPropertyAsString("order");

        String[] Separators= jsonReader.readPropertyAsStringArray("separators");
        if (Separators!=null){
            for (String Separator: Separators){
                separators.add(Separator);
            }
        }
        else{
            separators.add("");
        }

        prioritizeBy= jsonReader.readPropertyAsStringArray("prioritizeBy");

        JsonObject jsonObject= jsonReader.readJsonObject("firstName");
        firstName = jsonReader.readPropertyAsString("name", jsonObject);
        JsonObject FNParameters= jsonReader.readJsonObject("usePart", jsonObject);
        if (FNParameters!=null){
            if (jsonReader.readPropertyAsString("amountOfChars", FNParameters)!=null)
                FNchars = Integer.parseInt(jsonReader.readPropertyAsString("amountOfChars", FNParameters));

            FNtakeCharsFrom = jsonReader.readPropertyAsString("takeCharsFrom", FNParameters);
        }
        FNParameters= jsonReader.readJsonObject("usePercent", jsonObject);
        if (FNParameters!=null){
            if (jsonReader.readPropertyAsString("percentageOfName", FNParameters)!=null)
                FNpercentageOfName = Double.parseDouble(jsonReader.readPropertyAsString("percentageOfName", FNParameters));

            FNtakePercentFrom = jsonReader.readPropertyAsString("takeCharsFrom", FNParameters);
        }

        FNplacement = jsonReader.readPropertyAsString("placement", jsonObject);

        jsonObject= jsonReader.readJsonObject("lastName");
        lastName = jsonReader.readPropertyAsString("name", jsonObject);
        JsonObject LNParameters= jsonReader.readJsonObject("usePart", jsonObject);
        if (LNParameters!=null){
            if (jsonReader.readPropertyAsString("amountOfChars", LNParameters)!=null)
                LNchars = Integer.parseInt(jsonReader.readPropertyAsString("amountOfChars", LNParameters));

            LNtakeCharsFrom = jsonReader.readPropertyAsString("takeCharsFrom", LNParameters);
        }
        LNParameters= jsonReader.readJsonObject("usePercent", jsonObject);
        if (LNParameters!=null){
            if (jsonReader.readPropertyAsString("percentageOfName", LNParameters)!=null)
                LNpercentageOfName = Double.parseDouble(jsonReader.readPropertyAsString("percentageOfName", LNParameters));

            LNtakePercentFrom = jsonReader.readPropertyAsString("takeCharsFrom", LNParameters);
        }

        LNplacement = jsonReader.readPropertyAsString("placement", jsonObject);

        jsonObject= jsonReader.readJsonObject("characterLimits");
        if (jsonObject!= null){
            if (jsonReader.readPropertyAsString("lower", jsonObject)!=null)
                lowerLimit= Integer.parseInt(jsonReader.readPropertyAsString("lower", jsonObject));
            if (jsonReader.readPropertyAsString("upper", jsonObject)!=null)
                upperLimit= Integer.parseInt(jsonReader.readPropertyAsString("upper", jsonObject));
        }

        gen= new GeneratingMethods(firstName, lastName, academicID, "2017", separators, prefix, FNplacement, LNplacement);
        proposeNames();
    }

    public void proposeNames(){
        Vector<String> proposedNames = new Vector<String>();

        String prefixedID="";
        Vector<String> fullNames=gen.FullNames();
        Vector<String> prefixedNames= new Vector<String>();
        Vector<String> partOfNames = new Vector<String>();
        Vector<String> percentOfNames = new Vector<String>();

        partOfNames=gen.partOfNames(FNchars, FNtakeCharsFrom, LNchars, LNtakeCharsFrom);
        percentOfNames=gen.percentOfNames(FNpercentageOfName, FNtakePercentFrom, LNpercentageOfName, LNtakePercentFrom);

        if (prefix!=null){
            if (academicID!=null) prefixedID= gen.prefixedID();
            prefixedNames= gen.prefixedNames();
        }

        if (prioritizeBy==null){
            prioritizeBy=new String[]{"prefixedID", "fullNames", "prefixedNames", "partOfNames", "percentOfNames"};
        }

        int count=0;
        for (String priority: prioritizeBy){
            switch (priority){
                case "prefixedID":
                    if (prefix!=null) proposedNames.add(prefixedID);
                    break;
                case "prefixedNames":
                    addToArray(proposedNames, prefixedNames);
                    break;
                case "fullNames":
                    addToArray(proposedNames, fullNames);
                    break;
                case "partOfNames":
                    addToArray(proposedNames, partOfNames);
                    break;
                case "percentOfNames":
                    addToArray(proposedNames, percentOfNames);
                    break;
                default :
                    System.out.println("prioritization technique not found: " + priority);
            }
        }

        proposedNames= charAndSizeLimit(proposedNames);
        int names=proposedNames.size();
        Vector<String> randomNames;
        if (upperAmountOfNamesSuggested!=0){
            randomNames=gen.randomNames(lowerLimit, upperLimit, upperAmountOfNamesSuggested-names, proposedNames);
        }
        else{
            randomNames=gen.randomNames(lowerLimit, upperLimit, 5, proposedNames);
        }
        addToArray(proposedNames, randomNames);

        for (String Name: proposedNames){
            System.out.println(Name);
        }
    }

    private void addToArray(Vector<String> proposedNames, Vector<String> Names){
        if (orderBy!=null){
            if (orderBy.equals("alphabetically")){
                Collections.sort(Names);
            }
            else if (orderBy.equals("size")){
                sortBySize(Names);
            }
        }
        proposedNames.addAll(proposedNames.size(), Names);
    }

    private void sortBySize(Vector<String> vec){
        for (int i=1 ;i<vec.size(); i++)
        {
            String temp = vec.get(i);

            int j = i - 1;
            while (j >= 0 && temp.length() < vec.get(j).length())
            {
                vec.set(j+1,vec.get(j));
                j--;
            }
            vec.set(j+1,temp);
        }
    }
    public Vector<String> charAndSizeLimit(Vector<String> proposedNames){
        Vector<String> ProposedNames= new Vector<String>();
        for (String Name: proposedNames){
            if (lowerLimit!=0 && Name.length()<lowerLimit) {
                continue;
            }
            if (upperLimit!=0 && Name.length()>upperLimit) {
                continue;
            }
            ProposedNames.add(Name);
        }
        if (upperAmountOfNamesSuggested==0) return ProposedNames;

        int names=ProposedNames.size();
        for (int i=names-1; i>=upperAmountOfNamesSuggested; i--){
            ProposedNames.remove(i);
        }
        return ProposedNames;
    }
}
