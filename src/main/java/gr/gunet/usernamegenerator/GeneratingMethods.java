/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gr.gunet.usernamegenerator;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author mpkos
 */
public class GeneratingMethods {
    String FirstName;
    String LastName;
    String AcademicId;
    Vector<String> Separators;
    String prefix;
    String FNplacement;
    String LNplacement;

    public GeneratingMethods(String FirstName, String LastName, String AcademicId, Vector<String> Separators, String prefix, String FNplacement, String LNplacement){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.AcademicId=AcademicId;
        this.Separators=Separators;
        this.prefix=prefix;
        this.FNplacement=FNplacement;
        this.LNplacement=LNplacement;
    }

    public  Vector<String> FullNames(){
        Vector<String> vec = new Vector<String>();
        int count=0;
        for (String Separator: Separators){
            Vector<String> Names= orderOfNames(FirstName, LastName, Separator);
            vec.addAll(count,Names);
            count+=Names.size();
        }
         return vec;
    }

    public Vector<String> partOfNames(int FNchars, String FNtakeFrom, int LNchars, String LNtakeFrom){
        Vector<String> vec = new Vector<String>();
        int count=0;
        for (String Separator: Separators){
            String FN,LN;
            int FNlength=FirstName.length();
            int LNlength=LastName.length();
            FN=getSubstring(FirstName, FNchars, FNtakeFrom, FNlength);
            LN=getSubstring(LastName, LNchars, LNtakeFrom, LNlength);

            Vector<String> Names= orderOfNames(FN, LN, Separator);
            vec.addAll(count,Names);
            count+=Names.size();
        }
         return vec;
    }

    public Vector<String> percentOfNames(double FNpercent, String FNtakeFrom, double LNpercent, String LNtakeFrom){
        Vector<String> vec = new Vector<String>();
        int count=0;
        for (String Separator: Separators){
            String FN,LN;
            int FNlength= (FirstName.length());
            int LNlength= (LastName.length());
            double fnchars= FNlength*FNpercent;
            double lnchars= LNlength*LNpercent;
            int FNchars= (int) fnchars;
            int LNchars= (int) lnchars;

            FN=getSubstring(FirstName, FNchars, FNtakeFrom, FNlength);
            LN=getSubstring(LastName, LNchars, LNtakeFrom, LNlength);

            Vector<String> Names= orderOfNames(FN, LN, Separator);
            vec.addAll(count,Names);
            count+=Names.size();
        }
         return vec;
    }

    public Vector<String> orderOfNames(String FirstName, String LastName, String Separator){
        Vector<String> vec = new Vector<String>();
        if (FNplacement==null) FNplacement="";
        if (LNplacement==null) LNplacement="";
        switch (FNplacement){
            case "start":
                vec.add(FirstName+Separator+LastName);
                break;
            case "end":
                vec.add(LastName+Separator+FirstName);
                break;
            default:
                if (LNplacement=="start") vec.add(LastName+Separator+FirstName);
                else if (LNplacement=="end") vec.add(FirstName+Separator+LastName);
                else{
                    vec.add(LastName+Separator+FirstName);
                    vec.add(FirstName+Separator+LastName);
                }
        }
        return vec;
    }

    public String getSubstring(String Name, int chars, String TakeFrom, int length){
        Random rand= new Random();
        String N="";
        if (chars==0) return Name;
        if (TakeFrom==null) TakeFrom="";
        switch(TakeFrom){
            case "start":
                N=Name.substring(0,chars);
                break;
            case "end":
                N=Name.substring(length-chars , length);
                break;
            default:
                int randomInd=0;
                do{
                    randomInd= rand.nextInt(length - chars + 1);
                } while((randomInd+chars)>length);
                N=Name.substring(randomInd, randomInd+chars);
        }
        return N;
    }
}
