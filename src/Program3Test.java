/**
 * Created by Eric on 12/2/16.
 */
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;

/**
 * Test Program 3: Dynamic Programming
 * EID: js68634
 * Created by John Starich on 8/3/16.
 */
public class Program3Test extends TestCase {

    @Test
    public void test2()
    {
        int[][] multi = new int[][]{
                {0, 4, 7, 8, 12, 18, 19, 20, 23, 25, 27},
                {0, 1, 5, 6, 7, 13, 14, 19, 19, 21, 24},
                {0, 3, 3, 4, 5, 7, 8, 12, 16, 17, 24},
                {0, 0, 1, 3, 6, 10, 17, 17, 22, 28, 29},
                {0, 2, 4, 6, 7, 10, 16, 19, 19, 27, 28}
        };
        UnitTest.runSubmission((classID, hours) -> {
            return multi[classID][hours];
        },5, 10, 77);

        UnitTest.runSubmission((classID, hours) -> {
            return multi[classID][hours];
        },5, 8, 77);
    }

    public void testComputeHoursSquareRoot() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        int maxGrade = 3;
        program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));

        assertEquals(H, sum(program.computeHours(H)));
    }

    public void testComputeGradesSquareRoot() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        int maxGrade = 3;
        program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
        assertEquals(22, sum(program.computeGrades(H)));
    }

    public void testComputeGradesCustom() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 20;
        int maxGrade = 10;
        //program.initialize(n, maxGrade, new CustomGradeFunction(n, maxGrade));

        assertEquals(35, sum(program.computeGrades(H)));
    }

    public void testMyGradeFunction() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        int maxGrade = 10;
        program.initialize(n, maxGrade, (int classID, int hours) ->{
            if(classID % 2 == 0) {
                return hours * 3 / 2;
            }
            return hours;
        });
        int[] hrs = program.computeHours(H);
        int[] gr = program.computeGrades(H);
        assertEquals(H, sum(program.computeHours(H)));
        assertEquals(55, sum(program.computeGrades(H)));
    }

    /** Test successive calls to Program3 don't corrupt the state of the instance */
    public void testSuccessiveRunsOnProgram() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        int maxGrade = 10;
        program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
        assertEquals(H, sum(program.computeHours(H)));
        assertEquals(22, sum(program.computeGrades(H)));

        H = 20;
        assertEquals(15, sum(program.computeGrades(H)));

        H = 80;
        assertEquals(31, sum(program.computeGrades(H)));

        n = 20;
        H = 40;
        maxGrade = 10;
        program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
        assertEquals(H, sum(program.computeHours(H)));
        assertEquals(30, sum(program.computeGrades(H)));

        H = 20;
        assertEquals(20, sum(program.computeGrades(H)));

        H = 80;
        assertEquals(45, sum(program.computeGrades(H)));
    }

    /** Test case where no work can be done to improve the overall grade */
    public void testAllZeros() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        int maxGrade = 10;
        program.initialize(n, maxGrade, (classID, hours) -> 0);
        //assertEquals(0, sum(program.computeHours(H)));
        assertEquals(0, sum(program.computeGrades(H)));
    }

    /** Test another custom grade function for completeness sake. */
    public void testIncreaseOnClassId() throws Exception {
        Program3 program = new Program3();
        final int n = 10;
        int H = 40;
        final int maxGrade = 10;
        program.initialize(n, maxGrade, (classID, hours) -> {
            if(hours == n - classID) return Math.min(maxGrade, classID);
            return 0;
        });
        int[] hrs = program.computeHours(H);
        int[] gr = program.computeGrades(H);
        //assertEquals(40, sum(program.computeHours(H)));
        assertEquals(44, sum(program.computeGrades(H)));
    }

    /** Ensure that if every class is an easy-A then no work is done at all. */
    public void testAllMax() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 40;
        final int maxGrade = 10;
        program.initialize(n, maxGrade, new GradeFunction() {
            @Override
            public int grade(int classID, int hours) {
                return maxGrade;
            }
        });
        //assertEquals(0, sum(program.computeHours(H)));
        assertEquals(100, sum(program.computeGrades(H)));
    }

    public void testSquareRootMore() throws Exception {
        Program3 program = new Program3();
        int[][] solutions = {
                //n, H, maxGrade, sumHours, sumGrades
                {2, 400, 10, 182, 20},
                {2, 2, 10, 2, 2},
                {4, 100, 8, 100, 21},
        };
        for(int[] solution : solutions) {
            assertEquals("Error in solution for testSquareRootMore: length of solution is not 5", 5, solution.length);
            int n = solution[0];
            int H = solution[1];
            int maxGrade = solution[2];
            System.out.printf("Initializing program with n = %d, H = %d, maxGrade = %d\n", n, H, maxGrade);
            program.initialize(n, maxGrade, new SquareRootGradeFunction(n, maxGrade));
            int hours[] = program.computeHours(H);
            int grades[] = program.computeGrades(H);
            System.out.printf("hours = %s\ngrades = %s\n", Arrays.toString(hours), Arrays.toString(grades));
            assertEquals("Sum of hours is incorrect", solution[1], sum(hours));
            assertEquals("Sum of grades is incorrect", solution[4], sum(grades));
        }
    }

    private int sum(int[] integers) {
        int total = 0;
        for(int i : integers) total += i;
        return total;
    }
}
