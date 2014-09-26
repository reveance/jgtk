package nl.reveance.jgtk.rendering;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;

import nl.reveance.jgtk.components.PComponent;

/**
 * This class grabs desired properties from a {@link Graphics2D} object which
 * can be used to restore the previous state.
 * <p>
 * Because of that, {@link PComponent} instances don't interfere with each other
 * when they're painted with different properties, because those properties are
 * restored by this class after each instance is painted.
 * <p>
 * Note that because this class is instantiated using
 * {@link Class#newInstance()} in {@link PComponent#paint(Graphics2D)} it must
 * contain a constructor without arguments.
 * 
 * @author Reveance
 * 
 */
public class DrawParameterRestorer {
	private Stroke stroke;
	private Color color, bg;
	private AffineTransform transform;
	private Shape clip;
	private RenderingHints rh;
	private Font font;

	/**
	 * Obtains all the parameters that are to be restored from the
	 * {@link Graphics2D} object so it can be restored later.
	 * 
	 * @param g
	 *            The {@link Graphics2D} to get the parameters from
	 */
	public void grab(Graphics2D g) {
		stroke = g.getStroke();
		color = g.getColor();
		bg = g.getBackground();
		transform = g.getTransform();
		clip = g.getClip();
		rh = g.getRenderingHints();
		font = g.getFont();
	}

	/**
	 * Restore all the parameters that the {@link Graphics2D} object had when
	 * initialized.
	 * 
	 * @param g
	 *            The {@link Graphics2D} to restore the parameters in
	 */
	public void restore(Graphics2D g) {
		g.setStroke(stroke);
		g.setColor(color);
		g.setBackground(bg);
		g.setTransform(transform);
		g.setClip(clip);
		g.setRenderingHints(rh);
		g.setFont(font);
	}
}