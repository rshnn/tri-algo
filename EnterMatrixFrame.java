import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Jama.*;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class EnterMatrixFrame extends JDialog implements ActionListener {
	
	public static JFrame mainFrame;
	public Font font = new Font("Serif",Font.PLAIN,12);
	public static int n = 0;
	public static int m = 0;
	
	//panel 1 label
	public JPanel panel1 = new JPanel();
	public JLabel inputLabel = new JLabel("Input Matrix Dimensions");
	
	
	//panel 2 values
	public JPanel panel2 = new JPanel();
	public static double[][] matrixValue;
	public static Matrix matrix;
	public static JTextField[][] matrixTextValue;
	public JPanel[] rowPanel;
	
	
	//panel 3 buttons
	public JPanel panel3 = new JPanel();
	public JButton randomMatrix = new JButton("Random Matrix");
	public JButton randomZeroOne = new JButton("Random Bool Matrix");
	public JButton randomTum = new JButton("Random TUM");
	public JButton verifyTum = new JButton("Verify TUM");
	
	
	public EnterMatrixFrame(int n, int m){
		this.setTitle("Input Matrix Entries");
		this.setLayout(new BorderLayout(10,10));
		this.n = n;
		this.m = m;
		
		
		//panel 1 label
		panel1.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel1.add(inputLabel);
		inputLabel.setFont(font);
		
		
		//panel 2 input matrix entries
		panel2.setLayout(new GridLayout(n,1));
		matrixTextValue = new JTextField[n][m];
		matrixValue = new double[n][m];
		rowPanel = new JPanel[n];
		for(int i = 0; i < n; i++){
			rowPanel[i] = new JPanel();
			rowPanel[i].setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
			for(int j = 0; j < m; j++){
				matrixTextValue[i][j] = new JTextField(2);
				matrixTextValue[i][j].setFont(font);
				rowPanel[i].add(matrixTextValue[i][j]);
			}
		}
		for(int i = 0; i < n; i++){
			panel2.add(rowPanel[i]);
		}
		
		//panel 3 buttons
		panel3.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		panel3.add(randomMatrix);
		randomMatrix.setFont(font);
		randomMatrix.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//FILL (i,j) entries with random int;
				EnterMatrixFrame.matrix = Matrix.random(EnterMatrixFrame.n, EnterMatrixFrame.m);
				double i_j = 0;
				for(int i = 0; i < EnterMatrixFrame.matrix.getRowDimension(); i++){					
					for(int j = 0; j < EnterMatrixFrame.matrix.getColumnDimension(); j++){
						i_j = EnterMatrixFrame.matrix.get(i, j)*100;
						System.out.println(i_j + "\n");
						EnterMatrixFrame.matrix.set(i, j, ((int)(i_j))%2);
						EnterMatrixFrame.matrixTextValue[i][j].setText(EnterMatrixFrame.matrix.get(i, j)+"");
						EnterMatrixFrame.matrixValue[i][j] = EnterMatrixFrame.matrix.get(i, j);						
					}
				}
			}
		});
		panel3.add(randomTum);
		randomTum.setFont(font);
		randomTum.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				//
				int randomNum =0,x=-1,y=-1;
				for(int j =0 ; j < EnterMatrixFrame.m; j++){
					//make each column have exactly 2 non zeros or all zeros					
					randomNum = ThreadLocalRandom.current().nextInt(0, 2 + 1);
					if(randomNum == 0){
						//fill with zeros
						for(int i = 0; i < EnterMatrixFrame.n; i++){
							EnterMatrixFrame.matrixTextValue[i][j].setText(0+"");
							EnterMatrixFrame.matrixValue[i][j] = 0;	
						}
						continue;//go to next column
					}else if(randomNum == 1){
						//fill with same sign
						x = ThreadLocalRandom.current().nextInt(0, EnterMatrixFrame.m);
						while((y = ThreadLocalRandom.current().nextInt(0, EnterMatrixFrame.m)) != x){
							continue;
						}
						for(int i = 0; i < EnterMatrixFrame.n; i++){
							EnterMatrixFrame.matrixTextValue[i][j].setText(0+"");
							EnterMatrixFrame.matrixValue[i][j] = 0;	
							if(i == x || i == y){
								randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1); 
								if(randomNum == 0){
									EnterMatrixFrame.matrixTextValue[x][j].setText(1+"");
									EnterMatrixFrame.matrixValue[x][j] = 1;	
									EnterMatrixFrame.matrixTextValue[y][j].setText(1+"");
									EnterMatrixFrame.matrixValue[y][j] = 1;
								}else{
									EnterMatrixFrame.matrixTextValue[x][j].setText(-1+"");
									EnterMatrixFrame.matrixValue[x][j] = -1;
									EnterMatrixFrame.matrixTextValue[y][j].setText(-1+"");
									EnterMatrixFrame.matrixValue[y][j] = -1;
								}
							}
						}
						continue; //go to next column
					}else{ //randomNum == 2
						x = ThreadLocalRandom.current().nextInt(0, EnterMatrixFrame.m);
						while((y = ThreadLocalRandom.current().nextInt(0, EnterMatrixFrame.m)) != x){
							continue;
						}
						for(int i = 0; i < EnterMatrixFrame.n; i++){
							EnterMatrixFrame.matrixTextValue[i][j].setText(0+"");
							EnterMatrixFrame.matrixValue[i][j] = 0;	
							if(i == x){
								EnterMatrixFrame.matrixTextValue[i][j].setText(1+"");
								EnterMatrixFrame.matrixValue[i][j] = 1;		
							}
							if( i == y){
								EnterMatrixFrame.matrixTextValue[i][j].setText(-1+"");
								EnterMatrixFrame.matrixValue[i][j] = -1;
							}
						}
					}//end else					
				
				}
				EnterMatrixFrame.matrix = new Matrix(EnterMatrixFrame.matrixValue);
			}
		});
		panel3.add(verifyTum);
		verifyTum.setFont(font);
		verifyTum.addActionListener(new ActionListener(){
			@Override 
			public void actionPerformed(ActionEvent e){
				//call verify many det
				//if true open solve LP frame
				//else return not a TUM
				for(int i = 0; i< EnterMatrixFrame.n; i++){
					for(int j = 0; j < EnterMatrixFrame.m; j++){
						try{
							EnterMatrixFrame.matrixValue[i][j] = Double.parseDouble(EnterMatrixFrame.matrixTextValue[i][j].getText());
						}catch(NumberFormatException ex){
							JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, "Is not a valid number " + EnterMatrixFrame.matrixTextValue[i][j].getText());
							return;
						}
					}
				}
				EnterMatrixFrame.matrix = new Matrix(EnterMatrixFrame.matrixValue);
				EnterMatrixFrame.matrix.print(EnterMatrixFrame.matrix.getRowDimension(), EnterMatrixFrame.matrix.getRowDimension());
				System.out.println("n: " + EnterMatrixFrame.matrix.getRowDimension() + " m: " + EnterMatrixFrame.matrix.getRowDimension());
				for(int i = 0; i < EnterMatrixFrame.matrix.getRowDimension(); i++){					
					for(int j = 0; j < EnterMatrixFrame.matrix.getColumnDimension();j++){
						int k = i+j;
//						System.out.println("k = " + k + " i =" + i + " j =" + j);
//						System.out.println("Submatrix ("+ i + ", " + j + ") to " + "(" + (i+j) + ", " + (j+j) + ")");
						if((k < EnterMatrixFrame.matrix.getColumnDimension()) && (k < EnterMatrixFrame.matrix.getRowDimension()) && ((j+j)< EnterMatrixFrame.matrix.getRowDimension())){
//							System.out.println("Submatrix ("+ i + ", " + j + ") to " + "(" + (i+j) + ", " + (j+j) + ")");
							if(j == 0){
								if((int)EnterMatrixFrame.matrix.get(i, i) == 1 || (int)EnterMatrixFrame.matrix.get(i, i) == 0 || (int)EnterMatrixFrame.matrix.get(i, i) == -1 ){
									continue;
								}else{
									JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, "Submatrix ("+ i + ", " + j + ") to " + "(" + (i+j) + ", " + (i+j) + ") + " + " has determinant " + (int)EnterMatrixFrame.matrix.get(i, i) );
									return;
								}
							}
							Matrix submatrix = EnterMatrixFrame.matrix.getMatrix(i,j,i+j,j+j);
							Double result = submatrix.det();
							System.out.println(result);
							if(result == 0 || result == -1 || result == 1){
								//its TUM
								continue;
							}else{
								JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, "Submatrix ("+ i + ", " + j + ") to " + "(" + (i+j) + ", " + (i+j) + ") + " + (i+j) + " has determinant " + result );
								return;
							}
							//its TUM							
						}						
					}
					
				}
				JList list = new JList(new String[] {"Vertex Cover", "Matching", "None"});
				JOptionPane.showMessageDialog(
				  null, list, "Select a Problem to Solve", JOptionPane.PLAIN_MESSAGE);
					//System.out.println(Arrays.toString(list.getSelectedIndices()));
				String selection = (String)list.getSelectedValue();
				System.out.println("Selected " + selection);
				if(selection.trim().equals("Vertex Cover")){
					//solve vertex cover problem which is the dual of the matching
				}else if(selection.trim().equals("Matching")){
					//solve matching problem
					
				}
					
			}
			
		});
		
		this.add(panel1,BorderLayout.NORTH);
		this.add(panel2, BorderLayout.CENTER);
		this.add(panel3, BorderLayout.SOUTH);
	}

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
}
