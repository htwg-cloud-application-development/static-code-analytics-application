package de.htwg.konstanz.cloud.model;

/**
 * Class for various column attributes of the pmd evaluation
 */
public class Column {
    private  final int nColumnBegin;

    private final int nColumnEnd;

    public Column(int nColumnBegin, int nColumnEnd) {
        this.nColumnBegin = nColumnBegin;
        this.nColumnEnd = nColumnEnd;
    }

    int getColumnBegin() {
        return nColumnBegin;
    }

    int getColumnEnd() {
        return nColumnEnd;
    }
}
