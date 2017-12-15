package txtreaderlib.tasks;

import android.graphics.Color;
import android.graphics.Paint;

import txtreaderlib.interfaces.ILoadListener;
import txtreaderlib.interfaces.ITxtTask;
import txtreaderlib.main.PaintContext;
import txtreaderlib.main.TxtConfig;
import txtreaderlib.main.TxtReaderContext;
import txtreaderlib.utils.ELogger;

/**
 * Created by bifan-wei
 * on 2017/11/27.
 */

public class DrawPrepareTask implements ITxtTask {
    private String tag = "DrawPrepareTask";

    @Override
    public void Run(ILoadListener callBack, TxtReaderContext readerContext) {
        ELogger.log(tag, "do DrawPrepare");
        initPainContext(readerContext.getPaintContext(), readerContext.getTxtConfig());
        readerContext.getPaintContext().textPaint.setColor(Color.WHITE);
        ITxtTask txtTask = new BitmapProduceTask();
        txtTask.Run(callBack, readerContext);

    }

    private void initPainContext(PaintContext paintContext, TxtConfig txtConfig) {
        paintContext.textPaint.setTextSize(txtConfig.textSize);
        paintContext.textPaint.setFakeBoldText(txtConfig.Bold);
        paintContext.textPaint.setTextAlign(Paint.Align.LEFT);
        paintContext.textPaint.setColor(txtConfig.textColor);
        paintContext.notePaint.setTextSize(txtConfig.textSize);
        paintContext.notePaint.setColor(txtConfig.NoteColor);
        paintContext.notePaint.setTextAlign(Paint.Align.LEFT);
        paintContext.selectTextPaint.setTextSize(txtConfig.textSize);
        paintContext.selectTextPaint.setColor(txtConfig.SelectTextColor);
        paintContext.selectTextPaint.setTextAlign(Paint.Align.LEFT);
        paintContext.sliderPaint.setColor(txtConfig.SliderColor);
        paintContext.sliderPaint.setColor(txtConfig.SliderColor);
        paintContext.sliderPaint.setAntiAlias(true);
    }
}
