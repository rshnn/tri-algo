import javax.swing.JFrame;

public class main {

	public static void main(String[] args) {
		InitialMatrixSizeFrame frame = new InitialMatrixSizeFrame();			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
	}

}
