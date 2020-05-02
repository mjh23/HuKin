package com.example.hukin.Logic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.hukin.MainActivity;
import com.example.hukin.R;

public class GameArena extends SurfaceView implements SurfaceHolder.Callback {

    MediaPlayer click;

    //MainThread that the game loop runs through
    private MainThread thread;
    private Activity gameArenaHolder;

    //Gets the screenWidth and screenHeight of devices, useful for draw method below
    private int screenWidth = Resources.getSystem().getDisplayMetrics().widthPixels;
    private int screenHeight = Resources.getSystem().getDisplayMetrics().heightPixels;

    //Storing all game sprites shown
    private CharacterSprites playerSprite;
    private CharacterSprites enemySprite;

    // r is a variable used in the helper method drawCenterTextMod
    private Rect r = new Rect();

    //Whether the game is over
    private boolean gameOver = false;

    //Keeps track of elapsed game time. Useful for setting delays, etc.
    private long elapsedTime = 0;

    //Turns true when the user taps the upper left menu
    private boolean isUpperMenuSelected = false;
    private PlayerStatus player;
    private Canvas canvas = new Canvas();
    private Enemy[] enemies;
    // Maybe keep track of a roundOver variable?

    /*
    public constructor for GameArena.
     */

    //These variables describe the location of the boundaries of the arena space where the player moves
    public static int leftBound;
    public static int rightBound;
    public static int topBound;
    public static int bottomBound;


