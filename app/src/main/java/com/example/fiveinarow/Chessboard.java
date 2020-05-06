package com.example.fiveinarow;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;

/** Class build to set up the UI in game and draw out the chessboard and pieces.
 * Since I haven't learned how to use canvas in android to draw lines and use Bitmap to add
 * pictures, some of the codes below are borrowed from programming websites
 * https://github.com/codekongs/WuZiQi/blob/master/wuziqi/src/main/java/com/codekong/wuziqi/view/WuziqiPanel.java
 */
public class Chessboard extends View {
    public Chessboard(Context context) {
        super(context);
    }

    public Chessboard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    /** The width of the chessboard. */
    private int BoardWidth;
    /** The distance between each line on the chessboard. */
    private float LineDistance;
    /** The size of total horizontal or vertical lines on chessboard. */
    private final int MaxLine = 14;
    /** Paint the chessboard. */
    private Paint MyPaint;
    /** The graph of black pieces. */
    private Bitmap BlackPiece;
    /** The graph of white pieces. */
    private Bitmap WhitePiece;
    /** The ration of pieces to the height of each line. */
    private float PieceRatio= 2 * 1.0f / 3;
    /** The list to store the points of black pieces. */
    private ArrayList<Point> BlackArray = new ArrayList<>();
    /** The list to store the points of white pieces. */
    private ArrayList<Point> WhiteArray = new ArrayList<>();
    /** Black pieces start first. */
    private boolean IsBlack = true;
    /** Check whether current game is over. */
    private boolean IsGameOver = false;
    /** Check if the chessboard is full. */
    private boolean IsFull = false;


    /**
     * Set up the initial status in painting.
     */
    private void init() {
        MyPaint = new Paint();
        MyPaint.setStyle(Paint.Style.STROKE);
        BlackPiece = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        WhitePiece = BitmapFactory.decodeResource(getResources(), R.drawable.white);
    }

    /**
     * Measure the screen to get the size of the chessboard.
     */
    public void onMeasure(int widthMeasure, int heightMeasure) {
        int widthSize = MeasureSpec.getSize(widthMeasure);
        int heightSize = MeasureSpec.getSize(heightMeasure);
        int width = Math.min(widthSize, heightSize);
        setMeasuredDimension(width, width);
    }

    /**
     * Adjust the size of the chessboard.
     */
    public void onSizeChanged(int width, int height, int OldWidth, int OldHeight) {
        super.onSizeChanged(width, height, OldWidth, OldHeight);
        BoardWidth = width;
        LineDistance = BoardWidth / MaxLine;
        int PieceWidth = (int) (LineDistance * PieceRatio);
        BlackPiece = Bitmap.createScaledBitmap(BlackPiece, PieceWidth, PieceWidth, false);
        WhitePiece = Bitmap.createScaledBitmap(WhitePiece, PieceWidth, PieceWidth, false);
    }


    /**
     * Draw the chessboard.
     * @param canvas the screen in the app.
     */
    public void drawBoard(Canvas canvas) {
        int gapWidth = (int) LineDistance / 2;
        for (int i = 0; i < MaxLine; i++) {
            int xLeft = gapWidth;
            int xRight = BoardWidth - gapWidth;
            float y = i * LineDistance + gapWidth;
            canvas.drawLine(xLeft, y, xRight, y, MyPaint);
            canvas.drawLine(y, xLeft, y, xRight, MyPaint);
        }
    }

    /**
     * Draw each piece on the point.
     * @param canvas the chessboard region in the app.
     */
    private void drawPieces(Canvas canvas) {
        for (int i = 0; i < BlackArray.size(); i++) {
            Point blackPoint = BlackArray.get(i);
            canvas.drawBitmap(BlackPiece,
                    (blackPoint.x + (1 - PieceRatio) / 3) * LineDistance,
                    (blackPoint.y + (1 - PieceRatio) / 3) * LineDistance, null);
        }
        for (int j = 0; j < WhiteArray.size(); j++) {
            Point whitePoint = WhiteArray.get(j);
            canvas.drawBitmap(WhitePiece,
                    (whitePoint.x + (1 - PieceRatio) / 3) * LineDistance,
                    (whitePoint.y + (1 - PieceRatio) / 3) * LineDistance, null);
        }
    }


    /**
     * Help function to round up the coordinates of touch point into the nearest integer.
     * @param x x-coordinate of the point
     * @param y y-coordinate of the point
     * @return the valid point on chessboard
     * Cited from https://github.com/codekongs/WuZiQi/blob/master/wuziqi/src/main/java/com/codekong/wuziqi/view/WuziqiPanel.java
     */
    private Point getValidPoint(int x, int y) {
        return new Point((int) (x / LineDistance), (int) (y / LineDistance));
    }

    /**
     * @param move The touch by user on the screen.
     * @return The piece locates at the touch position.
     * Cited from https://github.com/codekongs/WuZiQi/blob/master/wuziqi/src/main/java/com/codekong/wuziqi/view/WuziqiPanel.java
     */
    public boolean onTouchEvent(MotionEvent move) {
        if (IsGameOver) {
            return false;
        }
        int action = move.getAction();
        if (action == MotionEvent.ACTION_UP){
            int x = (int) move.getX();
            int y = (int) move.getY();
            Point point = getValidPoint(x, y);
            if (BlackArray.contains(point) || WhiteArray.contains(point)){
                return false;
            }
            if (IsBlack){
                BlackArray.add(point);
            } else {
                WhiteArray.add(point);
            }
            invalidate();
            IsBlack = !IsBlack;
        }
        return true;
    }

    /**
     * Check whether the game is over.
     */
    public void checkGameOver() {
        boolean blackWin = CheckGame.checkGameWin(BlackArray);
        boolean whiteWin = CheckGame.checkGameWin(WhiteArray);
        if (blackWin == true) {
            IsGameOver = true;
            String message = "Black wins";
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
        if (whiteWin == true) {
            IsGameOver = true;
            String message = "White wins";
            Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
        }
        if (BlackArray.size() == 98 && WhiteArray.size() == 98) {
            IsFull = true;
            IsGameOver = true;
            Toast.makeText(getContext(), "Game ends in a draw", Toast.LENGTH_LONG).show();
        }
    }

    /** Make the game running with moves. */
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBoard(canvas);
        drawPieces(canvas);
        checkGameOver();
    }

    /**
     * Restart a new game.
     */
    public void restart() {
        postInvalidate();
        BlackArray.clear();
        WhiteArray.clear();
        IsGameOver = false;
        IsBlack = true;
    }

}
