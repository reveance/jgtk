package nl.reveance.jgtk;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * A JPanel which wraps a PaintCanvas. This way you can add a (custom)
 * PaintCanvas to a JPanel. This class takes care of registering the necessary
 * listeners.
 * <p>
 * Note that a JPanel normally only repaints itself if its state changes, this
 * uses a timer to repaint every 20ms.
 * 
 * @author Reveance
 * 
 */
public class JPaintPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7738454185614554315L;
	private UICanvas uiCanvas;
	private Timer timer;

	public JPaintPanel(UICanvas ui) {
		setUICanvas(ui);
		addMouseListener(ui);
		addMouseMotionListener(ui);
		setTimer(new Timer(16, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JPaintPanel.this.repaint();
			}
		}));
		getTimer().start();
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		uiCanvas.onPaint((Graphics2D) g);
	}

	public UICanvas getUICanvas() {
		return uiCanvas;
	}

	public void setUICanvas(UICanvas uiCanvas) {
		this.uiCanvas = uiCanvas;
	}

	public Timer getTimer() {
		return timer;
	}

	public void setTimer(Timer timer) {
		this.timer = timer;
	}

}
