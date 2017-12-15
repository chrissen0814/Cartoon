package txtreaderlib.interfaces;

import txtreaderlib.bean.TxtMsg;
import txtreaderlib.main.TxtReaderContext;

/*
* create by bifan-wei
* 2017-11-13
*/
public interface TaskParcel {
    void onNextTask(TaskParcel parcel, TxtReaderContext readerContext);
    void onBack(TxtMsg msg);
}
