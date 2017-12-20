package views;

import actions.CaretPositionFragment;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;
import utilites.ToolWindowRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

/**
 * Created by jubair on 11/25/17.
 * Time 2:57 AM
 */
public class ClonesView implements ToolWindowFactory, CaretPositionFragment.ViewUpdateListener{

	private DefaultTableModel model;
	private ToolWindow toolWindow;
	private JComponent wholePanel;
	private JPanel allClonesPanel;
	private JTable clonePositions;

	Object data[][]={ {}};
	Object headers[]={"Path","StartLine","EndLine", "Temp"};


	public ClonesView(){
		wholePanel = new JPanel();
		allClonesPanel = new JPanel();
		model = new DefaultTableModel() {

			@Override
			public boolean isCellEditable(int row, int column) {
				//all cells false
				return false;
			}


		};

		for (Object header : headers) {
			model.addColumn(header);
		}
	}

	public void addComponentsToPane() {
		clonePositions = new JTable(model);
		clonePositions.removeColumn(clonePositions.getColumnModel().getColumn(3));
		clonePositions.getColumnModel().getColumn(0).setMinWidth(200);
		clonePositions.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = clonePositions.rowAtPoint(e.getPoint());
				int col = clonePositions.columnAtPoint(e.getPoint());

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}
		});
		JScrollPane sp = new JScrollPane(clonePositions);
		clonePositions.setBounds(30,40,200,300);
		allClonesPanel.add(sp);

		setActionListener();

		wholePanel.add(allClonesPanel, BorderLayout.NORTH);
		wholePanel.add(new JSeparator(), BorderLayout.CENTER);
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

	@Override
	public void init(ToolWindow window) {
		ToolWindowRepository.getInstance().setView(this);
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
	public void update(ArrayList<String[]> list) {
		while (model.getRowCount() > 0){
			model.removeRow(model.getRowCount()-1);
		}
		list.forEach(strings -> model.addRow(strings));
	}

	private void setActionListener() {
		clonePositions.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
					int row = target.getSelectedRow();
					int column = target.getSelectedColumn();
					// do some action if appropriate column
					System.out.println(row);
					System.out.println(model.getValueAt(row, 3));
				}
			}
		});
	}

}
