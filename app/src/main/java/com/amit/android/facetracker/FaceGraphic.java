package com.amit.android.facetracker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

import com.amit.android.facetracker.ui.camera.GraphicOverlay;
import com.google.android.gms.vision.face.Face;

class FaceGraphic extends GraphicOverlay.Graphic {
    private static final float BOX_STROKE_WIDTH = 5.0f;
    private Paint mFacePositionPaint;
    private Paint mIdPaint;
    private Paint mBoxPaint;

    private volatile Face mFace;
    private int mFaceId;
    private float mFaceHappiness;
    Listener listener;

    FaceGraphic(Listener listener, GraphicOverlay overlay) {
        super(overlay);
        this.listener = listener;
        mFacePositionPaint = new Paint();
        mFacePositionPaint.setColor(Color.GREEN);

        mIdPaint = new Paint();
        mIdPaint.setColor(Color.GREEN);

        mBoxPaint = new Paint();
        mBoxPaint.setColor(Color.GREEN);
        mBoxPaint.setStyle(Paint.Style.STROKE);
        mBoxPaint.setStrokeWidth(BOX_STROKE_WIDTH);
    }

    void setId(int id) {
        mFaceId = id;
    }

    void updateFace(Face face) {
        mFace = face;
        postInvalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        Face face = mFace;
        if (face == null) {
            return;
        }

        float x = translateX(face.getPosition().x + face.getWidth() / 2);
        float y = translateY(face.getPosition().y + face.getHeight() / 2);

        float xOffset = scaleX(face.getWidth() / 2.0f);
        float yOffset = scaleY(face.getHeight() / 2.0f);
        float faceLeft = x - xOffset;
        float faceTop = y - yOffset;
        float faceRight = x + xOffset;
        float faceBottom = y + yOffset;

        int canvasW = canvas.getWidth();
        int canvasH = canvas.getHeight();
        Point centerOfCanvas = new Point(canvasW / 2, canvasH / 2);
        int rectW = 100;
        int rectH = 100;
        int captureAreaLeft = centerOfCanvas.x - (rectW *3);
        int captureAreaTop = centerOfCanvas.y - (rectH * 3);
        int captureAreaRight = centerOfCanvas.x + (rectW * 3 );
        int captureAreaBottom = centerOfCanvas.y + (rectH * 3 );
        Rect rect = new Rect(captureAreaLeft, captureAreaTop, captureAreaRight, captureAreaBottom);
        canvas.drawRect(rect, mBoxPaint);


        if (faceLeft >= captureAreaLeft && faceRight <= captureAreaRight && faceTop >= captureAreaTop && faceBottom <= captureAreaBottom) {
            listener.onFaceInsideCaptureArea(new Rect((int)faceLeft, (int)faceTop, (int)faceRight, (int)faceBottom));
        } else {
            listener.onFaceOutsideCaptureArea();
        }
    }


    public interface Listener {
        void onFaceInsideCaptureArea(Rect rect);
        void onFaceOutsideCaptureArea();

    }
}