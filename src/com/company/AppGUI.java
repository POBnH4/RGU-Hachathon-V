package com.company;
import javax.swing.*;
import java.awt.*;

import static com.company.Main.callBackEnd;

public class AppGUI extends JFrame{
	private JTextField textBox, ingredients, directions;
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
            String input = textBox.getText();
            String[] output = callBackEnd(input);
            directions.setText(output[0]); // get directions;
            ingredients.setText(output[1]); // get ingredients

        });
        aPanel.add(button);
        
        container.add(aPanel, BorderLayout.NORTH); // add panel to east side of frame
        
        aPanel = new JPanel();
        aPanel.setLayout(new GridLayout(1, 2));// change panel layout to grid layout
        
        ingredients = new JTextField("ingredients go here", 20);
        ingredients.setHorizontalAlignment(JTextField.CENTER);
        ingredients.setEditable(false);
        ingredients.setFont(new Font("SansSerif", Font.PLAIN, 24));;
        //System.out.println(ingredients.getFont());
        aPanel.add(ingredients);
        
        directions = new JTextField("directions go here", 20);
        directions.setHorizontalAlignment(JTextField.CENTER);
        directions.setFont(new Font("SansSerif", Font.PLAIN, 24));;
        directions.setEditable(false);
        aPanel.add(directions);
        
        container.add(aPanel, BorderLayout.CENTER); // add panel to east side of frame
    }
	
}
