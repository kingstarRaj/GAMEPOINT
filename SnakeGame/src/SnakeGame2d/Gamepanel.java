
package SnakeGame2d;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;

//IMPLEMENTED THE ACTION LISTENERS TO PERFORM ACTION ON SNAKE BODY
//imlemented the keylisteners to controll the movement 
public class Gamepanel extends JPanel implements ActionListener,KeyListener{

    private int[]snakexlength = new int[750];
    private int[]snakeylength = new int[750];
    private int lengthofsnake = 3 ;
    
    //GIVING ARRYAS FOR ENEMY APPEARANCE
    private int[] xpos = {25,50,75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,500,525,550,575,600,625,650,675,700,725,750,775,800,825,850};
    private int[] ypos = {75,100,125,150,175,200,225,250,275,300,325,350,375,400,425,450,500,525,550,575,600,625};
    
    //getting a Random variable from 
    private Random random = new Random();
    private int enemyX,enemyY;
    
    private boolean left =false;
    private boolean right=true;
    private boolean up=false;
    private boolean down=false;
    
    private int moves=0;
    private int score=0;
    private boolean gameOver= false;
    
    //FOR ALL THE IMAGES TO GET IMPORTED AND TO USE THENM IN THE CODE
    private ImageIcon snaketitle = new ImageIcon(getClass().getResource("snaketitle.jpg"));
    private ImageIcon leftmouth = new ImageIcon(getClass().getResource("leftmouth.png"));
    private ImageIcon rightmouth = new ImageIcon(getClass().getResource("rightmouth.png"));
    private ImageIcon upmouth = new ImageIcon(getClass().getResource("upmouth.png"));
    private ImageIcon downmouth = new ImageIcon(getClass().getResource("downmouth.png"));
    private ImageIcon snakeimage = new ImageIcon(getClass().getResource("snakeimage.png"));
    private ImageIcon enemy = new ImageIcon(getClass().getResource("enemy.png"));
 
// FOR CHANGING POSITION AND CREATING ILLUSION FOR POS CHANGE    
    private Timer timer;
    private int delay = 150;
    Gamepanel (){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(true);
        
     //PROVIDING THE TIMER FOR THE DELAY SPEED OF SNAKE CALLS SNAKE EVERY 100MS 
        timer = new Timer(delay,this);
        timer.start();
        
     //setting the enemy position 
     newEnemy();
        
    }
    //PAINT PAINT PAINT
    @Override
    public void paint(Graphics g) {
        super.paint(g); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    
    g.setColor(Color.WHITE);
    g.drawRect(24, 10, 851, 55);
    g.drawRect(24, 74, 851, 576);
    
    snaketitle.paintIcon(this, g, 25, 11);
    g.setColor(Color.DARK_GRAY);
    g.fillRect(25, 70, 850, 575);
    
     if(moves==0)
     {
         snakexlength[0]=100;
         snakexlength[1]=75;
         snakexlength[2]=50;
         
         snakeylength[0]=100;
         snakeylength[1]=100;
         snakeylength[2]=100;
         
         //moves++;
         
     }
     
// DIRECTION OF MOUTH OF SNAKE
     
     if(left){
         leftmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
     }
     if(right){
         rightmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
     }
     if(up){
         upmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
     }
     if(down){
         downmouth.paintIcon(this, g, snakexlength[0], snakeylength[0]);
     }
     
//DRAWING WHOLE BODY OF SNAKE
     for(int i=1;i<lengthofsnake;i++)
     {
        snakeimage.paintIcon(this, g, snakexlength[i], snakeylength[i]);
     }
     
     enemy.paintIcon(this, g, enemyX, enemyY);
     
     if(gameOver){
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial",Font.BOLD,50));
         g.drawString("Game Over", 300, 300);
         
         g.setFont(new Font("Arial",Font.PLAIN,20));
         g.drawString("Press SPACE to Restart", 320, 350);
         
     }
     //PRINTING THE SCORE AND LENGTH OF SNAKE 
         g.setColor(Color.WHITE);
         g.setFont(new Font("Arial",Font.PLAIN,14));
         g.drawString("score :"+score, 750, 30);
         g.drawString("Length :"+lengthofsnake, 750, 50);
         
         
     
     g.dispose();
    
    }
    
//ACTION PERFORMED!! //ACTION PERFORMED
    @Override
    //GET IMPLEMENTED THE ACTIONLISTENER TO PERFORM THE MOVEMENT AND OPERATION ON THE SNAKE 
    public void actionPerformed(ActionEvent e) {
     
        //TO MOVE THE WHOLE BODY WITH THE SNAKE 
        for(int i = lengthofsnake-1;i>0;i--){
            snakexlength[i]=snakexlength[i-1];
            snakeylength[i]=snakeylength[i-1];   
        }
    
//TO MOVE THE SNAKES HEAD 

        if(left){
            snakexlength[0]=snakexlength[0]-25;
        }
        if(right){
            snakexlength[0]=snakexlength[0]+25;
        }
        if(up){
            snakeylength[0]=snakeylength[0]-25;
        }
        if(down){
            snakeylength[0]=snakeylength[0]+25;
        }
        
        //HERE TO REAPPEARENCE OF SNAKE AFTER DISSAPEEARING 
        if(snakexlength[0]>850)snakexlength[0]=25;
        if(snakexlength[0]<25)snakexlength[0]=850;
        if(snakeylength[0]>625)snakeylength[0]=75;
        if(snakeylength[0]<75)snakeylength[0]=625;
        
     //COLLISION WITH ENEMY 
         collideWithEnemy();
     //COLLISION WITH BODY
         collideWithBody();
     // RECALL THE PAINT METHODE FOR HEAD
         repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        //ALLOCATING THE FUNTIONING TO PARTICULAR BUTTONS AND PROVIDING THE ILLEGAL MOVES
        if(e.getKeyCode()==KeyEvent.VK_SPACE){
            restart();
        }
    //DENOTING BUTTON OF KEYBORED TO DIRECT THE MOVEMENT    
        if(e.getKeyCode()==KeyEvent.VK_LEFT && (!right)){
            left = true;
            right= false;
            up   = false;
            down = false;
            //AFTER PRESSING BUTTON SNAKE START TO MOVE 
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_RIGHT && (!left)){
            left = false;
            right= true;
            up   = false;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_UP && (!down)){
            left = false;
            right= false;
            up   = true;
            down = false;
            moves++;
        }
        if(e.getKeyCode()==KeyEvent.VK_DOWN && (!up)){
            left = false;
            right= false;
            up   = false;
            down = true;
            moves++;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
       
    }

    private void newEnemy() {
        enemyX=xpos[random.nextInt(34)];
        enemyY=ypos[random.nextInt(23)];
        
        //condition IF ENEMY OCCURS ON BODY OF SNAKE 
        for(int i =lengthofsnake-1; i>=0 ;i--){
            if(snakexlength[i]==enemyX && snakeylength[i]==enemyY){
                newEnemy();
            }
            
        }
    }

    private void collideWithEnemy() {
        if(snakexlength[0]==enemyX && snakeylength[0]==enemyY){
            newEnemy();
            lengthofsnake++;
            score++;
        }
    }

    private void collideWithBody() {
     for (int i= lengthofsnake-1; i>0; i--){
         if(snakexlength[i]==snakexlength[0] && snakeylength[i]==snakeylength[0]){
             timer.stop();
             gameOver=true;
         }
     }
         
   }

    private void restart() {
        gameOver=false;
        moves=0;
        score=0;
        lengthofsnake=3;
        left=false;
        right=true;
        up=false;
        down=false;
        timer.start();
        repaint();
    }
   
    
    
}
