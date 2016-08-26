import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

class  Quiz extends JFrame implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	JPanel panel;
	JPanel panelresult;
	JRadioButton choice1;
	JRadioButton choice2;
	JRadioButton choice3;
	JRadioButton choice4;
	ButtonGroup bg;
	JLabel lblmess;
	JButton btnext;
	String[][] qpa;
	String[][] qca;
	int qaid;
	HashMap<Integer, String> map;
	
	
	Quiz(){
		initializedata(); // Initialize questions, possible answers, and correct answers
		setTitle("Test"); // Set the title of the program window
		setDefaultCloseOperation(EXIT_ON_CLOSE); // Let the program window close when the user clicks the close button
		setSize(430,350); // Specify the width and height of the program window when it opens
		setLocation(300,100); // Specify the location of the program window when it opens
		setResizable(false); // Disable program window resizing
		Container cont=getContentPane(); // Create a container object to act as the controls placeholder
		cont.setLayout(null); // Set the layout of the container to null so that you can customize the locations of controls to place on it      
		cont.setBackground(Color.GRAY); // Set the background color of the container to gray
		bg=new ButtonGroup(); // Create the ButtonGroup object to hold the JRadioButton objects
		
		// Create four JRadioButton objects and add them to the ButtonGroup bg
		choice1=new JRadioButton("Choice1",true);
		choice2=new JRadioButton("Choice2",false);
		choice3=new JRadioButton("Choice3",false);
		choice4=new JRadioButton("Choice4",false);
		bg.add(choice1);
		bg.add(choice2);
		bg.add(choice3);
		bg.add(choice4);
		
		lblmess=new JLabel("Choose a correct anwswer"); // Create a JLabel object lblmess to display the question
		lblmess.setForeground(Color.BLUE); // Set the text color of the lblmess to blue
		lblmess.setFont(new Font("Arial", Font.BOLD, 11)); // Set the font name, font style, and font size of the lblshow label
		btnext=new JButton("Continue"); // Create the JButton next object to enable next question
		btnext.setForeground(Color.BLACK); // Set the text color of the next button to black               
		btnext.addActionListener(this); // Add action event to button to enable button-click action 
		panel=new JPanel(); 
		panel.setBackground(Color.LIGHT_GRAY);
		panel.setLocation(10,10);
		panel.setSize(400,300);
		panel.setLayout(new GridLayout(6,2));
		
		// Add all controls to the panel object.
		panel.add(lblmess);
		panel.add(choice1);
		panel.add(choice2);
		panel.add(choice3);
		panel.add(choice4);
		panel.add(btnext);
		cont.add(panel);
		setVisible(true);
		qaid=0;
		readqa(qaid);
		

	}
	
	// handle the button click event

	public void actionPerformed(ActionEvent e){

		if(btnext.getText().equals("Continue")){
			if(qaid<9){

				map.put(qaid,getSelection());
				qaid++;
				readqa(qaid);
			}
			else {
				map.put(qaid,getSelection());
				btnext.setText("Show Answers");

			}
		}
		else if(btnext.getText().equals("Show Answers"))
			new Report();


	}

