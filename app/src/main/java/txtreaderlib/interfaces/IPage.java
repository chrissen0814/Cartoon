package txtreaderlib.interfaces;

import java.util.List;

import txtreaderlib.bean.TxtChar;


/*
* create by bifan-wei
* 2017-11-13
*/
public interface IPage {
    ITxtLine getLine(int index);

    void addLine(ITxtLine line);

    void addLineTo(ITxtLine line, int index);

    void setLines(List<ITxtLine> lines);

    TxtChar getFirstChar();

    TxtChar getLastChar();

    ITxtLine getFirstLine();

    ITxtLine getLastLine();

    List<ITxtLine> getLines();

    ICursor<ITxtLine> getLineCursor();

    int getLineNum();

    int CurrentIndex();

    Boolean HasData();

}
