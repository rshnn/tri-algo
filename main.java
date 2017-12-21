import javax.swing.JFrame;

import org.apache.commons.math.optimization.GoalType;
import org.apache.commons.math.optimization.LinearEquation;
import org.apache.commons.math.optimization.LinearModel;
import org.apache.commons.math.optimization.LinearObjectiveFunction;
import org.apache.commons.math.optimization.NoFeasibleSolutionException;
import org.apache.commons.math.optimization.Relationship;
import org.apache.commons.math.optimization.SimplexSolver;
import org.apache.commons.math.optimization.UnboundedSolutionException;

public class main {

	public static void main(String[] args) {		
		InitialMatrixSizeFrame frame = new InitialMatrixSizeFrame();			
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(true);
		frame.setVisible(true);
		
//		try {
//			testSimplexAPI();
//		} catch (UnboundedSolutionException | NoFeasibleSolutionException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//test
		
		    

	}

	private static void testSimplexAPI() throws UnboundedSolutionException, NoFeasibleSolutionException {
		LinearModel model = new LinearModel(new LinearObjectiveFunction(
		        new double[] { 0, 0,0, 0, 1 }, 0, GoalType.MINIMIZE));
		    model.addConstraint(new LinearEquation(new double[] { -2,  4, -4,3,-1}, Relationship.LEQ, 0));
		    model.addConstraint(new LinearEquation(new double[] {  1, -2, 3,5,-1 }, Relationship.LEQ, 0));
		    model.addConstraint(new LinearEquation(new double[] { 1,  1, 1,1,0}, Relationship.EQ, 1));


		    SimplexSolver solver = new SimplexSolver(model);
		    LinearEquation solution;
				solution = solver.solve();
				System.out.println("x_1:" + solution.getCoefficients().getEntry(0) + " Solution is 2");
			    System.out.println("x_2:" + solution.getCoefficients().getEntry(1) + " Solution is 2");
			    System.out.println("x_2:" + solution.getCoefficients().getEntry(2) + " Solution is 2");
			    System.out.println("x_2:" + solution.getCoefficients().getEntry(3) + " Solution is 2");
			    System.out.println("x_2:" + solution.getCoefficients().getEntry(4) + " Solution is 2");
			    System.out.println("objective function value: " + solution.getRightHandSide() + " Solution is 57");
			    
			    
			
	}

}
