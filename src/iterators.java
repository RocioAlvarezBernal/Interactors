import acm.graphics.*;
import acm.program.*;
import java.util.*;
import java.awt.event.*;
import javax.swing.*;

public class iterators extends GraphicsProgram {
	private static final int MAX = 25;
	private static final double BOX_WIDTH = 120;
	private static final double BOX_HEIGHT = 25;

	private HashMap<String, GObject> contents;
	private JTextField textField;
	private JButton add;
	private JButton remove;
	private JButton clear;
	private GObject currentObject;
	private GPoint last;

	public void init() {
		contents = new HashMap<String, GObject>();
		createController();
		addActionListeners();
		addMouseListeners();
	}

	private void createController() {
		textField = new JTextField(MAX);
		textField.addActionListener(this);
		add(new JLabel("text"));
		remove = new JButton("remove");
		clear = new JButton("clear");
		add = new JButton("add");
		add(textField);
		add(add);
		add(remove);
		add(clear);
	}

	private void addBox(String name) {
		GCompound box = new GCompound();
		GRect outline = new GRect(BOX_WIDTH, BOX_HEIGHT);
		GLabel label = new GLabel(name);
		box.add(outline, BOX_WIDTH / 2, BOX_HEIGHT / 2);
		box.add(label, -label.getWidth() / 2, label.getAscent() / 2);
		add(box, getWidth() / 2, getHeight() / 2);
		contents.put(name, box);
	}

	private void removeBox(String name) {
		GObject obj = contents.get(name);
		if (obj != null) {
			remove(obj);
		}
	}

	private void removeContents() {
		Iterator<String> iterator = contents.keySet().iterator();
		while (iterator.hasNext()) {
			removeBox(iterator.next());
		}
		contents.clear();
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == add || source == textField) {
			addBox(textField.getText());
		} else if (source == remove) {
			removeBox(textField.getText());
		} else if (source == clear) {
			removeContents();
		}
	}

	public void mousePressed(MouseEvent e) {
		last = new GPoint(e.getPoint());
		currentObject = getElementAt(last);
	}

	public void mouseDragged(MouseEvent e) {
		if (currentObject != null) {
			currentObject.move(e.getX() - last.getX(), e.getY() - last.getY());
			last = new GPoint(e.getPoint());
		}
	}

	public void mouseClicked(MouseEvent e) {
		if (currentObject != null)
			currentObject.sendToFront();
	}

}