package com.company;
/**
 * In this project, I create a program which generates random numbers by using the Linear Congruential Method.
 * And tests these numbers for uniformity and independence.
 *
 * @author Berke Basara
 * @version 1.0
 * @since 16.12.2021
 * @course Computer Simulation
 */

import java.text.DecimalFormat; // I imported the DecimalFormat method to reduce the number of digits in the results I received.
import java.util.ArrayList; // I listed the random values that I created by importing the ArrayList method.
import java.util.Collections; // By importing the Collections.sort method, I have ordered the values I have listed from smallest to largest.
import java.util.Scanner; // I got input values from the user by importing the Scanner object.


public class Main {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in); // By creating my Scanner object, I get input values from the user.

        System.out.println("GENERATING RANDOM NUMBERS");

        // I ask the user to enter parameters (Xo, a, c, m) to get initial values respectively.
        System.out.println("xO= ");
        double X0 = input.nextDouble();// Scanner object fetch the value by the user.
        System.out.println("a= ");
        double a = input.nextDouble();// Scanner object fetch the value by the user.
        System.out.println("c= ");
        double c = input.nextDouble();// Scanner object fetch the value by the user.
        System.out.println("m= ");
        double m = input.nextDouble();// Scanner object fetch the value by the user.

        double N = 100; // Number of random numbers to be generated.
        int x = 1; // My x value showing the rank of each number.
        double Da = 0.56327; // Level of Significance.


        DecimalFormat decimalFormat = new DecimalFormat("#.###");// Random numbers rounded up to 3 decimal places.
        DecimalFormat decimalFormat1 = new DecimalFormat("#.####"); // Random numbers(between 0-1) rounded up to 4 decimal places.

        double value, newValue; // Generated random number, and new random number.
        value = (a * X0 + c) % m; // Linear Congruential Method formula to generate random number.

        double R, RN; // A 4 decimal place random number(between 0-1), new 4 decimal place random number(between 0-1).
        R = value / m; // It takes it between 0-1 with the division by m.

        System.out.println("\n\nLinear Congruential Method\n"); // Part 1 generating random numbers begins.
        // My initial result.
        System.out.println("X" + x + "= " + decimalFormat.format(value) + "     R" + x + "= " + decimalFormat1.format(R));

        ArrayList<Double> numberList = new ArrayList<>();
        numberList.add(R);// I create arraylist called numberList. It adds random values to the list.

        // In here I create 2 field for Runs Up And Runs Down Test.
        int numberOfIncrease = 0; // For increase group.
        int numberOfDecrease = 0; // For decrease group.
        boolean tf= true; // I created a boolean value to add it to the increase and decrease groups according to whether it meets the if probability or not.

        while (x < N) { // My while loop is mainly used to print Linear Congruential Method values.
            // In addition, it adds the increase and decrease groups in the Runs Up and Runs Down test according to the increase and decrease of the numbers, respectively.


            x++; // Rank increases.
            newValue = (a * value + c) % m; // New random value generated. By using generated old random value.
            RN = newValue / m; // New random value divided by m.

            if (newValue>value)// If new generated random value will be bigger than the past value.
            {

                if (tf) // Then program the program enters the next statement.
                {
                    numberOfIncrease++;// It increases the group increase with the boolean value being true.
                }

                tf = false; // Boolean value will be false.

            }

            else // Otherwise
            {

                if (!tf) // The if statement can only work if the boolean value is false.
                {
                    numberOfDecrease++; // It decreases the group increase with the boolean value being false.
                }

                tf=true; // Boolean value will be true.

            }

            System.out.println("X" + x + "= " + decimalFormat.format(newValue) + "     R" + x + "= " + decimalFormat1.format(RN)); // New generated random result.

            value = newValue; // New value become old value.
            numberList.add(RN); // New generated random value(between 0-1) will add numberList arraylist.


        }// While loop ends.


        System.out.println("\n\n\nTest of Uniformity\n"// Part 2 Test of Uniformity begins.
                +"Kolmogorov-Smirnov Test\n");

        if (N > 35) { // If the N value is more than 35, uniformity test and independence test are performed.

            Collections.sort(numberList); // numberList is being sorted.
            int i = 1; // My i value showing the rank of each number.


            ArrayList<Double> D_positive = new ArrayList<>(); // D+ arraylist.
            ArrayList<Double> D_negative = new ArrayList<>(); // D- arraylist.

            for (double numberRank : numberList) { // My foreach loop, for using general number list.

                System.out.print(i + "=  " + decimalFormat1.format(numberRank) + "  " + (i / N) + "  "); // Respectively: Number of Rank, Random number, percentage of number.

                if ((i / N) - numberRank >= 0) // If D+ values are provided.
                {

                    double resultNumber = ((i / N) - numberRank);
                    System.out.print(decimalFormat1.format(resultNumber) + "  "); // Then result number will be printed between percentage of number.
                    D_positive.add(resultNumber); // D+ adds the resultNumber in arraylist.

                } else {
                    System.out.print("  -  "); // It turns out that D+ is negative. So result will be empty.

                }


                if ((numberRank - ((i - 1) / N)) >= 0) // If D- values are provided.
                {
                    double newResultNumber = (numberRank - ((i - 1) / N));
                    System.out.println(decimalFormat1.format(newResultNumber)); // Then result number will be printed between percentage of number.
                    D_negative.add(newResultNumber); // D- adds the resultNumber in arraylist.

                } else {
                    System.out.println("-"); // It turns out that D- is negative. So result will be empty.

                }

                i++; // Rank increases.

            }

            DecimalFormat decimalFormatUniformity = new DecimalFormat("#.#####"); // Reduce the number of digits in 5 decimal places for D+ and D- values.

            double maxPositiveD = Collections.max(D_positive); // I fetch max value in D+ list.
            System.out.println("\n\nMax Positive D value= " + decimalFormatUniformity.format(maxPositiveD)); // Max positive value.

            double maxNegativeD = Collections.max(D_negative); // I fetch max value in D- list.
            System.out.println("Max Negative D value= " + decimalFormatUniformity.format(maxNegativeD) + "\n\n"); // Max negative value.


            System.out.println("D= max{ " + decimalFormatUniformity.format(maxPositiveD) + ", " + decimalFormatUniformity.format(maxNegativeD) + "}"); // It max D+ and max D-value in parentheses.

            double D = Math.max(maxPositiveD, maxNegativeD);
            System.out.println("D= " + decimalFormatUniformity.format(D)); // Fetch the max D value.
            System.out.println("Da= " + Da); // Shows Da value.

            DecimalFormat resultTest = new DecimalFormat("#.##"); // Reduce the number of digits in 2 decimal places for D and Da.

            if (Da > D) // If Da value is bigger than D value.
            {
                System.out.println(resultTest.format(Da) + " > " + resultTest.format(D) + ":  Numbers are uniform(Da > D). \n" +
                        " We don’t have enough evidence to reject the null hypothesis  at the level of significance a=0.05.\n\n");
                //Then result of independence test will be completed successfully.

            } else // Otherwise
            {
                System.out.println(resultTest.format(Da) + " < " + resultTest.format(D) + ":  Numbers are not uniform. ");
                // Result not uniform. So values are dependent.
            }


            System.out.println("\nChecks Independence Of Random Numbers\n" + // Part 3 Runs Up And Runs Down Test begins.
                    "Runs Up And Runs Down Test\n");

            double sumNumbersIncreaseDecrease = numberOfIncrease + numberOfDecrease + 1;// Sum of ascending and descending groups in the while loop.Reason for adding 1.
            // I'm taking it because I didn't skip the first element.
            double Za = 1.9600; // My critical value.
            double Sigma = (((16 * N) - 29) / 90); // Sigma formula.
            double Mu = ((2 * N) - 1) / 3; // Mu formula.
            double Zo = (sumNumbersIncreaseDecrease - Mu) / Math.sqrt(Sigma); // Calculating program output.

            System.out.println("Zo= " + decimalFormat1.format(Zo) + "\n"); // Shows output of Zo.

            // In here output value compare between critical values.
            System.out.println("-Za= " + -Za);
            System.out.println("Zo= " + resultTest.format(Zo)); // Reduce the number of digit in 2 decimal places.
            System.out.println("Za= " + Za);

            if (-Za <= Zo && Zo <= Za) // It turns out to be independent if the calculated value is between critical values.
            {
                System.out.println("Numbers are independent(-Za <= Zo <= Za). \n" +
                        "The null hypothesis of independence cannot be rejected.");
            } else // Otherwise, the generation of random numbers will fail.
            {
                System.err.println("Numbers are not independent.");
            }


        }
        else // If not, the random number generation is completed unsuccessfully.
        {
            System.err.println("The simulation failed because there were not enough N values."); // Error message.

        }
    }
}
