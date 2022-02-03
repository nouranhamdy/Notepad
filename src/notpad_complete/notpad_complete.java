/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMain.java to edit this template
 */
package notpad_complete;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author noura
 */
public class notpad_complete extends Application {
    
   MenuBar bar;
    Menu file, edit,help;
    MenuItem newf,openf,savef,exitf;
    MenuItem undo,cut,copy,paste,delete,select;
    MenuItem about;
    TextArea txt;
    BorderPane root;
    Scene scene;
    String str;
    Stack s;
    boolean unsaved;
    Label l;
    //Dialog<String> dialogAbout;
    FileChooser choose;
    FileChooser choose2;
            
    @Override
    public void init(){
        unsaved=true;
        
        
        System.err.println("ddddddddddddddddddddddd");
        
        
        
        file=new Menu("File");
        newf=new MenuItem("New");
        newf.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.clear();
            }
            
        });
        openf=new MenuItem("Open");
        
        
        savef=new MenuItem("Save");
        savef.setAccelerator(KeyCombination.keyCombination("z"));
        choose=new FileChooser();
        choose.setTitle("Save");
        choose.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));
        
        exitf=new MenuItem("Exit");
        
        
        file.getItems().addAll(newf,openf,savef,exitf);
        
        edit=new Menu("Edit");
        undo=new MenuItem("Undo");
        undo.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                
                txt.undo();
                
            }
            
        });
        cut=new MenuItem("Cut");
        cut.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.cut();
            }
            
        });
        copy=new MenuItem("Copy");
        copy.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.copy();
                
            }
            
        });
        paste=new MenuItem("Paste");
        paste.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.paste();
            }
            
        });
        delete=new MenuItem("Delete");
        delete.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.deleteNextChar();
            }
            
        });
        
        select=new MenuItem("Select");
        select.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                txt.selectAll();
            }
            
        });
        
        edit.getItems().addAll(undo,cut,copy,paste,delete,select);
        
        help=new Menu("Help");
        about=new MenuItem("About Notepad");
        
        
        help.getItems().addAll(about);
        
        bar=new MenuBar();
        bar.getMenus().addAll(file,edit,help);
        
        txt=new TextArea();
        
        
        root=new BorderPane();
        root.setTop(bar);
        root.setCenter(txt);
        root.setBottom(l);
        
        
        scene=new Scene(root,1000,1000);
    }
    @Override
    public void start(Stage primaryStage) {

Dialog<String> dialogAbout = new Dialog<String>();
        dialogAbout.setTitle("About");        
        ButtonType b=new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
        dialogAbout.getDialogPane().getButtonTypes().add(b);
        dialogAbout.setContentText("Nouran intake 42");

        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Open");        
        ButtonType b2=new ButtonType("OK",ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().add(b2);


        about.setOnAction(new EventHandler<ActionEvent>(){
           
            @Override
            public void handle(ActionEvent event) {
                dialogAbout.showAndWait();
            }
            
        });

        savef.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                //System.out.println(txt.getText());
                str=txt.getText();
                File fsave = null;
                if(unsaved){ 
                fsave=choose.showSaveDialog(primaryStage);
                unsaved=false;
                }
                try{
                    FileWriter fw=new FileWriter(fsave.getAbsolutePath());
                    fw.write(txt.getText());
                    fw.close();
                    
                } catch (IOException ex) {
                    Logger.getLogger(notpad_complete.class.getName()).log(Level.SEVERE, null, ex);
                }
                

                
            }
            
        });
        
        openf.setOnAction(new EventHandler<ActionEvent>(){
            File f;
            
            @Override
            public void handle(ActionEvent event) { 
                choose2=new FileChooser();
                choose2.setTitle("Open file");
                f=choose2.showOpenDialog(primaryStage);
                if(f!=null){
                    
                    txt.clear();
                    try{
                        FileReader fr=new FileReader(f.getAbsolutePath());
                        char[] buffer=new char[(int)f.length()];
                        fr.read(buffer);
                        System.out.println(buffer);
                        for(char c:buffer){
                            txt.appendText(Character.toString(c));
                        }
                        
                        fr.close();
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(notpad_complete.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(notpad_complete.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }       
            }
        });
        
        
        
        exitf.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                primaryStage.hide();
            }
            
        });
        
        
        
        primaryStage.setTitle("Notepad");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
