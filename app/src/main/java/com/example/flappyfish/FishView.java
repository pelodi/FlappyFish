package com.example.flappyfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;

import com.example.flyingfishgame.R;

public class FishView extends View {

    private Bitmap fish;
    private Bitmap background;
    private Bitmap[] life = new Bitmap[2];
    private Paint score = new Paint();

    private int fishY;
    private int fishVelocity;

    private boolean touch = false;

    public FishView(Context context) {
        super(context);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        fish = BitmapFactory.decodeResource(getResources(), R.drawable.fish);
        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.lost_live);

        fishY = 550;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background,0,0,null);

        int canvasWidth = getWidth();
        int canvasHeight = getHeight();

        int minY = fish.getHeight();
        int maxY = canvasHeight - (fish.getHeight() * 3);
        fishY += fishVelocity;

        if(fishY < minY){
            fishY = minY;
        }
        if(fishY > maxY){
            fishY = maxY;
        }
        fishVelocity += 2;

        int fishX = 10;
        if(touch){
            canvas.drawBitmap(fish, fishX, fishY, null);
            touch = false;
        }
        else{
            canvas.drawBitmap(fish, fishX, fishY,null);
        }

        canvas.drawText("Score : ", 20, 60, score);
        canvas.drawBitmap(life[0], 580, 10, null);
        canvas.drawBitmap(life[0], 680, 10, null);
        canvas.drawBitmap(life[0], 780, 10, null);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            touch = true;
            fishVelocity = -22;
        }
        return true;
    }
}
