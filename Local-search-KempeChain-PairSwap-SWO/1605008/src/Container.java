import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class Container {
    public static double countPenalty(int[] sentSlots , LinkedList<Student> students)
    {
        //int[] slots = scheduler.getSlots();
        int[] slots = sentSlots ;

        int penalty = 0;

        for(Student student : students)
        {
            int tempPenalty = 0;
            int number = student.courses.length;

            for(int i=0 ; i<number-1 ; i++)
            {
                for(int j=i+1 ; j<number ; j++)
                {
                    if (slots[student.courses[i]] > slots[student.courses[j]])
                    {
                        int temp = student.courses[i];
                        student.courses[i] = student.courses[j];
                        student.courses[j] = temp;
                    }
                }
            }

            /*System.out.println("Student " + student.id);

            for(int i=0 ; i<number ; i++)
            {
                System.out.println((student.courses[i]+1) + " " + (slots[student.courses[i]] + 1) + " ");
            }

            System.out.println();*/

            for(int i=0 ; i<number-1 ; i++)
            {
                //System.out.println(slots[i+1] + " " + slots[i]);
                int gap = slots[student.courses[i+1]] - slots[student.courses[i]];

                int value;

                if (gap > 5)
                {
                    value = 0;
                }
                else
                {
                    value = (int) Math.pow(2 , 5-gap);
                }

                tempPenalty += value;
            }

            penalty += tempPenalty;

            //System.out.println(tempPenalty);
        }

        System.out.println(penalty);

        double penaltyFinal = (double) penalty;
        double studentSize = (double) students.size();

        double averagePenalty = penaltyFinal/studentSize;

        //System.out.println(scheduler.countSlots());
        System.out.println(averagePenalty);

        System.out.println();

        return averagePenalty;
    }

    public static void main(String[] args) throws IOException {
        //File courseFile = new File("yor-f-83.crs");
        //File studentFile = new File("yor-f-83.stu");

        //File courseFile = new File("car-f-92.crs");
        //File studentFile = new File("car-f-92.stu");

        //File courseFile = new File("car-s-91.crs");
        //File studentFile = new File("car-s-91.stu");

        //File courseFile = new File("tre-s-92.crs");
        //File studentFile = new File("tre-s-92.stu");

        File courseFile = new File("kfu-s-93.crs");
        File studentFile = new File("kfu-s-93.stu");

        //File courseFile = new File("course2.txt");
        //File studentFile = new File("student2.txt");

        String name = courseFile.getName();
        String onlyName ;
        onlyName = name.replace(".crs" , "");
        //onlyName[1] = "go";

        //System.out.println(onlyName);

        int courses = 0;

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(courseFile));

            while(bufferedReader.readLine() != null)
            {
                courses++;
            }

            bufferedReader.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        Scheduler scheduler = new Scheduler(courses);

        try
        {
            Scanner scanner = new Scanner(courseFile);

            for(int i=0 ; i<courses ; i++)
            {
                int course = scanner.nextInt() - 1;
                int students = scanner.nextInt();

                scheduler.studentCount(course , students);
            }

        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

        LinkedList<Student> students = new LinkedList<Student>();

        try
        {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(studentFile));

            int id = 1;
            String line = "";

            while((line = bufferedReader.readLine()) != null)
            {
                Scanner scanner = new Scanner(line);

                LinkedList<Integer> conflicts = new LinkedList<Integer>();

                while (scanner.hasNext())
                {
                    int next = scanner.nextInt() - 1;
                    conflicts.add(next);
                }

                int size = conflicts.size();

                Integer[] studentCourse = new Integer[size];

                Student student = new Student(id, conflicts.toArray(studentCourse));

                students.add(student);

                id++;

                for(int i=0 ; i<size-1 ; i++)
                {
                    for(int j=i+1 ; j<size ; j++)
                    {
                        scheduler.addConflict(conflicts.get(i) , conflicts.get(j));
                    }
                }
            }


        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //scheduler.normal();
        //scheduler.random();
        //scheduler.highestDegree();
        //scheduler.largestEnrollment();
        //scheduler.largestWeightedDegree();
        //scheduler.DSatur();
        //countPenalty(scheduler.getSlots() , students);
        //scheduler.KempeChain();
        //scheduler.PairSwap();
        //scheduler.MultipleKempeChain(8);
        //scheduler.SWO();
        //System.out.println(scheduler.countSlots());

        FileWriter fileWriter = new FileWriter("output.txt" , true);
        fileWriter.write(onlyName + "\t");

        for(int how=0 ; how<5 ; how++) {

            if(how == 0)
                scheduler.random();
            else if(how == 1)
                scheduler.highestDegree();
            else if(how == 2)
                scheduler.largestEnrollment();
            else if(how == 3)
                scheduler.largestWeightedDegree();
            else
                scheduler.DSatur();

            File file = new File(onlyName + "-Scheme"+ (how+1) + ".sol");
            FileWriter details = new FileWriter(file);


            int warning = 0;
            double lastOne = 0.0;
            int[] minimumPenaltySlots = new int[courses];
            //int[] minimumPenaltySlotsReal = new int[courses];
            double minimumAvgPenalty = Double.MAX_VALUE;
            for (int i = 0; i < 20; i++) {
                scheduler.KempeChain();
                double avgPenalty = countPenalty(scheduler.getSlots(), students);

                if (avgPenalty < minimumAvgPenalty) {
                    //System.out.println("once");
                    minimumAvgPenalty = avgPenalty;

                    for (int k = 0; k < courses; k++) {
                        minimumPenaltySlots[k] = scheduler.getSlots()[k];
                    }
                    //minimumPenaltySlots = scheduler.getSlots();
                    //minimumPenaltySlotsReal = minimumPenaltySlots;
                }

                if (i != 0 && avgPenalty > lastOne)
                    warning++;
                else {
                    //minimumPenaltySlots = scheduler.getSlots();
                    warning = 0;
                }

                lastOne = avgPenalty;

                if (warning == 3) {
                    System.out.println("I'm done with Kempe !!!");
                    break;
                }
            }
            //System.out.println(minimumAvgPenalty);
            System.out.println("Minimum Penalty And Average Penalty : ");
            countPenalty(minimumPenaltySlots, students);
            //System.out.println(scheduler.countSlots());
            fileWriter.write(scheduler.countSlots() + "   ");
            fileWriter.write(String.format("%.2f" , countPenalty(minimumPenaltySlots, students)) + "\t||\t");
            //System.out.println("The Best Coloring With Minimum Penalty : ");
            for (int i = 0; i < courses; i++) {
                details.write("Course " + (i + 1) + " -> Slot " + (minimumPenaltySlots[i] + 1) + "\n");
                //System.out.println("Course " + (i + 1) + " -> Slot " + (minimumPenaltySlots[i] + 1));
            }

            details.close();
            //System.out.println();
        }

        fileWriter.write("\n");
        fileWriter.close();
        //countPenalty(minimumPenaltySlots , students);
        //double avgPenalty = countPenalty(scheduler , students);

        /*int[] slots = scheduler.getSlots();

        int penalty = 0;

        for(Student student : students)
        {
            int tempPenalty = 0;
            int number = student.courses.length;

            for(int i=0 ; i<number-1 ; i++)
            {
                for(int j=i+1 ; j<number ; j++)
                {
                    if (slots[student.courses[i]] > slots[student.courses[j]])
                    {
                        int temp = student.courses[i];
                        student.courses[i] = student.courses[j];
                        student.courses[j] = temp;
                    }
                }
            }

            System.out.println("Student " + student.id);

            for(int i=0 ; i<number ; i++)
            {
                System.out.println((student.courses[i]+1) + " " + (slots[student.courses[i]] + 1) + " ");
            }

            System.out.println();

            for(int i=0 ; i<number-1 ; i++)
            {
                //System.out.println(slots[i+1] + " " + slots[i]);
                int gap = slots[student.courses[i+1]] - slots[student.courses[i]];

                int value;

                if (gap > 5)
                {
                    value = 0;
                }
                else
                {
                    value = (int) Math.pow(2 , 5-gap);
                }

                tempPenalty += value;
            }

            penalty += tempPenalty;

            //System.out.println(tempPenalty);
        }

        System.out.println(penalty);

        double penaltyFinal = (double) penalty;
        double studentSize = (double) students.size();

        double averagePenalty = penaltyFinal/studentSize;

        System.out.println();
        System.out.println(scheduler.countSlots());
        System.out.println(averagePenalty);*/

        System.out.println(scheduler.countSlots());
        System.out.println(countPenalty(scheduler.getSlots() , students));

        System.out.println(scheduler.isValid());

        System.out.println();

        //scheduler.show(onlyName);

    }
}
