/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry;

import java.awt.EventQueue;
import java.awt.event.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import javax.swing.GroupLayout;
import javax.swing.JFileChooser;
import javax.swing.filechooser.*;
import javax.swing.JPanel;
import javax.swing.Box;
import java.awt.GridLayout;
import javax.swing.BoxLayout;

import gfhund.jtelemetry.component.Diagram;
import gfhund.jtelemetry.component.Track;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import gfhund.jtelemetry.f1y18.F1Y2018Packets;
import java.nio.charset.Charset;
import gfhund.jtelemetry.data.AbstractPackets;
import gfhund.jtelemetry.f1y18.F1Y2018ParseException;
/**
 *
 * @author PhilippGL
 */
public class SwingMain extends JFrame{
    private AbstractPackets m_packetManager;
    
    public SwingMain(){
        setTitle("jTelemetry");
        setSize(800,600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                fileOpenAction();
            }
        });
        fileMenu.add(openMenuItem);
        setJMenuBar(menuBar);

        String[] columnNames = {"First Name",
                        "Last Name",
                        "Sport",
                        "# of Years",
                        "Vegetarian"};
        Object[][] data = {
            {"Kathy", "Smith",
                "Snowboarding", new Integer(5), new Boolean(false)},
            {"John", "Doe",
                "Rowing", new Integer(3), new Boolean(true)},
            {"Sue", "Black",
                "Knitting", new Integer(2), new Boolean(false)},
            {"Jane", "White",
                "Speed reading", new Integer(20), new Boolean(true)},
            {"Joe", "Brown",
                "Pool", new Integer(10), new Boolean(false)}
        };
        JTable table = new JTable(data,columnNames);

        Diagram diagram = new Diagram();
        Track track = new Track();
        
        JPanel pane = (JPanel) getContentPane();

        //getContentPane().setLayout(new GridLayout(0,2));
        getContentPane().setLayout(new BoxLayout(pane,BoxLayout.Y_AXIS));

        Box firstRow = Box.createHorizontalBox();
        firstRow.add(diagram);
        firstRow.add(track);
        Box secondRow = Box.createHorizontalBox();
        secondRow.add(table);

        getContentPane().add(firstRow);
        getContentPane().add(secondRow);
        
        /*
        JPanel pane = (JPanel) getContentPane();
        GroupLayout gl = new GroupLayout(pane);
        pane.setLayout(gl);
        gl.setAutoCreateGaps(true);
        gl.setAutoCreateContainerGaps(true);
        GroupLayout.SequentialGroup hGroup = gl.createSequentialGroup();

        hGroup.addGroup(gl.createParallelGroup()
        .addComponent(diagram,100,500,1000)
        .addComponent(track,100,500,1000)
        .addGroup(gl.createParallelGroup()
        .addComponent(table,100,500,1000)
        ));
        //gl.setHorizontalGroup(gl.createSequentialGroup().addComponent(table));
        gl.setHorizontalGroup(hGroup);

        GroupLayout.SequentialGroup vGroup = gl.createSequentialGroup();
        vGroup.addGroup(gl.createParallelGroup()
        .addComponent(diagram,100,200,500)
        .addComponent(table,100,200,500)
        .addGroup(gl.createParallelGroup()
        .addComponent(track,100,200,500)));
        gl.setVerticalGroup(vGroup);
        */
    }
    
    public void fileOpenAction(){
        System.out.println("Hallo Welt");
        JFileChooser fileDialog = new JFileChooser();
        fileDialog.setFileFilter(new FileNameExtensionFilter("Formel1 2018",".f1data"));
        int state = fileDialog.showOpenDialog(null);
        if(state == JFileChooser.APPROVE_OPTION){
            File file = fileDialog.getSelectedFile();
            long fileSize = file.length();
            if(fileSize<=0){
                JOptionPane.showMessageDialog(null, "Datei ist kleiner gleich 0", "Falsche Datei größe", JOptionPane.ERROR_MESSAGE);
                return;
            }
            String sFileContent = "";
            try{
                byte[] fileContent = Files.readAllBytes(file.toPath());
                sFileContent = new String(fileContent, Charset.forName("ascii"));
            }catch(IOException e){
                JOptionPane.showMessageDialog(null, "Cannot read File. Reason:"+e.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
            }
            if(sFileContent.isEmpty()){
                JOptionPane.showMessageDialog(null, "Cannot read File. Reason: "+"File is empty", "File Error", JOptionPane.ERROR_MESSAGE);
            }
            try{
                m_packetManager = F1Y2018Packets.parse(sFileContent);
            }catch(F1Y2018ParseException e){
                JOptionPane.showMessageDialog(null, "Cannot parse File. Reason:"+e.getMessage(), "Parse Error", JOptionPane.ERROR_MESSAGE);
            }
            
        }
    }
}
