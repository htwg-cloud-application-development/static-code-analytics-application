package de.htwg.konstanz.cloud.model;

import java.util.List;

/**
 * Pmd provides Copy Paste Detection (CPD) to find duplicate code. This class maps all attributes for the cpd analysis
 */
public class Duplication {
    private final int nDuplicatedLine;

    private final int nTokens;

    private final List<String> lInvolvedData;

    private final String sDuplicatedCode;

    public Duplication(int sDupilcatedLine, int nTokens, List<String> lInvolvedData, String sDuplicatedCode){
        this.nDuplicatedLine = sDupilcatedLine;
        this.nTokens = nTokens;
        this.lInvolvedData = lInvolvedData;
        this.sDuplicatedCode = sDuplicatedCode;
    }

    public static Duplication getDupliactionInstance(int sDupilcatedLine, int sTokens,
                                                     List<String> lInvolvedData, String sDuplicatedCode){
        return new Duplication(sDupilcatedLine,sTokens,lInvolvedData,sDuplicatedCode);
    }

    public int getDuplicatedLine() {
        return nDuplicatedLine;
    }

    public int getTokens() {
        return nTokens;
    }

    public List<String> getInvolvedData() {
        return lInvolvedData;
    }

    public String getDuplicatedCode() {
        return sDuplicatedCode;
    }
}
