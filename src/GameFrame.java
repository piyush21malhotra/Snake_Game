import javax.swing.JFrame;

public class GameFrame extends JFrame{
	
//	Creating the instance
	GamePanel panel;
	
	GameFrame(){ // Creating the class constructor
		
//		Creating a new GamePanel
		panel = new GamePanel();
		
//		This adds the panel to the frame
		this.add(panel);
		
//		Giving the title to the frame
		this.setTitle("Snake");
		
//		It will end the program when we click the window exit button
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		This restricts the user to change/set the size of the frame
		this.setResizable(false);
		
//		
		this.pack();
		
//		This helps to set the location relative to any component
//		Here it will locate the window in the middle
		this.setLocationRelativeTo(null);
		
//		This makes the window visible
		this.setVisible(true);
		
	}
}
