/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
 
package gpa.calculator;

/**
 *
 * @author Rubin1
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.ObservableList;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;

public class GPA extends Application{

    /**
     * @param args the command line arguments
     */
    
    ArrayList<String> majorList = new ArrayList<String>();
    Integer semesterCounter = 1;
    ArrayList<Course> courses = new ArrayList<Course>();
    List<String> possGrades = Arrays.asList("A", "A-", "B+", "B", "B-", "C+", "C", "C-", "F");
          
    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("My GPA");
       File file = new File("GPASaveState.txt");
        if(file.isFile() && file.length() != 0)
           createList(file, primaryStage);
        else
            initWindow(primaryStage);
         
        primaryStage.show();
        
    }
    public void initWindow(Stage primaryStage){
        Scene main, welcome;
        Pane one = new Pane();
        //Prompt Major Label
        Label prompt1 = new Label("What is your major?");   
        prompt1.setFont(new Font("Arial", 20));
        prompt1.setTranslateX(50);                      // LABEL X SHIFTED 50 
        prompt1.setTranslateY(30);                                
        TextField major = new TextField();
        major.setTranslateX(50);
        major.setTranslateY(60);
        Button addMajor = new Button("+");              //add hover over function "press to add another major"
        addMajor.setTranslateX(110);
        addMajor.setTranslateY(90);
        addMajor.setOnAction(e -> addOneMajor(one, addMajor));
        Button cont = new Button("Continue");
        cont.setTranslateX(250);
        cont.setTranslateY(90);
        one.getChildren().addAll(prompt1, major, addMajor, cont);
        welcome = new Scene(one, 350, 400);
        primaryStage.setScene(welcome);
        
        courses.clear();
        setUpEditScreen(cont, one, primaryStage);
       
        
    }
    
    public void setUpEditScreen(Button cont, Pane one, Stage primaryStage){
        Scene edit;
        VBox two = new VBox(10);
        ScrollPane sp = new ScrollPane(); 
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setContent(two);
        BorderPane mainScreen = new BorderPane();
        mainScreen.setCenter(sp);
        edit = new Scene(mainScreen, 350, 500);
        if(courses.isEmpty())
            cont.setOnAction(e -> 
                 processMajorData(one.getChildren(), primaryStage, edit)
             );
        
        // WINDOW 2, EDIT SCREEN
        //This adds a course // 
        
        Button addSemButton = new Button("+");
        Button calcAndCont = new Button("Done Editing");
        calcAndCont.setTranslateX(10);
        addSemButton.setTranslateX(10);
        addSemButton.setTranslateY(0); // This needs to be fixed
        addSemester(two, semesterCounter++, addSemButton, calcAndCont);

        addSemButton.setOnAction(e-> 
                addSemester(two, semesterCounter++, addSemButton, calcAndCont)
        );
                
        VBox sp2 = new VBox(10);
       // main = new Scene(sp2, 350, 350);
        
        if(!courses.isEmpty())
            fillEdit(two);
        
        calcAndCont.setOnAction(e->
                processData(two.getChildren(), mainScreen, sp2)
         );
    }
    
    public void fillEdit(VBox edit){
        //Fills edit screen with loaded courses
    }
    
    public void addOneMajor(Pane pane, Button button){
        TextField t = new TextField();
        int counter = 0;
        for(int i = 0; i < pane.getChildren().size(); i++){
            if(pane.getChildren().get(i) instanceof TextField)
                counter++;
        }
        t.setTranslateX(50);
        t.setTranslateY(30*(2+counter));
        button.setTranslateY(30*(3+counter));                  //MAYBE ADD CAP TO 3 MAJORS.
        pane.getChildren().add(t);                             //REMEMBER TO ADD MINUS TEXT FIELD OPTION
        
    }
    
    public void processMajorData(ObservableList<Node> list, Stage primaryStage, Scene scene){
          primaryStage.setScene(scene);
          for(Node n: list){
              if(n instanceof TextField){
                  TextField n2 = (TextField)n;
                  majorList.add(n2.getText()); // WORKS
                }
          }
          
          
    }
    
    public void addSemester(VBox window, Integer semesterCounter, Button button, Button button2){
        window.getChildren().remove(button);
        window.getChildren().remove(button2);
        GridPane semester = new GridPane();
        semester.setHgap(10);
        semester.setVgap(10);
        Label semesterLabel = new Label("Semester " + semesterCounter);
        semesterLabel.setFont(new Font("Arial", 20));
        semester.add(semesterLabel, 1, 1);
        for(int i = 2; i < 8; i+=2){
             semester.add(new TextField(), 1, i);
             ChoiceBox<String> grade = new ChoiceBox<>();
             grade.getItems().addAll(possGrades);
             semester.add(grade, 2, i);
             TextField temp2 = new TextField();
             temp2.setPrefWidth(70);
             semester.add(temp2, 3, i);
        }
        Button addOneCourse = new Button("+");
        semester.add(addOneCourse, 2, 8);
        addOneCourse.setOnAction(e->
                addOneCourse(semester, addOneCourse)
        );
        window.getChildren().add(semester);
        window.getChildren().add(button);
        window.getChildren().add(button2);
        //window.getChildren().lastIndexOf(semester);
        //Add another course button
    }
    
    public void addOneCourse(GridPane pane, Button button){
        int counter = 0;
        for(int i = 0; i < pane.getChildren().size(); i++){
            if(pane.getChildren().get(i) instanceof TextField)
                counter++;
        }
        counter = ((counter/2) * 2) + 2;
        pane.getChildren().remove(button);
             pane.add(new TextField(), 1, counter);        
             ChoiceBox<String> grade = new ChoiceBox<>();
             grade.getItems().addAll(possGrades);
             pane.add(grade, 2, counter);
             TextField temp2 = new TextField();
             temp2.setPrefWidth(70);
             pane.add(temp2, 3, counter);
        pane.add(button, 2, counter + 2);
    }
   
    public void processData(ObservableList<Node> list, BorderPane mainPane, VBox scene){
          courses.clear();
          scene.getChildren().clear();
           ObservableList<Node> two = mainPane.getChildren();
           if(two.size() > 1)
               two.remove(two.size() - 1);
           
          ArrayList<Integer> courseSemCount = new ArrayList<Integer>();
          
          //primaryStage.setScene(scene);
          mainPane.setBottom(scene);
          ArrayList<TextField> TFList = new ArrayList<TextField>();
          ArrayList<String> gradeList = new ArrayList<String>();
          
          for(Node n: list){
              if(n instanceof GridPane){
                  int quickCount = 0;
                  GridPane n2 = (GridPane)n; //Cast
                  ObservableList<Node> list2 = n2.getChildren();
                  for(Node n3: list2)
                    if(n3 instanceof TextField){
                          TextField t = (TextField)n3;
                          if(!(t.getText().trim().isEmpty())){
                            quickCount++;
                            TFList.add(t);
                          }
                    } else if(n3 instanceof ChoiceBox) {
                        ChoiceBox<String> n4 = (ChoiceBox)n3;
                        if(!n4.getSelectionModel().isEmpty()){
                            gradeList.add(n4.getValue());
                        }
                    }
                  courseSemCount.add(quickCount/2); // how many classes were taken that semester, for save state purpose.
              }
          }
          
          //ERROR HANDLING ON EMPTY TEXTFIELDS ** NEEDED.
          
          // FOLLOWING CODE CREATES A COURSE FOR EACH LINE IN THE GRIDPANE, GIVEN !EMPTY CELLS.
          int temp = 0;
          double lastNum = 0;
          if(courses.isEmpty()){
          for(int i = 0; i < TFList.size(); i += 2){  
              
                 double j = i/2.0;
                 if(j == (double)courseSemCount.get(temp) + lastNum){
                     temp++; 
                     lastNum = j;
                 }
                 
                 Course c = new Course(TFList.get(i).getText(), gradeList.get(i/2), Integer.parseInt(TFList.get(i+1).getText()), temp+1);
                 courses.add(c);
          }
          

          Writer writer = null;

            try {
                
                 writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("GPASaveState.txt"), "utf-8"));
                 writer.write(majorList.toString());
                 writer.write("\n");
                 writer.write(courses.toString());
            } catch (IOException ex) {
                 // report
            } finally {
                try {writer.close();} catch (Exception ex) {/*ignore*/}
            }
          }
          
          Label cumLabel = new Label("Cum GPA: " + calculateGPA(courses));
          cumLabel.setFont(new Font("Arial", 20));
          scene.getChildren().add(cumLabel);
          
           Button semesterBreakdown = new Button("View Breakdown");
          scene.getChildren().add(semesterBreakdown);
          
           // displayScreenThree(display);                  // DISPLAYS THE SCREEN THREE< CHANGING TO LOWER PANE > 
          
    }
    
    public void displayScreenThree(VBox display){
          Label cumLabel = new Label("Cum GPA: " + calculateGPA(courses));
          cumLabel.setFont(new Font("Arial", 20));
          display.getChildren().add(cumLabel);
          
          if(!(courses.isEmpty()))
            semesterBreakDown(display);
          
          Button backToEdit = new Button("Edit/Add Courses");
          display.getChildren().add(backToEdit);
          
    }
    
    public void semesterBreakDown(VBox display){
        //Need semester Label
            // 1 Course Label per Course for all courses in semester.
        int lastSem = courses.get(courses.size()-1).semester;
        int j = 0;
        for(int i = 0; i < lastSem; i++){
            
            Label semesterLabel = new Label("Semester " + (i+1));
            semesterLabel.setFont(new Font("Arial", 20));
            display.getChildren().add(semesterLabel);
            while(j < courses.size() && courses.get(j).semester == (i+1)){
                Label courseLabel = new Label("     " + courses.get(j).courseName + "      " + courses.get(j).courseLetterGrade + "      " + courses.get(j).courseCredits);
                courseLabel.setFont(new Font("Arial", 18));
                display.getChildren().add(courseLabel);
                j++;
            }
        }
    }
    
    public double calculateGPA(ArrayList<Course> courses){
        int totalCH = 0;
        double GPA = 0;
        for(Course c: courses){
            GPA += c.courseGPA * c.courseCredits;
            totalCH += c.courseCredits;
        }
        
        return GPA/totalCH;
         
    }
    

    
    public void processEditScreen(){
        ///this needs to be able to function with prior COURSES. and with no data.
    }
    
    public void createList(File file, Stage primaryStage) throws FileNotFoundException, IOException{
        BufferedReader br = new BufferedReader(new FileReader(file));
        String majors = br.readLine(); // What if you dont input majors?
        String readCourses = br.readLine(); 
        majors = majors.substring(1, majors.length()-1);
        readCourses = readCourses.substring(1, readCourses.length() - 1);
        String[] majorsTwo = majors.split(", ");
        
        for(int i = 0; i < majorsTwo.length; i++){
            majorList.add(majorsTwo[i]);
        }
        
         String[] indvCourses = readCourses.split(", ");
         
         for(int i = 0; i < indvCourses.length; i++){
             String[] tempCourse = indvCourses[i].split(" ");
             Course c = new Course(tempCourse[0], tempCourse[1], Integer.parseInt(tempCourse[2]), Integer.parseInt(tempCourse[3]));
             courses.add(c);
         }
         
        /*
        VBox three = new VBox(10);
        ScrollPane sp2 = new ScrollPane();
        sp2.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp2.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp2.setContent(three);
        Scene main = new Scene(sp2, 350, 350);
        primaryStage.setScene(main);
        displayScreenThree(three);
        */
         
    //so reads in in form [ CLASSNAME GRADE CH SEM, CLASSNAME GRADE CH SEM]
    }
    //Reads Saved Data
    // Initializes Windows, and GUI event handlers.
    

 }
    