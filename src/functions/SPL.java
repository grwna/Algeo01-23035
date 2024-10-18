package functions;

import java.util.Arrays;
import main.IO;
import matrix.*;

/* Setiap method membuat instansi SPL sebagai nilai yang akan di return. 
   Oleh karena itu, penggunaan di luar Class ini tidak perlu membuat instance baru
   
   Setiap instance memiliki 3 state, oneSolution, infSolution dan noSolution.
   Solusi unik dinyatakan dalam sebuah array solusi.
   Tidak ada solusi cukup dengan state no solution, display "tidak ada solusi" ditulis
   pada Main.
   Solusi dependent (Infinite Solutions) dinyatakan dalam prametrik 
 */

public class SPL {
    boolean oneSolution;
    boolean noSolution;
    boolean infSolution;
    double[] solutions;
    String[] paramSolutions;
    int variables;
    
    // Contructor
    public SPL(int varCount){
        this.oneSolution = false;
        this.noSolution = false;
        this.infSolution = false;
        this.solutions = new double[varCount];
        this.paramSolutions = new String[varCount];
        this.variables = this.solutions.length; 
    }

    public boolean oneSolution(){return oneSolution;}
    public boolean noSolution(){return noSolution;}
    public boolean infSolution(){return infSolution;}
    public void setOneSolution() {oneSolution = true;}
    public void setNoSolution() {noSolution = true;}
    public void setInfSolution() {infSolution = true;}
    public void setSolutions(int Idx, double val){solutions[Idx] = val;}
    public void setParamSolutions(int Idx, String val){paramSolutions[Idx] = val;}
    public double getSolutions(int Idx){return solutions[Idx];}
    public int varCount(){return variables;}
    public void displaySolutions(){
        for(int i = 0; i < variables; i++){
<<<<<<< Updated upstream
            System.out.print("X" + i + "= " + solutions[i] + " ");
=======
            if(oneSolution){System.out.print("X" + (i+1) + " = " + solutions[i] + " ");}
            else if(infSolution){System.out.print("X" + (i+1) + " = " + paramSolutions[i] + " ");}
            System.out.println("");
>>>>>>> Stashed changes
        }
    }

    public static SPL gaussJordanElim (Matrix M) {
        Matrix T = MatrixAdv.getRREMatrix(M);
        int row = M.rowCount(), col = M.colCount();
        SPL result = new SPL(col -1);

        if(T.colCutter(col-1).hasZeroRow()){
            if(T.hasZeroRow()) result.setInfSolution();
            else result.setNoSolution();
        }
        // Kalo solusi unik
        if (!result.noSolution && !result.infSolution()){
            result.setOneSolution();
            for(int i = 0; i < row; i++){
                result.solutions[i] = T.getElmt(i, col-1);
            }
        }
        return result;
    }

    public static SPL gaussElim(Matrix M) {
     Matrix T = MatrixAdv.getUpperTriangular(M);
     int row = M.rowCount(), col = M.colCount();
     SPL result = new SPL(col -1);

     if(T.colCutter(col-1).hasZeroRow()){
         if(T.hasZeroRow()) result.setInfSolution();
         else result.setNoSolution();
     }
     // Jika tidak ada row zero

     if (!result.noSolution && !result.infSolution()) {
         result.setOneSolution();

         // Back Substitution
        for (int i = row - 1; i >= 0; i--) {
            double sum = T.getElmt(i,col-1); 

            for (int j = i + 1; j < col-1; j++) {
                sum -= T.getElmt(i, j) * result.solutions[j];
            }
            
            result.solutions[i] = sum / T.getElmt(i, i);
                }
        }
     return result;
    }
    
