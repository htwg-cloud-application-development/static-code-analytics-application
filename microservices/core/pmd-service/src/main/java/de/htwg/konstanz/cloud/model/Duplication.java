package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

public class Duplication {
    private final int sDuplicatedLine;
    private final int sTokens;
    private final List<String> lInvolvedData;
    private final String sDuplicatedCode;

    public Duplication(int sDupilcatedLine, int sTokens, List<String> lInvolvedData, String sDuplicatedCode){
        this.sDuplicatedLine = sDupilcatedLine;
        this.sTokens = sTokens;
        this.lInvolvedData = lInvolvedData;
        this.sDuplicatedCode = sDuplicatedCode;
    }

    public static Duplication getDupliactionInstance(int sDupilcatedLine, int sTokens, List<String> lInvolvedData, String sDuplicatedCode){
        return new Duplication(sDupilcatedLine,sTokens,lInvolvedData,sDuplicatedCode);
    }

    public int getsDuplicatedLine() {
        return sDuplicatedLine;
    }

    public int getsTokens() {
        return sTokens;
    }

    public List<String> getlInvolvedData() {
        return lInvolvedData;
    }

    public String getsDuplicatedCode() {
        return sDuplicatedCode;
    }
}
