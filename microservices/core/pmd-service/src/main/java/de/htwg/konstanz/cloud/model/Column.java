package de.htwg.konstanz.cloud.model;

public class Column {
    private  final int nColumnBegin;

    private final int nColumnEnd;

    public Column(int nColumnBegin, int nColumnEnd) {
        this.nColumnBegin = nColumnBegin;
        this.nColumnEnd = nColumnEnd;
    }

    public int getColumnBegin() {
        return nColumnBegin;
    }

    public int getColumnEnd() {
        return nColumnEnd;
    }
}
