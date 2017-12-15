package txtreaderlib.interfaces;

import java.util.List;

import txtreaderlib.bean.TxtChar;


/*
* create by bifan-wei
* 2017-11-14
*/
public interface ITxtLine {
    List<TxtChar> getTxtChars();

    int getCharNum();

    TxtChar getFirstChar();

    TxtChar getLastChar();

    TxtChar getChar(int index);

    ICursor<TxtChar> getCharCursor();

    String getLineStr();

    char[] getLineChar();

    Boolean HasData();

    void addChar(TxtChar txtChar);

    void Clear();

    int CurrentIndex();
}
