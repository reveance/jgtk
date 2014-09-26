package nl.reveance.jgtk.paint;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import nl.reveance.jgtk.paint.components.PContainer;

import org.osbot.rs07.canvas.paint.Painter;

/**
 * This class is the starting point of every UI. All components that are
 * supposed to be drawn should be added to this class.
 * 
 * @author Reveance
 * 
 */
public class UICanvas extends PContainer implements Painter, MouseListener,
		MouseMotionListener {
	private RenderingHints renderingHints;

	/**
	 * Creates a new UI based on the supplied parameters.
	 * 
	 * @param x
	 *            The x position on the specified canvas where this paint should
	 *            be painted on
	 * @param y
	 *            The y position on the specified canvas where this paint should
	 *            be painted on
	 * @param width
	 *            The width of this canvas
	 * @param height
	 *            The height of this canvas
	 */
	public UICanvas(int x, int y, int width, int height) {
		super(x, y, width, height);
	}

	/**
	 * @param width
	 *            The width of this canvas
	 * @param height
	 *            The height of this canvas
	 */
	public UICanvas(int width, int height) {
		this(0, 0, width, height);
	}

	public UICanvas() {
		this(0, 0, 0, 0);
	}

	@Override
	public void onPaint(Graphics2D g) {
		if (renderingHints != null)
			g.setRenderingHints(renderingHints);

		// Set default color so we're sure nothing interferes
		g.setColor(Color.BLACK);

		super.paint(g);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		handleMouseEvent(e);
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		handleMouseEvent(e);
	}

	public RenderingHints getRenderingHints() {
		return renderingHints;
	}

	public void setRenderingHints(RenderingHints renderingHints) {
		this.renderingHints = renderingHints;
	}
}