    public static SPL cramerMethodSPL(Matrix M){
        int row = M.rowCount();
        int col = M.colCount();
        
        Matrix A = M.colCutter(col - 1);
        double[] B = M.getCol(col - 1);
        double detA, detB;
        detA = MatrixAdv.detByGauss(A); // Determinant of A
        
        SPL result = new SPL(col - 1);
        if (row != col -1 || detA == 0) {
            System.out.println("Tidak dapat diselesaikan dengan metode Cramer");
            return result;
        }
        result.setOneSolution();
        int i,j;
        for (j = 0; j < col-1; j++){
            Matrix TempMatrix = A.copyMatrix();
            for (i = 0; i < row; i++){
                TempMatrix.setElmt(i, j, B[i]);
            }
            detB = MatrixAdv.detByGauss(TempMatrix);
            result.setSolutions(j, detB/detA);
        }
        
        return result;
    }
<<<<<<< Updated upstream
    public static SPL inverseElim (Matrix mProblem){
=======
    public static SPL inverseSPL (Matrix mProblem){
        // Asumsikan matriks invertible (dicek di main)
>>>>>>> Stashed changes
        Matrix A = mProblem.copyMatrix();
        int row = mProblem.rowCount();
        int col = mProblem.colCount();
        double[] tempB = A.getCol(col-1);
        Matrix B = new Matrix(row,1);
        // Copy double[] ke Matrix
        for(int i = 0; i < row; i++){
            B.setElmt(i, 0, tempB[i]);
        }
        // IO.terminalOutputMatrix(B);
        A = A.colCutter(col-1);
        Matrix inverse = MatrixAdv.inverseByAdjoin(A);
        // IO.terminalOutputMatrix(inverse);
        Matrix multiplied = MatrixAdv.multiplyMatrix(inverse, B);
<<<<<<< Updated upstream
        double[] array = multiplied.getCol(0);
        SPL hasilSPL = new SPL(multiplied.rowCount());
        for (int i = 0; i < multiplied.rowCount(); i++) {
            hasilSPL.setSolutions(i, array[i]); 
        }
        return hasilSPL;
=======
        
        // Assign value hasil ke result SPL
        SPL result = new SPL(col-1);
        result.setOneSolution();
        for(int i = 0; i < row; i++){
            result.setSolutions(i, multiplied.getElmt(i, 0));
        }
        return result;
>>>>>>> Stashed changes
    }

    // Dipakai pada RREF Form
    public static SPL parametricWriter(Matrix M) {
        int row = M.rowCount(), col = M.colCount() - 1;
        int i, j, k;
        boolean[] isFreeVariable = new boolean[col];
        int paramCount = 1;
        SPL result = new SPL(col);

        // Initialize paramSolutions
        for (i = 0; i < col; i++) {
            result.setParamSolutions(i, "");
        }

         // Find all the free variables
         for (j = 0; j < col; j++){
             boolean isPivot = false;
             for( i = 0; i< row; i++){
                if(M.getElmt(i, j) == 1 ){
                    isPivot = true;
                    break;}
                
            }
            isFreeVariable[j] = !isPivot;
            if (isFreeVariable[j]) {
                // Initialize the free variable with a parameter like t1, t2, etc.
                result.setParamSolutions(j, "t" + paramCount);
                paramCount++; // Increment paramCount here for free variables
            }
         }

         // Construct Parametric Form
         for (i = 0; i < row; i++) {
            boolean isRowEmpty = true;
            for(j = 0; j < col; j++){
                if(M.getElmt(i, j) != 0){
                    isRowEmpty = false;
                    break;
                }
            }
            if(!isRowEmpty){
                // Dependent variable
                double constant = M.getElmt(i, col); // The constant term

                String currSolution = "" + constant;
                
                // For each free variable, print its coefficient with parameter
                for (k = 0; k < col; k++) {
                    if (isFreeVariable[k]) {
                        double coeff = -M.getElmt(i, k);
                        String sign = "+";
                        if (coeff < 0) {
                            coeff = -coeff;
                            sign = "-";
                        }
                        if (coeff == 1){currSolution += " " + sign + " " + result.paramSolutions[k];}
                        else currSolution += " " + sign + " " +  coeff + result.paramSolutions[k];
                    }
                }
                result.setParamSolutions(i, currSolution);
            }
            
        }
        System.out.println(Arrays.toString(isFreeVariable));
        return result;
    }
    public static void main(String[] args) {
        Matrix M = IO.keyboardInputMatrix(4, 6);
        M = MatrixAdv.getRREMatrix(M);
        IO.terminalOutputMatrix(M);
        SPL R = parametricWriter(M);
        R.setInfSolution();
        R.displaySolutions();
    }
} 
