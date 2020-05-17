package com.cybersectnt.data;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;


import android.os.Handler;

import com.cybersectnt.cybersectntdemo1.R;



public class TypeWriter extends androidx.appcompat.widget.AppCompatTextView {

    /*
    This class is used as an extention to the text view however with some modification ( it allows to show the message being written)
     */
    private String mText;
    private int mIndex;
    private long mDelay = 50;
    public static final int INCOMING = 1;
    public static final int OUTGOING = 2;
    public static final int QUESTION = 3;
    public static final int ANSWER = 4;
    public static final int RESULT = 5;

    private int type = 0;
    DataChange dataChange;

    /**
     * This method is used to initialize the text view and show it
     * @param context, type, text
     *
     */

    public TypeWriter(Activity context, int type, String text) {
        super(context);
        dataChange = (DataChange) context;
        mText = text;
        this.type = type;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        if (type == INCOMING) {
            setBackground(context.getDrawable(R.drawable.incoming_bubble));
        } else if (type == OUTGOING) {
            setBackground(context.getDrawable(R.drawable.outgoing_bubble));
            params.gravity = Gravity.END;
        } else if (type == QUESTION) {
//            params = new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    LinearLayout.LayoutParams.WRAP_CONTENT
//            );
            params.gravity = Gravity.CENTER;
            setGravity(Gravity.CENTER);
            setBackgroundColor(context.getColor(R.color.colorPrimary));
        } else { // RESULT AND ANSWER
            params.gravity = Gravity.CENTER;
            setGravity(Gravity.CENTER);
            setBackgroundColor(context.getColor(R.color.colorPrimary));
        }
        setTextColor(context.getColor(R.color.colorWhite));
        setTextSize(23);
        setText("12");
        params.setMargins(0, calculateInDP(20), 0, 0);
        setLayoutParams(params);
        setPadding(calculateInDP(30), 0, calculateInDP(30), 0);
    }

    /*
    This method is used to calculate the how the letters will appear
     */

    public int calculateInDP(int value) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (value * scale + 0.5f);
    }

    /*
    This method is used to add each character after a certain amount of time
     */

    public TypeWriter(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    Handler mHandler = new Handler();
    private Runnable characterAdder = new Runnable() {
        @Override
        public void run() {
            setText(mText.subSequence(0, mIndex++));
            if (mIndex <= mText.length()) {
                mHandler.postDelayed(characterAdder, mDelay);
                dataChange.scroll();
            } else {
                if (type != RESULT) {
                    dataChange.finished();
                }
            }
        }
    };

    /*
    this method is used to be called from the other activity so the animation can start
     */

    public void animateText() {
        mIndex = 0;
        setText("");
        mHandler.removeCallbacks(characterAdder);
        mHandler.postDelayed(characterAdder, mDelay);
    }

    /*
    if the data changed finish
     */

    public interface DataChange {
        void finished();
        void scroll();
    }
}
