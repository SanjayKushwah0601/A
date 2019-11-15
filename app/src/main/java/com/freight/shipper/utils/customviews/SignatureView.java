package com.freight.shipper.utils.customviews;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.freight.shipper.R;

import java.io.File;
import java.io.FileOutputStream;

/**
 * @CreatedBy Sanjay Kushwah on 10/19/2019.
 * sanjaykushwah0601@gmail.com
 */
public class SignatureView extends View {
    private static final float STROKE_WIDTH = 7f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private Paint paint = new Paint();
    private Path path = new Path();

    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();
    private final CustomScrollView mContent;
    Bitmap mBitmap;

    public SignatureView(Context context, AttributeSet attrs, View parent) {
        super(context, attrs);
        this.mContent = (CustomScrollView) parent;
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.black));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    @SuppressLint("WrongThread")
    public File print(String fileName) {
        ProgressDialog dialog = new ProgressDialog(mContent.getContext());
        dialog.setMessage("Saving...");
        dialog.show();

        Bitmap bitmap = getBitmapFromView(mContent, mContent.getChildAt(0).getHeight(), mContent.getChildAt(0).getWidth());
        try {
            File defaultFile = new File(mContent.getContext().getFilesDir().getAbsolutePath() + "/Hello");
            if (!defaultFile.exists())
                defaultFile.mkdirs();

            File file = new File(defaultFile, fileName);
            if (file.exists()) {
                file.delete();
                file = new File(defaultFile, fileName);
            }

            FileOutputStream output = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();

            dialog.dismiss();
            Toast.makeText(mContent.getContext(), "Save", Toast.LENGTH_SHORT).show();
            return file;
        } catch (Exception e) {
            e.printStackTrace();
            dialog.dismiss();
            Toast.makeText(mContent.getContext(), "Failed", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void clear() {
        path.reset();
        invalidate();
    }

    //create bitmap from the view
    private Bitmap getBitmapFromView(View view, int height, int width) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Drawable bgDrawable = view.getBackground();
        if (bgDrawable != null)
            bgDrawable.draw(canvas);
        else
            canvas.drawColor(Color.WHITE);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        // mGetSign.setEnabled(true);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(eventX, eventY);
                lastTouchX = eventX;
                lastTouchY = eventY;
                mContent.setEnableScrolling(false);
                return true;

            case MotionEvent.ACTION_MOVE:

                resetDirtyRect(eventX, eventY);
                int historySize = event.getHistorySize();
                for (int i = 0; i < historySize; i++) {
                    float historicalX = event.getHistoricalX(i);
                    float historicalY = event.getHistoricalY(i);
                    expandDirtyRect(historicalX, historicalY);
                    path.lineTo(historicalX, historicalY);
                }
                path.lineTo(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                mContent.setEnableScrolling(true);
                break;
            default:
                debug("Ignored touch event: " + event.toString());
                return false;
        }

        invalidate((int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;

        return true;
    }

    private void debug(String string) {
        Log.v("log_tag", string);
    }

    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }

        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    private void resetDirtyRect(float eventX, float eventY) {
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }
}
