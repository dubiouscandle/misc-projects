package main;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import enemies.Enemy;
import particles.Particle;
import projectiles.Projectile;
import projectiles.Turret;
import util.Util;

public class GamePanel extends JPanel {	
	private static final long serialVersionUID = 1L;
	
	private GameState gameState;
	public static final int SCREEN_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().height-100;
	public static final int SCREEN_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height-100;
	public static final double SCALE_FACTOR_X = (double)SCREEN_WIDTH/GameState.GRID_WIDTH/2;
	public static final double SCALE_FACTOR_Y = (double)SCREEN_HEIGHT/GameState.GRID_HEIGHT/2;

	private static final int DEFAULT_TURRET_LENGTH = 1;
	
	private static final Font PLAY_AGAIN_FONT = new Font("Courier New", 0, SCREEN_WIDTH/40);
	private static final Font PLAYER_CENTER_FONT = new Font("Courier New", 0, SCREEN_WIDTH/35);
	private static final Font WIN_LOSE_TEXT_FONT = new Font("Courier New", 0, SCREEN_WIDTH/20);
	
    private static final String PLAY_AGAIN_TEXT = "Click to play again.";

	private static final String[] deathMessages = {
		"There is an unnecessary amount\nof death lines in this game.",
		"Went out with a bang.", 
		"It's funny to watch you\ndie again and again.",
		"WASTED",
		"OBLITERATED",
		"BRUTALITY",
		"Objective failed.",
		"My bad.",
		"One shotted lol",
		"Git gud",
		"Skill issue?",
		"Dev needs to fix this",
		"gg ez",
		"Thats it?",
		"Here we go again...",
		"Are you okay?",
		":clown:",
		"bro...",
		"This is embarrasing.",
		"Try not to ragequit.",
		"Lol.",
		"Hahaha",
		"K.O.!",
		"FATALITY",
		"Oof",
		"-15 respect",
		"You died",
		"AAAAHHHHHHHHHHHHHHHH",
		"Tip: Don't die.",
		"Have you tried NOT dying?",
		"The end.",
		"Hello, player.",
		"Perhaps I should nerf that...",
		"Misclick?",
		"You died that easily?",
		"I expected a little\nmore, but okay.",
		"Oops.",
		"Sample text here",
		"bad",
		"noob",
		"Do I have to turn\non baby mode?",
		//"HAHHAHHAAHHHAAHAHAAAHAHAHAAHAAAHAAHHAAHA\nAAHAAAHAAAAAAAHAAHHAHHAAAAAHHHHHAAAHHAAH\nHAAAAHAAAHHHHHHAHHAAHHAHHHAAHAHAAHHAAAAH\nAAHAHHAHAAAAAAHAHHAHHHHHHAAAHAAHHHAAAHAH\nAAAHAHAAAHHHAHAAAHAAHHAAAAHHHAHAAHAHAHAA\nAAAHHAHAHHAHAAHHAHHAHAHHAHHHHAHHAAHHAHHA\nAAAHHAHHHHHAHAAHHHAHAAHAAAHAHAAAHHHHAHAH\nAHHHHHAAHHHHAHAAHAHAHAHAAHAAAHAHHAAAAAHA\nHHAHAAHHHAAAAHHAHAHAHHAAHHAHHAAHAAAAHAHH\nHAAHHAAAHHAAAHAHAHHHHAHAHHHHAHAAAHHAHAHH\nHAHAAAAHHAAHAAHHAAAAAHHAAHHHHAHAHHAAAAAH\nAHAAAHHHAAHHAAAHAHAHAAAHHAHAHHAAAHAHHHHA\nHHHHHAHAHAAAAHAAAHHHHAHAAHAHHAAHHAHHAHAA\nAHHHAAAAHAAHHHAAHHHAHAAAHHHHAHAAAAAAHAAA\nHAAHAAHAHAAAAHHHHHHHAAHHAAHAAHHHAAHHAHHA\nAHAAAAAAHAAAAHAAAAHHAHAHAAAAAAHHAAHHHAAA\nAHAAAHAAHAHHAHAAHAAHAHHAHHAHHHHAAHHAAHAA\nAAHHHAAHHHHAHHAAAAAHHAAAHHHAHHHAHAHAHAAH\nAAAAHHAAHAAAHHHAAAAAHHAHAHHAAHAHHAHAAAHH\nAHHHHAAAAAHAHAAHHHHHAAHAHAAHHAAHHAHHHHAH\nAHHAAHAHHAAHHHAAHAAHHAAHAAHAAAAAAHHAHAAA\nHHHAAHAHAAAHHHHHAAAHHHAHAHAHAAAHHHAAHHAH\nHHAHHAHAAAAHHAAAAAAAAAAAAHHAHAHAAAHAAAHH\nHAHHHHAAAHAHHHHHAAAHAAHHAAAAHHHHHAAAAAHA\nHHAHHHHHHAAAHAAHAHHHAAHHAAAHAAHHAHHHHAHA\nHHAAHHAAAHAAAAHAAHHAAAAAHAHHHHAHAHAHAAAA\nAHHAAHHHAHAAAHAHAAHAAAHAAAHAAAHHHHAHHHHA\nAAAAHAHHAHAAHHHHHHAAAAHHHAAAAHAHAAAAAAHA\nAHAAAAHHHAHAHHHHAHHHHAHHAAAAHHHHAHAAHHHH\nAAHHHAHAAHAAAHAAAHHAAAHHHAAHAAAAAHAHAAAA\nAHHHHHHAHHAAAHAAAAHHHAHAHAHHHAHHAHHHHHHA\nHAHHHHHAAAAHAAAHAHAAAAHAHAHHAAHAAHAAAAAA\nHAHHHAHHHHAAAHAHAHAHHHAHAHAAAHAAHHHHAHHA\nAAHHHHAHAAAHHHAAAAAHHHAHHAHHHHAHAHAHAHAA\nHHHAAAAAHHHHHAHHAHHAAAAAHAHAHHHAAHHAAHHH\nHHAAHHAHHAAAHAHAAAHHAHHAHHHHAAHAHHAHAHHH\nAAHAHHAAHAAAHHHHHHHHAAAHHAAHAAAAHAHAAHHH\nHAHHHAHHHHAHAHHAAAAAHHAAHHHHAAHHAHAAAHAH\nAAHAHAAHAHAAAHAHAAAHHAHHHAHHHAHAHHHHHHAH\nAAAAAAHHHAHHHHAAAAAAHHHAAHAAAHAAHHAAAHAA\n",
	};
	private static int deathMessageIndex;
	
