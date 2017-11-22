package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


/**
 * Created by brentaureli on 8/17/15.
 */
public class Hud implements Disposable{

    //Scene2D.ui Stage and its own Viewport for HUD
    public Stage stage;
    private Viewport viewport;

    //Mario score/time Tracking Variables
    private boolean timeUp; // true when the world timer reaches 0
    private static Integer score;
    private static Integer hp;

    //Scene2D widgets    
    private static Label scoreLabel;   
    private static Label hpLabel;
    private static Label hpLabelNome;
    private static Label scoreLabelNome;   

    public Hud(SpriteBatch sb){
        //define our tracking variables        
        score = 0;
        hp = 100;

        //setup the HUD viewport using a new camera seperate from our gamecam
        //define our stage using that viewport and our games spritebatch
        viewport = new FitViewport(ExGame.V_LARG, ExGame.V_ALT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        //define a table used to organize our hud's labels
        Table table = new Table();
        //Top-Align table
        table.top();
        //make the table fill the entire stage
        table.setFillParent(true);

        //define our labels using the String, and a Label style consisting of a font and color
       
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));       
        hpLabel = new Label(String.format("%03d", hp), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabelNome = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));       
        hpLabelNome = new Label("HP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        //add our labels to our table, padding the top, and giving them all equal width with expandX
        table.add(hpLabelNome).expandX().padTop(10);        
        table.add(scoreLabelNome).expandX().padTop(10);   
        //add a second row to our table
        table.row();
        table.add(hpLabel).expandX(); 
        table.add(scoreLabel).expandX();        

        //add our table to the stage
        stage.addActor(table);

    }

    /*public void update(float dt){
        timeCount += dt;
        if(timeCount >= 1){
            if (worldTimer > 0) {
                worldTimer--;
            } else {
                timeUp = true;
            }
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }*/

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}