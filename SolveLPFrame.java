import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.LinearEquation;
import org.apache.commons.math.optimization.LinearModel;
import org.apache.commons.math.optimization.LinearObjectiveFunction;
import org.apache.commons.math.optimization.NoFeasibleSolutionException;
import org.apache.commons.math.optimization.Relationship;
import org.apache.commons.math.optimization.SimplexSolver;
import org.apache.commons.math.optimization.UnboundedSolutionException;

public class SolveLPFrame extends JDialog implements ActionListener{

	public JTextArea textArea = new JTextArea(10,10);
	public JButton solve = new JButton("Solve");
	public JPanel textpanel = new JPanel();
	public JPanel buttonpanel = new JPanel();
	public Font font = new Font("Serif",Font.PLAIN,12);
	public LinearModel model;
	public LinearEquation solution;
	public SimplexSolver solver;
	public String type;
	
	public SolveLPFrame(String type){
		this.setTitle("Linear Programming");
		this.setLayout(new BorderLayout(10,10));
		this.setModal(true);
		this.type = type;		
		
		textpanel.setLayout(new FlowLayout(FlowLayout.CENTER,10,10));
		textpanel.add(textArea);
		textArea.setFont(font);
		this.textpanel.setBorder(BorderFactory.createTitledBorder("Linear Program"));
		((javax.swing.border.TitledBorder)this.textpanel.getBorder()).setTitleFont(this.font);
		textArea.setEditable(false);		
		
		buttonpanel.add(solve);
		solve.setFont(font);
		solve.addActionListener(this);		
		
		this.add(textpanel, BorderLayout.CENTER);		
		this.add(buttonpanel, BorderLayout.SOUTH);
		
		//set up LP
		double[] objective = new double[EnterMatrixFrame.m];
		double[][] identity = new double[EnterMatrixFrame.n][EnterMatrixFrame.m];
		for(int i = 0; i < EnterMatrixFrame.n; i++){
			for(int j = 0; j < EnterMatrixFrame.m; j++){
				if(i == j){
					identity[i][j] = -1;
				}else{
					identity[i][j] = 0;
				}				
			}
		}
			for(int i = 0; i< objective.length; i++){
				objective[i] = 1;
			}
			
			//display in text area
			StringBuilder textAreaString = new StringBuilder();
			textAreaString.append("Objective Function: Maximize\n\t");
			for(int i = 0; i< objective.length;i++){
				if(i == (objective.length-1)){
					textAreaString.append(objective[i] + "x_" + i + " ");
				}
				textAreaString.append(objective[i] + "x_" + i + "+ ");
			}
			textAreaString.append("\nSubject to:\n\t");
			for(int i =0 ; i < EnterMatrixFrame.n; i++){
				for(int j =0; j < EnterMatrixFrame.m;j++){
					if(j == (objective.length-1)){
						textAreaString.append(EnterMatrixFrame.matrixValue[i][j]+ "x_" + j + " ");
					}
					textAreaString.append(EnterMatrixFrame.matrixValue[i][j]+ "x_" + j + "+ ");
				}
				textAreaString.append("<= 1\n\t");
			}
			for(int i =0 ; i < EnterMatrixFrame.n; i++){
				for(int j =0; j < EnterMatrixFrame.m;j++){
					textAreaString.append(identity[i][j]+ "x_" + j + " ");
				}
				textAreaString.append("<= 0\n\t");
			}
			
			
			//set up linear model
			this.model = new LinearModel(new LinearObjectiveFunction(objective,0,GoalType.MAXIMIZE));
			for(int i = 0; i < EnterMatrixFrame.matrixValue.length; i++){
				model.addConstraint(new LinearEquation(EnterMatrixFrame.matrixValue[i],Relationship.LEQ,1));
			}
			for(int i = 0; i < EnterMatrixFrame.n; i++){
				model.addConstraint(new LinearEquation(identity[i],Relationship.GEQ,0));
			}
			this.solver = new SimplexSolver(model);	
			
			textArea.setText(textAreaString.toString());
		 }
		
		
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try {
			this.solution = solver.solve();			
			StringBuilder output = new StringBuilder();
			for(int i =0; i< EnterMatrixFrame.m; i++){
				output.append("x_" + i + " = " + solution.getCoefficients().getEntry(i) + "\n");
			}
			output.append("objective function value: " + solution.getRightHandSide());
			JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, output.toString());
		} catch (UnboundedSolutionException e1) {
			JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, "Solution is unbounded");
			return;
			
		} catch (NoFeasibleSolutionException e) {
			JOptionPane.showMessageDialog(EnterMatrixFrame.mainFrame, "No Feasible solution");
			return;
		}
	}
}