	//color pallete: https://lospec.com/palette-list/st-24
	private static final Color WIN_LOSE_TEXT_COLOR = Color.decode("#ba41d9");
	private static final Color BACKGROUND_COLOR = Color.decode("#111126");
	private static final Color PLAYER_COLOR = Color.decode("#ae42c9");
	private static final Color TURRET_COLOR = Color.decode("#de73e5");
	private static final Color PLAYER_SCORE_COLOR = Color.decode("#3e2680");
	private static final Color GRID_LINE_COLOR = Color.decode("#3e2680");
	private static final Color ENEMY_MARKER_COLOR = Color.decode("#ffffff");

	private Stroke DEFAULT_STROKE = new BasicStroke(2);
	
	public GamePanel(GameState gameState){
		//passes reference to GameState so that GamePanel knows where to draw things
		this.gameState = gameState;
		
		setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
	}
	
	//where most of the stuff happens
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        g2.setStroke(DEFAULT_STROKE);
        
        drawBackground(g2);
        drawEnemies(g2);
        drawProjectiles(g2);
        drawTurrets(g2);
        
        drawPlayer(g2);
        
        drawParticles(g2);
        
		//win/lose text
	    if(gameState.gameEnded) {
	    	if(deathMessageIndex == -1) {
	    		deathMessageIndex = (int)((deathMessages.length-1)*Math.random());
	    	}
	    	
    	    g2.setColor(WIN_LOSE_TEXT_COLOR);
			g2.setFont(WIN_LOSE_TEXT_FONT);
    	    FontMetrics metrics1 = g2.getFontMetrics(WIN_LOSE_TEXT_FONT);

    	    String message;
    	    
    	    if(gameState.playerWon) {
    	    	if(gameState.cheated) {
    	    		message = "cheater";
    	    	}else {
    	    		message = "The dev beat it first try but\nyou still win i guess";
    	    	}
	    	}else {
	    		if(gameState.deaths == 0) {
	    			message = "Click to start";
	    		}else if(gameState.getLevel() == 0) {
	    			message = "bro how did you lose\non the first level";
	    		}else {
		    		message = deathMessages[deathMessageIndex];
	    		}
	    	}
    	    
    	    //basically all this draws the text so that \n works with drawString
    	    String[] text = message.split("\n");
    	    
    	    int y = (SCREEN_HEIGHT - metrics1.getHeight() * text.length) / 2 + metrics1.getAscent();
    	    for(String t : text) {
    	    	g2.drawString(t,SCREEN_WIDTH/2 - metrics1.stringWidth(t)/2, y);
    	        y += metrics1.getHeight();
    	    }

    	    if(gameState.deaths != 0) {
        	    g2.setFont(PLAY_AGAIN_FONT);
        	    FontMetrics metrics2 = g2.getFontMetrics(PLAY_AGAIN_FONT);
    	    	g2.drawString(PLAY_AGAIN_TEXT, SCREEN_WIDTH/2 - metrics2.stringWidth(PLAY_AGAIN_TEXT)/2, SCREEN_HEIGHT * 19/21);
    		}
    	    
	    }else{
	    	deathMessageIndex = -1;
	    }
	    
