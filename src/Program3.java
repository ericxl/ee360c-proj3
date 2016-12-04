/**
 * Name: Xiaoyong Liang
 * EID: XL5432
 */

public class Program3 implements IProgram3 {

    private int numClasses;
    private int maxGrade;
    private GradeFunction gf;

    public Program3() {
        this.numClasses = 0;
        this.maxGrade = 0;
        this.gf = null;
    }

    public void initialize(int numClasses, int maxGrade, GradeFunction gf){
        this.numClasses = numClasses;
        this.maxGrade = maxGrade;
        this.gf = gf;
    }

    /* Computes the optimal solution for given input: numClasses, maxGrade, gradeFunction, and totalHours
     * The output of hours is 0-indexed.
     */
    public int[] computeHours(int totalHours){
        //set up the table
        Node [][] gradeMatrix = new Node[numClasses + 1][totalHours + 1];

        //initialize the table
        for (int i = 0; i < numClasses + 1; i ++) {
            for(int j = 0; j < totalHours + 1; j++){
                gradeMatrix[i][j] = new Node();
            }
        }

        //Base case
        for (int i = 1; i <= numClasses; i++){
            int agg = 0;
            for(int j = 1; j <= i; j++){
                agg += gf.grade(j - 1, 0);
            }
            gradeMatrix[i][0].grade = agg;
        }

        //start with (1,1). find its optimal grade value in row major order
        for(int i = 1; i <= numClasses; i ++){
            for(int h = 1; h <= totalHours; h ++){
                int maxIndex = 0;
                int maxValue = 0;
                for(int k = 0; k <= h; k ++){
                    int grade = Math.min(gf.grade(i - 1, k), maxGrade) + gradeMatrix[i - 1][h - k].grade;
                    //int grade = Math.max(gradeMatrix[i - 1][h].grade,   cap(gf.grade(i - 1, k), maxGrade) + gradeMatrix[i - 1][h - k].grade);

                    if(grade > maxValue){
                        maxValue = grade;
                        maxIndex = k;
                    }
                }

                gradeMatrix[i][h].grade = maxValue;
                gradeMatrix[i][h].hour = maxIndex;
            }

        }

        //reconstruct solution by tracing back
        int [] hours = new int[numClasses];
        int hIndex = totalHours;
        int iIndex = numClasses;
        while (iIndex != 0){
            int k = gradeMatrix[iIndex][hIndex].hour;
            hours[iIndex - 1] = k;
            iIndex = iIndex - 1;
            hIndex = hIndex - k;
        }

        //Make sure total hour is always spent
        int totalHrNow = 0;
        for(int i = 0; i < hours.length; i ++){
            totalHrNow += hours[i];
        }
        if(totalHrNow < totalHours){
            int diff = totalHours - totalHrNow;
            //just put it on class 0 because it shouldn't matter
            hours[0] = hours[0] + diff;
        }

        return hours;
    }

    public int[] computeGrades(int totalHours){
        int[] hours = computeHours(totalHours);
        int[] grades = new int[numClasses];

        if(hours == null){
            //should not happen
            return hours;
        }

        //just to make sure
        int length = hours.length < numClasses ? hours.length : numClasses;

        for (int c = 0; c < length; c ++)
        {
            //cap the max grade in case grade function doesn't cap it
            grades[c] = Math.min(gf.grade(c, hours[c]), maxGrade);
        }

        return grades;
    }
}
