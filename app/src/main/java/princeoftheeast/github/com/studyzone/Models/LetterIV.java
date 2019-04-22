package princeoftheeast.github.com.studyzone.Models;
/*https://www.youtube.com/watch?v=mw3t-trrnmU&t=587s
The blueprint of this class was obtained from the above tutorial
***/
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import princeoftheeast.github.com.studyzone.R;

import java.util.Random;

public class LetterIV extends android.support.v7.widget.AppCompatImageView {

    private char theLetter;
    private Paint thePaintText;
    private Paint thePaintBackground;
    private int nColorText = Color.WHITE;
    private boolean isOval;

    public LetterIV(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        thePaintText = new Paint(Paint.ANTI_ALIAS_FLAG);
        thePaintText.setColor(nColorText);
        thePaintBackground = new Paint(Paint.ANTI_ALIAS_FLAG);
        thePaintBackground.setStyle(Paint.Style.FILL);
        thePaintBackground.setColor(randomColor());
    }

    public char getLetter() {
        return theLetter;
    }

    public void setLetter(char letter) {
        theLetter = letter;
        invalidate();
    }

    public int getTextColor() {
        return nColorText;
    }

    public void setTextColor(int textColor) {
        nColorText = textColor;
        invalidate();
    }

    public void setOval(boolean oval) {
        isOval = oval;
    }

    public boolean isOval() {
        return isOval;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (getDrawable() == null) {
            //font size of text is set to be base from the view height
            thePaintText.setTextSize(canvas.getHeight() - getTextPadding() * 2);
            if (isOval()) {
                canvas.drawCircle(canvas.getWidth() / 2f, canvas.getHeight() / 2f, Math.min(canvas.getWidth(), canvas.getHeight()) / 2f,
                        thePaintBackground);
            } else {
                canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), thePaintBackground);
            }
            // To measure it
            Rect textBounds = new Rect();
            thePaintText.getTextBounds(String.valueOf(theLetter), 0, 1, textBounds);
            float textWidth = thePaintText.measureText(String.valueOf(theLetter));
            float textHeight = textBounds.height();
            // To Draw it
            canvas.drawText(String.valueOf(theLetter), canvas.getWidth() / 2f - textWidth / 2f,
                    canvas.getHeight() / 2f + textHeight / 2f, thePaintText);
        }
    }

    private float getTextPadding() {
        return 8 * getResources().getDisplayMetrics().density;
    }

    private int randomColor() {
        Random random = new Random();
        String[] colorsArr = getResources().getStringArray(R.array.colors);
        return Color.parseColor(colorsArr[random.nextInt(colorsArr.length)]);
    }
	

}