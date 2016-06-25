package de.htwg.konstanz.cloud.model;

/**
 * Class for various line attributes of the pmd evaluation
 */
public class Line {
    private final int nLineBegin;

    private final int nLineEnd;

    public Line(int nLineBegin, int nLineEnd) {
        this.nLineBegin = nLineBegin;
        this.nLineEnd = nLineEnd;
    }

    int getLineBegin() {
        return nLineBegin;
    }

    int getLineEnd() {
        return nLineEnd;
    }
}