    public GameArena(Context context) {
        //Set up game loop
        super(context);
        getHolder().addCallback(this);
        thread = new MainThread(getHolder(), this);
        setFocusable(true);
        gameArenaHolder = (Activity) context;

        leftBound = 50;
        rightBound = screenWidth - 50;
        topBound = 200;
        bottomBound = screenHeight - 300;
        //If user had saved data, initialize those values
        if (SavedData.isOldGame) {
            elapsedTime = SavedData.elapsedTime;
            playerSprite = SavedData.player.getSprite();
            player = SavedData.player;
            player.move = SavedData.movePlayer;
        } else {
            setPlayerSprite();
            enemySprite = new CharacterSprites(BitmapFactory.decodeResource(getResources(), R.drawable.char_dark_armor));
            player = new PlayerStatus(SavedData.role, playerSprite, canvas);

        }
        levelEnemies n1 = new levelEnemies(0, 1, enemySprite, canvas, player);
        levelEnemies n2 = new levelEnemies(0, 1, enemySprite, canvas, player);
        levelEnemies n3 = new levelEnemies(0, 1, enemySprite, canvas, player);
        levelEnemies n4 = new levelEnemies(0, 1, enemySprite, canvas, player);
        levelEnemies[] arr = {n1, n2, n3, n4};
        enemies = WaveGenerator.wave(arr);


        //Prepares click sound if sound effects are turned on
        click = MediaPlayer.create(gameArenaHolder, R.raw.click);

        //Initializing values start here...
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
        //Sets x and y positions of where the user tapped
        int x = (int) event.getX();
        int y = (int) event.getY();

        switch(event.getAction()){
            //For one taps
            case MotionEvent.ACTION_DOWN:

                //If the upper menu was opened
                if (isUpperMenuSelected) {
                    //Check to see if the user is tapping within the menu, otherwise return to the game
                    if (x>=screenWidth/2-400&&x<=screenWidth/2+400&&y>=screenHeight/2-400&&y<=screenHeight/2+400) {
                        //Return to main menu button
                        if(x>=screenWidth/2-200&&x<=screenWidth/2+200&&y>=screenHeight/2-300&&y<=screenHeight/2-200) {
                            if (SavedData.soundEffOn) {
                                clickSound();
                            }

                            Intent intent = new Intent(getContext(), MainActivity.class);
                            getContext().startActivity(intent);
                            gameArenaHolder.finish();
                        }
                    } else {
                        isUpperMenuSelected = false;
                    }
                }

                //Checks if upper left menu was pressed
                if(!isUpperMenuSelected && (x>=50&&x<=200&&y>=50&&y<=170)) {
                    if (SavedData.soundEffOn) {
                        clickSound();
                    }

                    Toast.makeText(getContext(), "Upper Left Menu was pressed!", Toast.LENGTH_SHORT).show();
                    isUpperMenuSelected = true;
                }
                if (!isUpperMenuSelected && ((x > leftBound && x < rightBound) && (y < bottomBound && y > topBound))) {
                    player.move.setTarg(x, y);
                    for (Enemy enemy : enemies) {
                        enemy.move.setTarg(x, y);
                    }
                    //Movement.move(player, x - 64, y - 64, canvas, playerSprite);
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
     */
    public void update() {
        saveData();
        if (isUpperMenuSelected) {
            //Does not update anything on screen
            return;
        }
        //Elapsed time incremented here by 1
        elapsedTime++;
        player.move.move2();
        Movement.massMover(enemies);

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
        drawArena(canvas);

        if (gameOver) {
            //When game is over
        }

        //Draw all sprites here
        playerSprite.draw(canvas, player.getX() , player.getY());
        for (Enemy d : enemies) {
            enemySprite.draw(canvas, d.getX(), d.getY());
        }

        //Display Elapsed Time
        drawCenterTextMod(canvas, paint, "" + elapsedTime + "\n " + SavedData.characterName, 0, (-screenHeight / 2 + 95));

        //Draws upper left menu
        drawMenu(canvas);

        //When upper menu is pressed, game should look paused and pop up of options should appear
        if (isUpperMenuSelected) {
            drawUpperMenuPopout(canvas);
        }

        if (isUpperMenuSelected) {
            //Drawing Main Menu Button to return to main menu
            //(Once rounds are set, this should be put inside an if statement
            // that checks when rounds are over/game over
            Paint rectPaint = new Paint();
            rectPaint.setColor(Color.WHITE);
            canvas.drawRect(screenWidth / 2 - 200, screenHeight / 2 - 300, screenWidth / 2 + 200, screenHeight / 2 - 200, rectPaint);
            paint.setTextSize(80);
            drawCenterTextMod(canvas, paint, "Main menu", 0, -250);
        }
    }

    //Draws the popout when the upper left menu is opened
    private void drawUpperMenuPopout(Canvas canvas) {
        Paint shade = new Paint();
        shade.setColor(Color.GRAY);
        shade.setAlpha(200);
        canvas.drawRect(0, 0, screenWidth, screenHeight, shade);
        Paint menu = new Paint();
        menu.setColor(getResources().getColor(R.color.colorPrimaryDark));
        menu.setStyle(Paint.Style.FILL);
        canvas.drawRect(screenWidth/2 - 400, screenHeight/2 - 400, screenWidth/2 + 400, screenHeight/2 + 400, menu);
        menu.setColor(Color.BLACK);
        menu.setStyle(Paint.Style.STROKE);
        menu.setStrokeWidth(10);
        canvas.drawRect(screenWidth/2 - 400, screenHeight/2 - 400, screenWidth/2 + 400, screenHeight/2 + 400, menu);
    }

    //Draws the white arena with a black border
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
        SavedData.isOldGame = true;
        SavedData.elapsedTime = elapsedTime;
        SavedData.player = player;
        SavedData.movePlayer = player.move;
    }

    private void setPlayerSprite() {
        switch (SavedData.role) {
            case 0:
                playerSprite = new CharacterSprites(BitmapFactory.decodeResource(getResources(), R.drawable.char_armor));
                break;
            case 1:
                playerSprite = new CharacterSprites(BitmapFactory.decodeResource(getResources(), R.drawable.ranger));
                break;
            case 2:
                playerSprite = new CharacterSprites(BitmapFactory.decodeResource(getResources(), R.drawable.wizard));
                break;
            case 3:
                playerSprite = new CharacterSprites(BitmapFactory.decodeResource(getResources(), R.drawable.bard));
                break;
        }
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
        //Release mediaplayer to free memory
        click.release();

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

    //Plays click sound effect
    private void clickSound() {
        try {
            click.stop();
            click.prepare();
            click.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
