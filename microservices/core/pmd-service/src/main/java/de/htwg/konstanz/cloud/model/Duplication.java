package de.htwg.konstanz.cloud.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nexoc on 09.06.2016.
 */
public class Duplication {
    private final int sDupilcatedLine;
    private final int sTokens;
    private final List<String> lInvolvedData;
    private final String sDuplicatedCode;

    private Duplication(int sDupilcatedLine, int sTokens, List<String> lInvolvedData, String sDuplicatedCode){
        this.sDupilcatedLine = sDupilcatedLine;
        this.sTokens = sTokens;
        this.lInvolvedData = lInvolvedData;
        this.sDuplicatedCode = sDuplicatedCode;
    }

    public static Duplication getDupliactionInstance(int sDupilcatedLine, int sTokens, List<String> lInvolvedData, String sDuplicatedCode){
        return new Duplication(sDupilcatedLine,sTokens,lInvolvedData,sDuplicatedCode);
    }

    public int getsDupilcatedLine() {
        return sDupilcatedLine;
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