// Initialize the qpa and qca arrays

	public void initializedata(){
		//qpa stores pairs of question and its possible answers
		qpa=new String[10][5];

		qpa[0][0]="Which planet is located closest to the Sun?";
		qpa[0][1]="Venus";
		qpa[0][2]="Mercury";
		qpa[0][3]="Mars";
		qpa[0][4]="Earth";

		qpa[1][0]="Which planet is often referred to as the Red Planet?";
		qpa[1][1]="Mars";
		qpa[1][2]="Mercury";
		qpa[1][3]="Earth";
		qpa[1][4]="Venus";

		qpa[2][0]="Which gas giant has an average radius 9 times that of Earth?";
		qpa[2][1]="Neptune";
		qpa[2][2]="Saturn";
		qpa[2][3]="Uranus";
		qpa[2][4]="Jupiter";

		qpa[3][0]="Which planet is located furthest from the sun";
		qpa[3][1]="Uranus";
		qpa[3][2]="Neptune";
		qpa[3][3]="Jupiter";
		qpa[3][4]="Saturn";

		qpa[4][0]="Which of the following planets is the densest in our solar system?";
		qpa[4][1]="Earth";
		qpa[4][2]="Mars";
		qpa[4][3]="Venus";
		qpa[4][4]="Jupiter";

		qpa[5][0]="Uranus is ____ times the mass of Earth.";
		qpa[5][1]="15";
		qpa[5][2]="5";
		qpa[5][3]="20";
		qpa[5][4]="25";

		qpa[6][0]="Venus orbits the sun once every _____ Earth days.";
		qpa[6][1]="365";
		qpa[6][2]="225";
		qpa[6][3]="105";
		qpa[6][4]="455";

		qpa[7][0]="Saturn is just over ____ times more massive than the Earth";
		qpa[7][1]="300";
		qpa[7][2]="95";
		qpa[7][3]="10";
		qpa[7][4]="250";

		qpa[8][0]="Earth was formed about __________ years ago";
		qpa[8][1]="454 billion";
		qpa[8][2]="5.45 billion";
		qpa[8][3]="100 million";
		qpa[8][4]="4.54 billion";

		qpa[9][0]="What is Jupiter primarily composed of?";
		qpa[9][1]="Helium";
		qpa[9][2]="Nitrogen";
		qpa[9][3]="Oxygen";
		qpa[9][4]="Hydrogen";


		//qca stores pairs of question and its correct answer
		qca=new String[10][2];

		qca[0][0]="Which planet is located closest to the Sun?";
		qca[0][1]="Mercury";

		qca[1][0]="Which planet is often referred to as the Red Planet?";
		qca[1][1]="Mars";

		qca[2][0]="Which gas giant has an average radius 9 times that of Earth?";
		qca[2][1]="Jupiter";

		qca[3][0]="Which one is a single-line comment?";
		qca[3][1]="Neptune";


		qca[4][0]="Which of the following planets is the densest in our solar system?";
		qca[4][1]="Earth";

		qca[5][0]="Uranus is ____ times the mass of Earth.";
		qca[5][1]="15";

		qca[6][0]="Venus orbits the sun once every _____ Earth days.";
		qca[6][1]="225";

		qca[7][0]="Saturn is just over ____ times more massive than the Earth";
		qca[7][1]="95";

		qca[8][0]="Earth was formed about __________ years ago";
		qca[8][1]="4.54 billion";

		qca[9][0]="What is Jupiter primarily composed of?";
		qca[9][1]="Hydrogen";


		//create a map object to store pairs of question and selected answer
		map=new HashMap<Integer, String>();

	}
	
	// returns the answer selected by the user from the answer choices list.
	public String getSelection(){
		String selectedChoice=null;
		Enumeration<AbstractButton> buttons=bg.getElements(); 
		while(buttons.hasMoreElements()) 
		{ 
			JRadioButton temp=(JRadioButton)buttons.nextElement(); 
			if(temp.isSelected()) 
			{ 
				selectedChoice=temp.getText();
			} 
		}  
		return(selectedChoice);
	}

// set the question to the lblmess, and answer choices text to the four radio buttons. The first radio button is selected by default
	public void readqa(int qid){
		lblmess.setText("  "+qpa[qid][0]);
		choice1.setText(qpa[qid][1]);
		choice2.setText(qpa[qid][2]);
		choice3.setText(qpa[qid][3]);
		choice4.setText(qpa[qid][4]);
		choice1.setSelected(true);
	}
	
	// reset the program back to its first load state.
	public void reset(){
		qaid=0;
		map.clear();
		readqa(qaid);
		btnext.setText("Continue");
	}
	
	//return the number of answers that are answered correctly.
	public int calCorrectAnswer(){
		int qnum=10;
		int count=0;
		for(int qid=0;qid<qnum;qid++)
			if(qca[qid][1].equals(map.get(qid))) count++;
		return count;
	}

	
	// report window is displayed when the user complete all questions and clicks the btnext button
	public class Report extends JFrame{
	
		private static final long serialVersionUID = 1L;


		Report(){
			setTitle("Answers");
			setSize(850,550);
			setBackground(Color.WHITE);
			addWindowListener(new WindowAdapter(){
				public void windowClosing(WindowEvent e){
					dispose();
					reset();
				}
			});
			Draw d=new Draw();                                  
			add(d);
			setVisible(true);
		}


		// used to display the output to the user.
		class Draw extends Canvas{

			private static final long serialVersionUID = 1L;

			public void paint(Graphics g){
				int qnum=10;
				int x=10;
				int y=20;
				for(int i=0;i<qnum;i++){
					//print the 1st column
					g.setFont(new Font("Arial",Font.BOLD,12));                                         
					g.drawString(i+1+". "+qca[i][0], x,y);
					y+=30;           
					g.setFont(new Font("Arial",Font.PLAIN,12));                             
					g.drawString("      Correct answer:"+qca[i][1], x,y);
					y+=30;
					g.drawString("      Your answer:"+map.get(i), x,y);
					y+=30;
					//print the 2nd column
					if(y>400)
					{y=20;
					x=450;
					}

				}
				//Show number of correct answers
				int numc=calCorrectAnswer();
				g.setColor(Color.BLUE);
				g.setFont(new Font("Arial",Font.BOLD,14));
				g.drawString("Number of correct answers:"+numc,300,500);


			}
		}

	}

}


public class Test{

	// main method to start the Quiz program.
	public static void main(String args[]){
		//new Quiz();

	}

}