	    g.dispose();
	}
  
    
    private void drawParticles(Graphics2D g2) {
        for(int i = 0; i < gameState.getParticles().size(); i++) {   
            Particle particle = gameState.getParticles().get(i);
            
            g2.setColor(particle.color);
            g2.fillOval(convertToScreenX(particle.getX() - particle.radius), convertToScreenY(particle.getY() + particle.radius), Util.round(particle.radius * SCALE_FACTOR_X), Util.round(particle.radius * SCALE_FACTOR_Y));
            g2.setColor(Color.BLACK);
            g2.drawOval(convertToScreenX(particle.getX() - particle.radius), convertToScreenY(particle.getY() + particle.radius), Util.round(particle.radius * SCALE_FACTOR_X), Util.round(particle.radius * SCALE_FACTOR_Y));
        
        }

    }

    private void drawPlayer(Graphics2D g2) {
        g2.setColor(PLAYER_COLOR);
		g2.fillOval(convertToScreenX(gameState.getPlayer().getX()-gameState.getPlayer().getRadius()), convertToScreenY(gameState.getPlayer().getY()+gameState.getPlayer().getRadius()), Util.round(gameState.getPlayer().getRadius()*SCALE_FACTOR_X*2), Util.round(gameState.getPlayer().getRadius()*SCALE_FACTOR_Y*2));
    	g2.setColor(Color.BLACK);
		g2.drawOval(convertToScreenX(gameState.getPlayer().getX()-gameState.getPlayer().getRadius()), convertToScreenY(gameState.getPlayer().getY()+gameState.getPlayer().getRadius()), Util.round(gameState.getPlayer().getRadius()*SCALE_FACTOR_X*2), Util.round(gameState.getPlayer().getRadius()*SCALE_FACTOR_Y*2));

	    g2.setFont(PLAYER_CENTER_FONT);
	    g2.setColor(PLAYER_SCORE_COLOR);
	    FontMetrics metrics = g2.getFontMetrics(PLAYER_CENTER_FONT);
	    String playerCenterInfo = gameState.getPointsUntilNextLevel() + "";
	    g2.drawString(playerCenterInfo, convertToScreenX(gameState.getPlayer().getX()) - metrics.stringWidth(playerCenterInfo)/2, convertToScreenY(gameState.getPlayer().getY()) + metrics.getMaxAscent()/2-2);
    	

    }
    private void drawTurrets(Graphics2D g2) {
    	AffineTransform old = g2.getTransform();
    	AffineTransform transformation;
    	
        for(int i = 0; i < gameState.getTurrets().size(); i++) {   
            Turret turret = gameState.getTurrets().get(i);
            
            transformation = g2.getTransform();
            
            transformation.setToTranslation(convertToScreenX(gameState.getPlayer().getX()), convertToScreenY(gameState.getPlayer().getY()));
            transformation.rotate(-turret.getAngle());
            
            g2.transform(transformation);
                        
            //draw the rectangle
	          g2.setColor(TURRET_COLOR);
	          g2.fillRect(0, -Util.round(SCALE_FACTOR_Y * turret.getProjectileAttributes().radius), Util.round(DEFAULT_TURRET_LENGTH * SCALE_FACTOR_X), Util.round(SCALE_FACTOR_Y * turret.getProjectileAttributes().radius * 2));
	          g2.setColor(Color.BLACK);
	          g2.drawRect(0, -Util.round(SCALE_FACTOR_X * turret.getProjectileAttributes().radius), Util.round(DEFAULT_TURRET_LENGTH * SCALE_FACTOR_X), Util.round(SCALE_FACTOR_X * turret.getProjectileAttributes().radius * 2));
            
            g2.setTransform(old);
        }

    }
    private void drawBackground(Graphics2D g2) {
        //background
    	g2.setColor(BACKGROUND_COLOR);
        g2.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);
        
        //draw the x and y axises
    	g2.setColor(Color.BLACK);
    	g2.drawLine(SCREEN_WIDTH/2, 0, SCREEN_WIDTH/2, SCREEN_HEIGHT);
    	g2.drawLine(0, SCREEN_HEIGHT/2, SCREEN_WIDTH, SCREEN_HEIGHT/2);
    	

    	//draw the grid lines
    	g2.setColor(GRID_LINE_COLOR);
        for(int i = -GameState.GRID_WIDTH; i < GameState.GRID_WIDTH+1; i++) {
        	g2.drawLine(convertToScreenX(i), 0, convertToScreenX(i), SCREEN_HEIGHT);
        }
        for(int i = -GameState.GRID_HEIGHT; i < GameState.GRID_HEIGHT+1; i++) {
        	g2.drawLine(0, convertToScreenY(i), SCREEN_WIDTH, convertToScreenY(i));
        }

    }
    private void drawEnemies(Graphics2D g2) {    
        AffineTransform old = g2.getTransform();
        AffineTransform transformation;

        for(int i = 0; i < gameState.getEnemies().size(); i++) {
            Enemy enemy = gameState.getEnemies().get(i);
            
            transformation = g2.getTransform();
            
            transformation.setToTranslation(convertToScreenX(enemy.getX()), convertToScreenY(enemy.getY()));
            transformation.rotate(enemy.angle);
            
            g2.transform(transformation);
            
            int width = Util.round(enemy.getRadius() * SCALE_FACTOR_X * 2);
            int height = Util.round(enemy.getRadius() * SCALE_FACTOR_Y * 2);
            
            //draw the rectangle
            if(enemy.levelSpawnedIn != gameState.getLevel()) {
            	g2.setColor(ENEMY_MARKER_COLOR);
            }else {
            	g2.setColor(enemy.getColor());
            }
            g2.fillRect(-width/2, -height/2, width, height);
            g2.setColor(Color.BLACK);
            g2.drawRect(-width/2, -height/2, width, height);
            
            //maybe still useful but probably not anyways it is just gonna live here in the code now
//            	g2.fillOval(-width/4, -height/4, width/2, height/2);
//            	g2.setColor(Color.BLACK);
//            	g2.drawOval(-width/4, -height/4, width/2, height/2);
//            }
            
            g2.setTransform(old);
        }		
    }

	private void drawProjectiles(Graphics g) {
        for(int i = 0; i < gameState.getProjectiles().size(); i++) {   
        	Projectile projectile = gameState.getProjectiles().get(i);
        	
        	if(projectile == null) {
        		continue;
        	}
        	
            g.setColor(projectile.getAttributes().color);
        	g.fillOval(convertToScreenX(projectile.getX() - projectile.getAttributes().radius), convertToScreenY(projectile.getY() + projectile.getAttributes().radius), Util.round(projectile.getAttributes().radius * SCALE_FACTOR_X*2), Util.round(projectile.getAttributes().radius * SCALE_FACTOR_Y*2));
            g.setColor(Color.BLACK);
        	g.drawOval(convertToScreenX(projectile.getX() - projectile.getAttributes().radius), convertToScreenY(projectile.getY() + projectile.getAttributes().radius), Util.round(projectile.getAttributes().radius * SCALE_FACTOR_X*2), Util.round(projectile.getAttributes().radius * SCALE_FACTOR_Y*2));
        }
	}

	//util
    public static int convertToScreenX(double graphX) {
    	return Util.round(SCREEN_WIDTH/2+graphX * SCALE_FACTOR_X);
    }
    public static int convertToScreenY(double graphY) {
    	return Util.round(SCREEN_HEIGHT/2-graphY * SCALE_FACTOR_Y);
    }
    
    public static double convertToGridX(int screenX) {
    	return (screenX-SCREEN_WIDTH/2) / SCALE_FACTOR_X;
    }
    public static double convertToGridY(int screenY) {
    	return -(screenY-SCREEN_HEIGHT/2) / SCALE_FACTOR_Y;
    } 
    
    public void setGameState(GameState gameState) {
    	this.gameState = gameState;
    }
        
}
