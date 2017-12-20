package views;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;
import org.jetbrains.annotations.NotNull;
import utilites.ToolWindowRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Created by peacefrog on 12/20/17.
 * Time 10:16 AM
 */
public class ClonedCodeView implements ToolWindowFactory , ClonedCodeViewUpdate{
	private ToolWindow toolWindow;
	private JComponent wholePanel;


	private RSyntaxTextArea baseCodeView;
	private RSyntaxTextArea clonedCodeView;
	final JComboBox<String> cb = new JComboBox<String>();

	String changedText ="";

	ArrayList<String[]> cloneList;

	public ClonedCodeView() {
		wholePanel = new JPanel();
		cloneList = new ArrayList<>();

	}

	@Override
	public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
		this.toolWindow = toolWindow;
		ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
		addComponentsToPane();
		Content content = contentFactory.createContent(wholePanel, "", false);
		toolWindow.getContentManager().addContent(content);
		toolWindow.setAutoHide(true);
	}

	private void addComponentsToPane() {
		wholePanel.add(getCodeView(), BorderLayout.CENTER);
		wholePanel.setBounds(0, 0, 200, 200);
	}


	public JPanel getCodeView(){
		JPanel container = new JPanel();
		JPanel panelOne = new JPanel(new BorderLayout());
		JPanel panelTwo = new JPanel(new BorderLayout());

		panelOne.setBounds(0 ,0, 100, 200);
		panelTwo.setBounds(0 ,0, 100, 200);

		baseCodeView = new RSyntaxTextArea(20, 60);
		baseCodeView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		baseCodeView.setCodeFoldingEnabled(true);
		baseCodeView.setEditable(false);
		baseCodeView.setText(changedText);


		clonedCodeView = new RSyntaxTextArea(20, 60);
		clonedCodeView.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_JAVA);
		clonedCodeView.setEditable(false);
		clonedCodeView.setCodeFoldingEnabled(true);
//		clonedCodeView.setText();
		RTextScrollPane sp = new RTextScrollPane(baseCodeView);
		RTextScrollPane sp1 = new RTextScrollPane(clonedCodeView);

		JPanel rightPanel = new JPanel();
		rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS) );

		rightPanel.add(getDropdownList());
		rightPanel.add(sp1);


		panelOne.add(sp);
		panelTwo.add(rightPanel);

		container.setLayout(new GridLayout(1,2));
		container.add(panelOne);
		container.add(panelTwo);

		return container;
	}

	public JPanel getDropdownList(){
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.setBorder(BorderFactory.createTitledBorder("Cloned Codes"));


		String[] choices = { "CHOICE 1", "CHOICE 2", "CHOICE 3", "CHOICE 4",
				"CHOICE 5", "CHOICE 6" };


		cb.setMaximumSize(cb.getPreferredSize()); // added code
		cb.setAlignmentX(Component.CENTER_ALIGNMENT);// added code
		//cb.setVisible(true); // Not needed
		cb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				System.out.println(cb.getItemAt(cb.getSelectedIndex()));
				clonedCodeView.setText(cloneList.get(cb.getSelectedIndex())[1]);
			}
		});


		panel.add(cb);
		return panel;
	}


	@Override
	public void init(ToolWindow window) {
		ToolWindowRepository.getInstance().setCodeView(this);
	}

	@Override
	public boolean shouldBeAvailable(@NotNull Project project) {
		return false;
	}

	@Override
	public boolean isDoNotActivateOnStart() {
		return false;
	}

	@Override
	public void changedSelectedFragment(String changedText) {
		this.changedText = changedText;
		baseCodeView.setText(this.changedText== null? "" : this.changedText);
		this.clonedCodeView.setText("");
	}

	@Override
	public void changeCloneList(ArrayList<String[]> changeList) {
		this.cloneList = changeList;
		this.cb.removeAllItems();
		if(changeList.size() > 0 ){
			this.cloneList.forEach(strings -> cb.addItem(new File(strings[0]).getName()));
			this.cb.setSelectedIndex(0);
		}
		else {

		}
	}
}


