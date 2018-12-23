package flappy;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

public class Flappy implements ActionListener, MouseListener {
	int score=0;
	 public static Flappy flappy;
	 Dimension screenSize;
	 public Renderer renderer;
	 public int width,height,yMontion=0,ticks=0;
	 Random rand;
	 public Rectangle bird,lastColumn;
	 public ArrayList<Rectangle> columns;
	 boolean gameOver=false,start=false;
	int speed=8;
	 
public Flappy ()
{
	rand= new Random();
	screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	width =(int) screenSize.getWidth()/2 + (int) screenSize.getWidth()/4;
	height =  (int) screenSize.getHeight()/2 + (int) screenSize.getHeight()/4;
	
	JFrame jframe = new JFrame();
	renderer = new Renderer();
	Timer timer = new Timer(20,this);
	
	jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	jframe.setSize(width,height);
	jframe.addMouseListener(this);
	jframe.setVisible(true);
	jframe.setResizable(false);
	jframe.add(renderer);
	
	bird = new Rectangle(width/2-10,height/2-10,20,20);
	lastColumn=bird;
	columns = new ArrayList<Rectangle>();
	
	addColumn(true);
	addColumn(true);
	addColumn(true);
	addColumn(true);
	addColumn(true);
	addColumn(true);
	
	timer.start();
	
	
}

public void addColumn (boolean start)
{
	int randomSpaceheight=250+rand.nextInt(50)-speed;
	int randomSpace=200+rand.nextInt(100);
	int randomWidth=50+rand.nextInt(50);
	int randomheight=50+rand.nextInt(300);
	
	
	if(start) {
		columns.add(new Rectangle(width + randomWidth + columns.size() * 300, height - randomheight - 150, randomWidth , randomheight));
		columns.add(new Rectangle(width + randomWidth + (columns.size()-1)*300, 0 , randomWidth ,height - randomheight - randomSpaceheight));
	}
	else {
		columns.add(new Rectangle(columns.get(columns.size()-1).x + randomSpace*2, height - randomheight - 150, randomWidth , randomheight));
		columns.add(new Rectangle(columns.get(columns.size()-1).x, 0 , randomWidth ,height - randomheight - randomSpaceheight));
	}
	}



public void paintColumn (Graphics g, Rectangle column) {
	g.setColor(Color.green.darker());
	g.fillRect(column.x, column.y, column.width, column.height);
}
	
public void repaint(Graphics g) {
	

	g.setColor(Color.cyan);
	g.fillRect(0, 0, width, height);
	
	g.setColor(Color.orange);
	g.fillRect(0, height-150, width, 150);
	
	g.setColor(Color.green);
	g.fillRect(0, height-150, width, 20);
	
	g.setColor(Color.red);
	g.fillRect(bird.x, bird.y, bird.width, bird.height);
	
	for(Rectangle column : columns)
	{
		paintColumn(g, column);
	}
	
	g.setColor(Color.white);
	g.setFont(new Font ("Arial",1,100));
	
	g.drawString(String.valueOf(score), width/2, 90);
	if(!start) {
		g.drawString("Click to start", width/2 - 250, height/2);
	}
	if(gameOver)
	{
		g.drawString("Game Over", width/2 - 250, height/2);
	}
	
}

@Override
public void actionPerformed(ActionEvent arg0) {

	speed = score/10 + 8; 

	if(start) {
	ticks++;
	
	for (int i =0 ; i<columns.size();i++)
	{
		Rectangle column = columns.get(i);
		
		column.x -= speed;
	}
	
	if(ticks % 2 == 0 && yMontion < 15)
	{
		yMontion += 2;
	}
	
	bird.y += yMontion;
	
	for(Rectangle column : columns)
	{
		if(column.y== 0 && bird.x + bird.width / 2 > column.x + column.width / 2 - 5 &&  bird.x + bird.width / 2 < column.x + column.width / 2 + 5)
			score++;
		if(column.intersects(bird)) {
		gameOver=true;
		lastColumn = column;
	}
	}
	
	if(bird.y<0 || bird.y > height - 170) {
	gameOver=true;	
	bird.y=height-150-bird.height;
	}
	
	if(gameOver)
	bird.x=lastColumn.x-bird.width;
	
	renderer.repaint();

	for (int i =0 ; i<columns.size();i++)
	{
		Rectangle column = columns.get(i);
		
		if(column.x + column.width < 0)
		{
			columns.remove(column);
			
			if(column.y==0)
				addColumn(false);
		}
	}
	
	}
	
}

public void jump ()
{
	if(gameOver)
	{
		
		columns.clear();
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		addColumn(true);
		bird = new Rectangle(width/2-10,height/2-10,20,20);
		lastColumn=bird;
		gameOver=false;
		start=false;
		yMontion = 0;
		score=0;
		renderer.repaint();
	}
	else
	{	
	if(!start)
		start=true;
	else
	{
	if(yMontion > 0) 
	yMontion = 0;
	yMontion -= 10;
	
	}
	}
}

public static void main (String [] Args) {
	flappy = new Flappy();
	
}

@Override
public void mouseClicked(MouseEvent arg0) {

	//jump();
	
}

@Override
public void mouseEntered(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mouseExited(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}

@Override
public void mousePressed(MouseEvent arg0) {
	jump();
	
}

@Override
public void mouseReleased(MouseEvent arg0) {
	// TODO Auto-generated method stub
	
}




}