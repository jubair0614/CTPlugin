package views;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by jubair on 11/25/17.
 * Time 2:57 AM
 */
public class ClonesView implements ToolWindowFactory{
	static final String gapList[] = {"0", "10", "15", "20"};
	final static int maxGap = 20;
	JComboBox horGapComboBox;
	JComboBox verGapComboBox;
	JButton applyButton = new JButton("Apply gaps");
	GridLayout experimentLayout = new GridLayout(0,2);

	private JPanel jPanel;
	private ToolWindow toolWindow;

	public ClonesView(){

	}

	public void initGaps() {
		horGapComboBox = new JComboBox(gapList);
		verGapComboBox = new JComboBox(gapList);
	}

	public void addComponentsToPane(final Container pane) {
		initGaps();
		final JPanel compsToExperiment = new JPanel();
		compsToExperiment.setLayout(experimentLayout);
		JPanel controls = new JPanel();
		controls.setLayout(new GridLayout(2,3));

		//Set up components preferred size
		JButton b = new JButton("Just fake button");
		Dimension buttonSize = b.getPreferredSize();
		compsToExperiment.setPreferredSize(new Dimension((int)(buttonSize.getWidth() * 2.5)+maxGap,
				(int)(buttonSize.getHeight() * 3.5)+maxGap * 2));

		//Add buttons to experiment with Grid Layout
		compsToExperiment.add(new JButton("Button 1"));
		compsToExperiment.add(new JButton("Button 2"));
		compsToExperiment.add(new JButton("Button 3"));
		compsToExperiment.add(new JButton("Long-Named Button 4"));
		compsToExperiment.add(new JButton("5"));

		//Add controls to set up horizontal and vertical gaps
		controls.add(new Label("Horizontal gap:"));
		controls.add(new Label("Vertical gap:"));
		controls.add(new Label(" "));
		controls.add(horGapComboBox);
		controls.add(verGapComboBox);
		controls.add(applyButton);

		//Process the Apply gaps button press
		applyButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//Get the horizontal gap value
				String horGap = (String)horGapComboBox.getSelectedItem();
				//Get the vertical gap value
				String verGap = (String)verGapComboBox.getSelectedItem();
				//Set up the horizontal gap value
				experimentLayout.setHgap(Integer.parseInt(horGap));
				//Set up the vertical gap value
				experimentLayout.setVgap(Integer.parseInt(verGap));
				//Set up the layout of the buttons
				experimentLayout.layoutContainer(compsToExperiment);
			}
		});
		pane.add(compsToExperiment, BorderLayout.NORTH);
		pane.add(new JSeparator(), BorderLayout.CENTER);
		pane.add(controls, BorderLayout.SOUTH);
	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		this.toolWindow = toolWindow;
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		addComponentsToPane(jPanel);
		Content content = contentFactory.createContent(jPanel, "", false);
		toolWindow.getContentManager().addContent(content);
	}

	@Override
	public void init(ToolWindow window) {

	}

	@Override
	public boolean shouldBeAvailable(@NotNull Project project) {
		return false;
	}

	@Override
	public boolean isDoNotActivateOnStart() {
		return false;
	}
}
