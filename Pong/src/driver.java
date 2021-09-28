import javax.swing.JFrame;
import java.awt.EventQueue;

public class driver extends JFrame {
	
	public driver() {
		add(new pong());
		setResizable(false);
		pack();
		
		setTitle("Pong Game");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				JFrame x = new driver();
				x.setVisible(true);
			}
		});

	}

}