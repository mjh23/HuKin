package com.example.hukin.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.hukin.MainActivity;
import com.example.hukin.R;

public class GameArena extends SurfaceView implements SurfaceHolder.Callback {

    //MainThread that the game loop runs through
    private MainThread thread;
    private Activity gameArenaHolder;

    //Gets the screenWidth and screenHeight of devices, useful for draw method below
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    // r is a variable used in the helper method drawCenterTextMod
    private Rect r = new Rect();

    //Whether the game is over
    private boolean gameOver = false;

    //Keeps track of elapsed game time. Useful for setting delays, etc.
    private long elapsedTime = 0;

    // Maybe keep track of a roundOver variable?

    /*
    public constructor for GameArena.
     */

    //These variables describe the location of the boundaries of the arena space where the player moves
    private int leftBound;
    private int rightBound;
    private int topBound;
    private int bottomBound;

    public GameArena(Context context) {
        //Set up game loop
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameArenaHolder = (Activity) context;

        //Initializing values start here...
        //
        //

    }

    /*
    public constructor for GameArena if player has already started game.
     */
    public GameArena(Context context, SavedData data){
        //Set up game loop
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);

        // Initializing values start here...
        //
        //

    }

    public void reset(){ //Driver class info here and in GamePanel initialize
        /*
        This method to be called when player wants to continue to next round.
        We want to reinitialize values for the new round.
        (i.e. generate enemies)
         */
    }

    /*
    Player interaction with screen gets processed here.
    MotionEvent event is the data of user interaction.
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                //Sets x and y positions of where the user tapped
                int x = (int) event.getX();
                int y = (int) event.getY();

                //Placement for demo currently, to be moved within if(gameOver), upper left menu, or removed
                if(x>=screenWidth/2-200&&x<=screenWidth/2+200&&y>=screenHeight/2+800&&y<=screenHeight/2+900) {
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    getContext().startActivity(intent);
                    gameArenaHolder.finish();
                }

                //Checks if upper left menu was pressed
                if(x>=50&&x<=200&&y>=50&&y<=170) {
                    Toast.makeText(getContext(), "Upper Left Menu was pressed!", Toast.LENGTH_SHORT).show();
                }

                if (gameOver) {
                    //When user taps after game is over.
                }
                break;
        }
        return true;
    }

    /*
    Method for live updates on screen, being called several times in milliseconds.
    Put
     */
    public void update() {
        //Elapsed time incremented here by 1
        elapsedTime++;

        if (gameOver) {
            //When game is over
        }
    }

    /*
    Draws pixels to the screen.
    This is for the visual component of game loop.
     */
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        //Set background color
        Paint paintBack = new Paint();
        int color = getResources().getColor(R.color.colorPrimary);
        paintBack.setColor(color);
        paintBack.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, screenWidth, screenHeight, paintBack);

        //Prepares text color/size
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(80f);

        //Drawing an arena space for the avatar and enemies to move around in.
        leftBound = 50;
        rightBound = screenWidth - 50;
        topBound = 200;
        bottomBound = screenHeight - 300;
        drawArena(canvas);

        if (gameOver) {
            //When game is over
        }

        //Display Elapsed Time
        drawCenterTextMod(canvas, paint, "ElapsedTime: " + elapsedTime, 0, (-screenHeight / 2 + 95));

        //Draws upper left menu
        drawMenu(canvas);

        //Drawing Main Menu Button to return to main menu
        //(Once rounds are set, this should be put inside an if statement
        // that checks when rounds are over/game over
        Paint rectPaint = new Paint();
        rectPaint.setColor(Color.WHITE);
        canvas.drawRect(screenWidth/2 - 200,screenHeight/2 + 800,screenWidth/2 + 200,screenHeight/2 + 900, rectPaint);
        paint.setTextSize(80);
        drawCenterTextMod(canvas,paint,"Main menu",0,850);
    }

    private void drawArena(Canvas canvas) {
        Paint arenaColor = new Paint();
        arenaColor.setColor(Color.WHITE);
        canvas.drawRect(leftBound, topBound, rightBound, bottomBound, arenaColor);
        arenaColor.setStrokeWidth(10);
        arenaColor.setStyle(Paint.Style.STROKE);
        arenaColor.setColor(Color.BLACK);
        canvas.drawRect(leftBound, topBound, rightBound, bottomBound, arenaColor);
    }


    /*
    Helper method to draw center text.
     */
    private void drawCenterTextMod(Canvas canvas, Paint paint, String text, int plusX, int plusY) {
        paint.setTextAlign(Paint.Align.LEFT);
        canvas.getClipBounds(r);
        int cHeight = r.height();
        int cWidth = r.width();
        paint.getTextBounds(text, 0, text.length(), r);
        float x = cWidth / 2f - r.width() / 2f - r.left;
        float y = cHeight / 2f + r.height() / 2f - r.bottom;
        canvas.drawText(text, x+plusX, y+plusY, paint);
    }

    /*
    To save data between rounds / when game is over
     */
    public void saveData(){

    }

    //Everything below is just to set up game loop

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        thread = new MainThread(getHolder(), this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(Exception e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    // Gets the left bound of the arena space.
    public int getLeftBound() {
        return leftBound;
    }

    // Gets the right bound of the arena space.
    public int getRightBound() {
        return rightBound;
    }

    // Gets the top bound of the arena space.
    public int getTopBound() {
        return topBound;
    }

    // Gets the bottom bound of the arena space.
    public int getBottomBound() {
        return bottomBound;
    }

    //Draws upper left menu
    private void drawMenu(Canvas canvas) {
        //Draws upper left menu/options button with border
        Paint buttonColor = new Paint();
        buttonColor.setColor(getResources().getColor(R.color.colorPrimaryDark));
        int top = 50;
        int right = 200;
        int bottom = 170;
        canvas.drawRect(leftBound, top, right, bottom, buttonColor);
        buttonColor.setStyle(Paint.Style.STROKE);
        buttonColor.setColor(Color.BLACK);
        buttonColor.setStrokeWidth(10);
        canvas.drawRect(leftBound, top, right, bottom, buttonColor);
        //Three lines for visual effect
        buttonColor.setStyle(Paint.Style.FILL);
        canvas.drawRect(leftBound + 20, top + 25, right - 20, top + 35, buttonColor);
        canvas.drawRect(leftBound + 20, top + 55, right - 20, top + 65, buttonColor);
        canvas.drawRect(leftBound + 20, top + 85, right - 20, top + 95, buttonColor);
    }
}
