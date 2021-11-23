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
    String inscriptionYear;

    public GeneratingMethods(String FirstName, String LastName, String AcademicId, String inscriptionYear, Vector<String> Separators, String prefix, String FNplacement, String LNplacement){
        this.FirstName=FirstName;
        this.LastName=LastName;
        this.AcademicId=AcademicId;
        this.Separators=Separators;
        this.prefix=prefix;
        this.FNplacement=FNplacement;
        this.LNplacement=LNplacement;
        this.inscriptionYear=inscriptionYear;
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
        if (FNchars==0 && LNchars==0) return vec;
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
        if (FNpercent==0.0 && LNpercent==0.0) return vec;
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

        if (FNplacement.equals("start"))
        {
            if (LNplacement.equals("") || LNplacement==null || LNplacement.equals("end")){
                vec.add(FirstName+Separator+LastName);
                return vec;
            }
        }
        if (LNplacement.equals("start"))
        {
            if (FNplacement.equals("") || FNplacement==null || FNplacement.equals("end")){
                vec.add(LastName+Separator+FirstName);
                return vec;
            }
        }

        vec.add(LastName+Separator+FirstName);
        vec.add(FirstName+Separator+LastName);
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

    public String prefixedID(){
        String N="";
        int length= AcademicId.length();
        int delim=hasDelimiter(AcademicId);
        if (delim!=-1){
            N=AcademicId.substring(delim+1, length);
        }
        else if (AcademicId.contains(inscriptionYear)){
            int index=AcademicId.indexOf(inscriptionYear);
            N= AcademicId.substring(index+2);
        }
        else{
            N= AcademicId.substring(prefix.length());
        }
        return prefix+N;
    }

    public Vector<String> prefixedNames(){
        Vector<String> vec = new Vector<String>();
        vec.add(prefix+capitalize(FirstName)+capitalize(LastName));
        vec.add(prefix+capitalize(LastName)+capitalize(FirstName));
        vec.add(prefix+capitalize(FirstName));
        vec.add(prefix+capitalize(LastName));
        return vec;
    }

    public Vector<String> randomNames(int minLimit, int maxLimit, int namesNeeded, Vector<String> proposedNames){
        Vector<String> vec = new Vector<String>();
        Random rand = new Random();
        int size1,size2;
        if (minLimit==0) minLimit=5;
        if (maxLimit==0) maxLimit=100;
        for (int i=0; i<namesNeeded; i++){
             boolean check = false;
            int j=0;
            while (check != true) {
                String name;
                size1 = rand.nextInt(FirstName.length());
                size2 = rand.nextInt(LastName.length()) ;

                if ((size1 + size2) >= minLimit && (size1 + size2) <= maxLimit) {
                    name = FirstName.substring(0, size1) + LastName.substring(0, size2);
                    boolean Test=inVector(vec, name);
                        if (Test==false) {
                            vec.add(name);
                            check = true;
                        }
                }
                if (j==20*namesNeeded) check=true;
                j++;
            }
        }
        return vec;
    }

    public boolean inVector(Vector<String> proposedNames, String userName){
        for (String name: proposedNames){
            if (name.equals(userName)) return true;
        }
        return false;
    }

    int hasDelimiter (String ID){
        int count=0;
        for (char ch: ID.toCharArray()) {
            if (Character.isDigit(ch)==false) return count;
            count++;
        }
        return -1;
    }

    public String capitalize(String str){
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}
