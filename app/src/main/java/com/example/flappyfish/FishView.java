package com.example.flappyfish;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.example.flyingfishgame.R;

public class FishView extends View {

    private Bitmap fish;
    private Bitmap background;
    private Bitmap[] life = new Bitmap[2];
    private Paint score = new Paint();
    private Paint coin = new Paint();
    private Paint obstacle = new Paint();
    private Paint doubleCoin = new Paint();

    private int scorePoint, lives;
    private int fishX = 10;
    private int fishY;
    private int fishVelocity;
    private int coinX;
    private int coinY;
    private int doubleCoinX, doubleCoinY, doubleCoinVelocity = 20;
    private int obstacleX, obstacleY, obstacleVelocity = 20;

    private boolean touch = false;

    public FishView(Context context) {
        super(context);

        background = BitmapFactory.decodeResource(getResources(), R.drawable.background);
        coin.setColor(Color.YELLOW);
        coin.setAntiAlias(false);
        doubleCoin.setColor(Color.GREEN);
        doubleCoin.setAntiAlias(false);
        obstacle.setColor(Color.RED);
        obstacle.setAntiAlias(false);
        fish = BitmapFactory.decodeResource(getResources(), R.drawable.fish);
        score.setColor(Color.WHITE);
        score.setTextSize(70);
        score.setTypeface(Typeface.DEFAULT_BOLD);
        score.setAntiAlias(true);

        life[0] = BitmapFactory.decodeResource(getResources(), R.drawable.like);
        life[1] = BitmapFactory.decodeResource(getResources(), R.drawable.lost_live);

        fishY = 550;
        scorePoint = 0;
        lives = 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawBitmap(background,0,0,null);

        int canvasWidth = getWidth();
        int canvasHeight = getHeight();
        int coinVelocity = 16;

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

        if(touch){
            canvas.drawBitmap(fish, fishX, fishY, null);
            touch = false;
        }
        else{
            canvas.drawBitmap(fish, fishX, fishY,null);
        }

        coinX -= coinVelocity;

        if(hitCoincheck(coinX,coinY)){
            scorePoint += 10;
            coinX = -100;
        }

        if(coinX < 0){
            coinX = canvasWidth + 21;
            coinY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(coinX, coinY, 25, coin);

        canvas.drawText("Score : " + scorePoint, 20, 60, score);

        doubleCoinX -= doubleCoinVelocity;

        if(hitCoincheck(doubleCoinX,doubleCoinY)){
            scorePoint += 20;
            doubleCoinX = -100;
        }

        if(doubleCoinX < 0){
            doubleCoinX = canvasWidth + 21;
            doubleCoinY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(doubleCoinX, doubleCoinY, 25, doubleCoin);

        obstacleX -= obstacleVelocity;

        if(hitCoincheck(obstacleX,obstacleY)){
            obstacleX = -100;
            lives --;

            if(lives == 0) {
                Toast.makeText(getContext(),"GAME OVER", Toast.LENGTH_SHORT).show();

                Intent gameOver = new Intent(getContext(), GameOverActivity.class);
                gameOver.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                getContext().startActivity(gameOver);
            }
        }

        if(obstacleX < 0){
            obstacleX = canvasWidth + 21;
            obstacleY = (int) Math.floor(Math.random() * (maxY - minY)) + minY;
        }
        canvas.drawCircle(obstacleX, obstacleY, 30, obstacle);

        for(int i=0; i<3; i++) {
            int x = (int) (580 + life[0].getWidth() * 1.5 * i);
            int y = 30;

            if(i < lives) {
                canvas.drawBitmap(life[0], x, y, null);
            }
            else {
                canvas.drawBitmap(life[1], x, y, null);
            }
        }
    }

    public boolean hitCoincheck(int x, int y) {
        return fishX < x && x < (fishX + fish.getWidth()) && fishY < y && y < (fishY + fish.getHeight());
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
