/**
 * Created by Eric on 12/2/16.
 */
public class MyGradeFunction2 implements GradeFunction{

    private int numClasses;
    private int maxGrade;

    public MyGradeFunction2(int n, int g){
        this.numClasses = n;
        this.maxGrade = g;
    }

    /*
     * This grade function gives square hours
     */
    public int grade(int classID, int hours){
        return Math.min((hours*hours) / 4 ,maxGrade);
    }

}