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


public class Hud implements Disposable{

  
    public Stage stage;
    private Viewport viewport;

   
    private boolean timeUp; 
    private static Integer score;
    private static Integer hp;

    
    private static Label scoreLabel;   
    private static Label hpLabel;
    private static Label hpLabelNome;
    private static Label scoreLabelNome;   

    public Hud(SpriteBatch sb){
     
        score = 0;
        hp = 100;

       
        viewport = new FitViewport(ExGame.V_LARG, ExGame.V_ALT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

       
        Table table = new Table();
   
        table.top();
     
        table.setFillParent(true);

       
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));       
        hpLabel = new Label(String.format("%03d", hp), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabelNome = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));       
        hpLabelNome = new Label("HP", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

     
        table.add(hpLabelNome).expandX().padTop(10);        
        table.add(scoreLabelNome).expandX().padTop(10);   

        table.row();
        table.add(hpLabel).expandX(); 
        table.add(scoreLabel).expandX();        

        stage.addActor(table);

    }
   

    public static void addScore(int value){
        score += value;
        scoreLabel.setText(String.format("%06d", score));
    }
    public static int getScore(){
    	if(score != null) {
       return score; 
    	}
    	else {
    		return 0;
    	}
    }
    public static void levaDano(int value){
        hp -= value;
        hpLabel.setText(String.format("%03d", hp));
    }

    @Override
    public void dispose() { stage.dispose(); }

    public boolean isTimeUp() { return timeUp; }
}