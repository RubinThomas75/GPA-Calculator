/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package gpa.calculator;


public class Course {
    String courseName;
    double courseGPA;
    String courseLetterGrade;
    int courseCredits;
    int semester;
    
    public Course(String name, String clg, int credits, int sem){

            if(clg.equals("A"))
                courseGPA = 4.0;
            else if(clg.equals("A-"))
                courseGPA = 3.7;
            else if(clg.equals("B+"))
                courseGPA = 3.3;
            else if(clg.equals("B"))
                courseGPA = 3.0;
            else if(clg.equals("B-"))
                courseGPA = 2.7;
            else if(clg.equals("C+"))
                courseGPA = 2.3;
            else if(clg.equals("C"))
                courseGPA = 2.0;
            else if(clg.equals("C-"))
                courseGPA = 1.7;
            else if(clg.equals("F"))
                courseGPA = 0.0;
            
            courseLetterGrade = clg;
            courseName = name;
            courseCredits = credits;
            semester = sem;
    }
    
    public String toString(){
        return (courseName + " " + courseLetterGrade + " " + String.valueOf(courseCredits) + " " + String.valueOf(semester));
    }
}
