import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class InitialMatrixSizeFrame extends JFrame implements ActionListener {
	public Font font = new Font("Serif",Font.PLAIN,12);
	public static JFrame mainFrame;
	//panel 1
	JPanel panel1 = new JPanel();
	JLabel sizeLabel = new JLabel("Input the matrix dimensions");	
	//panel 2
	JPanel panel2 = new JPanel();
	JLabel nLabel = new JLabel("n: ");
	JTextField nText = new JTextField(2);	
	JLabel mLabel = new JLabel("m: ");
	JTextField mText = new JTextField(2);	
	//panel 3
	JPanel panel3 = new JPanel();
	JButton cButton = new JButton("Continue");	
	
	public InitialMatrixSizeFrame(){
		this.setTitle("Set up matrix size");
		this.setLayout(new GridLayout(3,1));
		mainFrame = this;
		//panel 1
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel1.add(sizeLabel);
		sizeLabel.setFont(font);
		//panel 2
		panel2.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel2.add(nLabel);
		nLabel.setFont(font);
		panel2.add(nText);
		nText.setFont(font);
		panel2.add(mLabel);
		mLabel.setFont(font);
		panel2.add(mText);
		mText.setFont(font);
		//panel 3
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel3.add(cButton);
		cButton.setFont(font);
		cButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int n= 0, m = 0;
				try{
					n = Integer.parseInt(nText.getText().trim());
					m = Integer.parseInt(mText.getText().trim());
				}catch (NumberFormatException e){
					JOptionPane.showMessageDialog(InitialMatrixSizeFrame.mainFrame, "Invalid integer matrix dimentions: " + nText.getText() + " x " + mText.getText());
					nText.setText("");
					mText.setText("");
					return;
				}
				if( n > 10 || m > 10 ){
					JOptionPane.showMessageDialog(InitialMatrixSizeFrame.mainFrame, "Lets keep it to smaller matrices");
					return;
				}else if(n <=0 || m <=0){
					JOptionPane.showMessageDialog(InitialMatrixSizeFrame.mainFrame, "Each dimension must be greater than 0");
					return;
				}else{
					EnterMatrixFrame frame = new EnterMatrixFrame(n,m);			
					frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					frame.pack();
					frame.setLocationRelativeTo(InitialMatrixSizeFrame.mainFrame);
//					welderFrame.setSize(1291,663);
					frame.setResizable(true);
					frame.setVisible(true);
				}
				
				
			}
			
		});
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);		
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
