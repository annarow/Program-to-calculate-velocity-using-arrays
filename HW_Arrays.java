
/**
 * This program calculates the velocity of a skydiver as a function of time.
 *
 * Anna Waldron
 * 5/11/2020
 */

import java.util.Scanner;
import java.lang.Math;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
public class HW_Arrays
{   //Void main function that takes user input for variables for a skydiver
    //velocity. Uses a while loop to execute program multiple times. Calls
    //the calculate method once all the variables have been inputted.
    public static void main(String[] args)
    {
        Scanner keyboard = new Scanner(System.in);
        boolean run = true;
        while(run){
            System.out.print("Enter the mass of the skydiver (kg): ");
            double mass = keyboard.nextDouble();
            System.out.print("Enter the cross-sectional area of " +
                "the skydiver (m^2): ");
            double cs_area = keyboard.nextDouble();
            System.out.print("Enter the drag coefficient of the " + 
                "skydiver: ");
            double drag = keyboard.nextDouble();
            System.out.print("Enter the ending time (sec): ");
            Double end_time = keyboard.nextDouble();
            System.out.print("Enter the time step (sec): ");
            double time_step = keyboard.nextDouble();
            System.out.print("Enter the output filename: ");
            String filename = keyboard.next();
            calculate(mass, cs_area, drag, end_time, time_step, filename);
            //System.out.println();
            System.out.print("Enter another dive? (y/[n]): ");
            String answer = keyboard.next();
            if(answer.equals("n")){
                run = false;
            }
            else if(answer.equals("y")){
                run = true;
            }

        }
        System.exit(0);
    }
    //Void function that calculates the velocity of a skydiver based on 
    //the arguements. Takes 6 arguements, 5 being doubles and one string for 
    //the file name that the data will be written onto. Uses a for loop to
    //do the calculations and sets two arrays, one for time and one for
    //velocity, equal to the corresponding data. Calls a helper method
    //to calculate the current velocity at that time. After for loop calls
    //a writer method to write the information to a file.
    public static void calculate(double mass, double cs_area, double drag,
    double end_time, double time_step, String filename)
    {
        double current_time = 0.0;
        double prev_velocity = 0.0;
        double current_velocity = 0.0;
        double[] velocity = new double[(int) (end_time / time_step)];
        double[] time = new double[(int) (end_time / time_step)];
        int index = 0;
        for(current_time = time_step; current_time <= end_time; 
        current_time += time_step){   
            time[index] = Math.round(current_time*1000.0)/1000.0;
            current_velocity = helper(mass, cs_area, drag, prev_velocity,
                time_step);
            velocity[index] = Math.round(current_velocity*10000.0)/10000.0;
            prev_velocity = current_velocity;
            //testVariableValues(time, velocity, prev_velocity, current_velocity, index);
            index++;
        }        
        writer(time, velocity, filename);
    }
    //Double function that is a helper to the calculate method. Takes 5 
    //double arguements that does the hard calculations that then returns
    //that velocity at that time. Uses consatnts for air density and gravity.
    public static double helper(double mass, double cs_area, double drag, 
    double prev_velocity, double time_step)
    {
        final double AIR_DENSITY = 1.14; //Pa
        final double GRAVITY = 9.81; //g is Gravitational acceleration
        //C is drag
        //A is cs_area
        double return_double = 0;

        return_double = ((drag*AIR_DENSITY*cs_area)/(2*mass));
        return_double = return_double * prev_velocity * prev_velocity;
        return_double = GRAVITY - return_double;
        return_double = return_double * time_step;
        return_double = prev_velocity + return_double;

        return return_double;
    }
    //Void method which takes two arrays, for time and velocity,
    //and one string file name as arguement. Creates an output stream to 
    //write to a new creaated file named the string passed in. Uses a try-
    //catch to determine if it is possible and uses a for loop to write
    //the contents of the arrays to the file, seperated by commas.
    public static void writer(double[] time, double[] velocity, 
    String filename)
    {
        PrintWriter outputStream = null;
        try
        {
            outputStream = new PrintWriter(new FileOutputStream(filename));
            System.out.println("Writing out file.  " + 
            "Here are the first few lines: ");
            for(int i = 0; i < time.length; i++){
                outputStream.println(time[i] + ", " + velocity[i]);
                if(i < 9){
                    System.out.printf("%.3f, ", time[i]);
                    System.out.println(velocity[i]);
                }
            }
            outputStream.close();

        }
        catch (FileNotFoundException e)
        {
            System.out.println("Error creating file " + filename);
            System.exit(0);
        }
    }
    //Void testing method that takes as arguements both arrays for time
    //and velocity, the previous velocity as a double, and a double for
    //the current velocity, along with an integer index for the array.
    //Prints out these values at that index time for testing.
    public static void testVariableValues(double[] time, double[] velocity, 
    double prev_velocity, double current_velocity, int index)
    {
        System.out.print("Time: " + time[index] + ", " + "Velocity: " 
        + velocity[index] + ", " + "Previous velocity: " + 
        prev_velocity + ", " + "Current velocity: " + current_velocity 
        + "\n");
    }
}
