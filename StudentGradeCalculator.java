import java.util.Scanner;

class StudentGradeCalculator 
{
    public static void main(String[] args) 
    {
        Scanner scanner = new Scanner(System.in);
        
        // Input: Student's name
        System.out.print("Enter your name: ");
        String studentName = scanner.nextLine();
        
        // Input: Number of subjects
        System.out.print("Enter the number of subjects: ");
        int subjectCount = scanner.nextInt();
        
        // Array to store marks and variable to calculate the total
        int[] subjectMarks = new int[subjectCount];
        int totalMarks = 0;
        
        // Loop to input marks for each subject and calculate the total marks
        for (int i = 0; i < subjectCount; i++) 
        {
            System.out.print("Enter marks obtained in subject " + (i + 1) + " (out of 100): ");
            subjectMarks[i] = scanner.nextInt();
            totalMarks += subjectMarks[i];
        }
        
        // Calculate average percentage
        double averagePercentage = (double) totalMarks / subjectCount;
        
        // Determine the grade based on average percentage
        char grade;
        if (averagePercentage >= 90) 
        {
            grade = 'A';
        } 
        else if (averagePercentage >= 80) 
        {
            grade = 'B';
        } 
        else if (averagePercentage >= 70) 
        {
            grade = 'C';
        } 
        else if (averagePercentage >= 60) 
        {
            grade = 'D';
        } 
        else 
        {
            grade = 'F';
        }
        
        // Output: Display the results
        System.out.println("\n--- Results ---");
        System.out.println("Student Name: " + studentName);
        System.out.println("Total Marks: " + totalMarks);
        System.out.println("Average Percentage: " + averagePercentage + "%");
        System.out.println("Grade: " + grade);
        
        scanner.close();
    }
}
