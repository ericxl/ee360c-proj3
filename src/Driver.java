public class Driver {
    public static void main(String[] args) {
        IProgram3 program = new Program3(); //Please name your primary solution file "Program3" so this initialization will work.
	if (args.length != 3){
		System.out.println("Incorrect argument list");
		System.exit(-1);
	}
        int n = Integer.parseInt(args[0]);
        int h = Integer.parseInt(args[1]);
        int maxgrade = Integer.parseInt(args[2]);
        GradeFunction gf = new ConcreteRandomGrade(n, maxgrade); //We will use our own grade function, so test your solution rigorously! Try your own GradeFunctions here.
	runSubmission(program, gf, n, h, maxgrade);
    }

    public static void runSubmission(IProgram3 program, GradeFunction gf, int n, int h, int maxGrade){
        program.initialize(n, maxGrade, gf);
        int[] hours_array = program.computeHours(h);
        int[] grades_array = program.computeGrades(h);
        for (int i=0; i<hours_array.length; i++) {
            System.out.println("Class " + (i+1) + " Hours " + hours_array[i] + " Grade " + grades_array[i]);
        }

    }
}
