import org.junit.Test;

import static junit.framework.TestCase.*;

/**
 * Created by Eric on 12/1/16.
 */
public class UnitTest {

    @Test
    public void test1()
    {
        Program3 program = new Program3();
        int n = 5;
        int H = 10;
        int maxGrade = 20;
        program.initialize(n, maxGrade, (classID, hours) -> {
            return 2+hours;
        });
        int[] s = program.computeHours(H);
        assertEquals(20, sum(s));
        assertEquals(20, sum(program.computeGrades(H)));
    }

    @Test
    public void test3()
    {
        int[][] mtx = new int[][]{
                { 4, 4, 4, 4, 4, 4},
                { 2, 4, 6, 8, 10, 12},
                { 4, 6, 8, 10,12, 14},
                { 2, 4, 4, 4, 4, 4},
                { 2, 2, 2, 4, 4, 16}
        };
        runSubmission((classID, hours) -> {
            return mtx[classID][hours];
        },5, 5, 16);
    }

    @Test
    public void test4()
    {
        int[][] mtx = new int[][]{
                { 2, 3, 3, 3, 4},
                { 2, 3, 4, 5, 6},
                { 2, 3, 5, 6, 7},
                { 3, 5, 5, 5, 5},
                { 1, 2, 2, 2, 2}
        };
        runSubmission((classID, hours) -> {
            return mtx[classID][hours];
        },5, 4, 7);
    }

    @Test
    public void testZero() {
        Program3 program = new Program3();
        int n = 10;
        int H = 30;
        int maxGrade = 100;
        program.initialize(n, maxGrade, (classID, hours) -> 0);
        assertEquals(0, sum(program.computeGrades(H)));
    }

    @Test
    public void testCapMaxGrade() throws Exception {
        Program3 program = new Program3();
        int n = 10;
        int H = 20;
        int maxGrade = 5;
        program.initialize(n, maxGrade, (classID, hours) -> hours);
        assertEquals(20, sum(program.computeGrades(H)));
    }

    @Test
    public void testAllHrSpent() throws Exception {
        Program3 program = new Program3();
        int n = 5;
        int H = 100;
        int maxGrade = 5;
        program.initialize(n, maxGrade, (classID, hours) -> hours);
        assertEquals(100, sum(program.computeHours(H)));
        assertEquals(25, sum(program.computeGrades(H)));
    }

    static void runSubmission(GradeFunction gf, int n, int h, int maxGrade){
        Program3 program = new Program3();
        program.initialize(n, maxGrade, gf);
        int[] hours_array = program.computeHours(h);
        int[] grades_array = program.computeGrades(h);
        for (int i=0; i<hours_array.length; i++) {
            System.out.println("Class " + (i+1) + " Hours " + hours_array[i] + " Grade " + grades_array[i]);
        }

    }

    static int sum(int[] arr) {
        int total = 0;
        for(int i : arr) total += i;
        return total;
    }
}
