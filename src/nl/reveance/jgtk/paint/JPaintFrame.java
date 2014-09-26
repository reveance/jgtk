package nl.reveance.jgtk.paint;

import javax.swing.JFrame;

public class JPaintFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5664727664736742099L;

	JPaintPanel paintPanel;

	public JPaintFrame(UICanvas canvas) {
		setTitle("JGTK Previewer");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		if (canvas != null) {
			paintPanel = new JPaintPanel(canvas);
			getContentPane().add(paintPanel);
			this.setSize(canvas.getSize());
			setLocationRelativeTo(null);
			setVisible(true);
		}
	}

	public JPaintFrame() {
		this(null);
	}

	public void setUICanvas(UICanvas canvas) {
		paintPanel = new JPaintPanel(canvas);
		getContentPane().add(paintPanel);
		this.setSize(canvas.getSize());
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
