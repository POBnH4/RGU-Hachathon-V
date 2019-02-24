package com.company;
import javax.swing.*;
import java.awt.*;

import static com.company.Main.callBackEnd;

public class AppGUI extends JFrame{
	private JTextField textBox;
	private JTextArea ingredients, directions;
	private JButton button;
	
	public void initGUI()
    {
        Container container = getContentPane(); // reference to content pane of JFrame
        setTitle("RecipeBot GUI"); // set title of frame
        setSize(1000, 600); // set initial size of frame (x,y) in pixels

        // creating text fields on top of GUI
        // create a Panel to group together currentTime and timeleft TextFIelds
        JPanel aPanel = new JPanel();
        aPanel.setPreferredSize(new Dimension(800, 100));
        aPanel.setLayout(new GridBagLayout());// change panel layout to grid layout
        
        textBox = new JTextField("", 20);
        //doesn't work no idea why
        textBox.setPreferredSize(new Dimension(424,20));
        textBox.setHorizontalAlignment(JTextField.CENTER);
        textBox.setEditable(true);
        textBox.setEnabled(true);
        //preferred size won't change
        //System.out.println(textBox.getPreferredSize());
        aPanel.add(textBox);
        
        button = new JButton("Cook!");
        button.addActionListener(e -> {
        	//clear text area
        	ingredients.setText(null);
        	ingredients.append("\n");
        	directions.setText(null);
        	directions.append("\n");
        	
        	//generate new text
            String input = textBox.getText();
            String[] output = callBackEnd(input);
            String[] temp1 = output[0].split(",");
           
            for (String step : temp1)
            {
            	directions.append(step + "\n");
            }
            
            String[] temp2 = output[1].split(",");
            for (String ingredient : temp2)
            {
            	ingredients.append("\t" + ingredient + "\n");
            }
            
            //directions.setText(output[0]); // get directions;
            //ingredients.setText(output[1]); // get ingredients

        });
        aPanel.add(button);
        
        container.add(aPanel, BorderLayout.NORTH); // add panel to east side of frame
        
        aPanel = new JPanel();
        aPanel.setLayout(new GridLayout(1, 2));// change panel layout to grid layout
        
        ingredients = new JTextArea();
        //ingredients.setHorizontalAlignment(JTextField.CENTER);
        ingredients.setEditable(false);
        ingredients.setFont(new Font("SansSerif", Font.PLAIN, 24));;
        //System.out.println(ingredients.getFont());
        aPanel.add(ingredients);
        
        directions = new JTextArea();
        //directions.setHorizontalAlignment(JTextField.CENTER);
        directions.setLineWrap(true);
        directions.setWrapStyleWord(true);
        directions.setFont(new Font("SansSerif", Font.PLAIN, 20));;
        directions.setEditable(false);
        aPanel.add(directions);
        
        container.add(aPanel, BorderLayout.CENTER); // add panel to east side of frame
    }
	
}
