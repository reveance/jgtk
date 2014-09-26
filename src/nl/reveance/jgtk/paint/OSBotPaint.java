package nl.reveance.jgtk.paint;

import java.awt.Color;
import java.awt.Graphics2D;

import org.osbot.rs07.canvas.paint.Painter;
import org.osbot.rs07.script.MethodProvider;

/**
 * This class is the starting point of every Paint. It handles the registering
 * of listeners.
 * 
 * @author Reveance
 * 
 */
public class OSBotPaint implements Painter {
	private UICanvas uiCanvas;
	private MethodProvider methodProvider;

	public OSBotPaint(UICanvas uiCanvas, MethodProvider mp) {
		this.setUICanvas(uiCanvas);
		this.setMethodProvider(mp);

		mp.getBot().addPainter(this);
		mp.getBot().addMouseListener(getUICanvas());
		mp.getBot().getCanvas().addMouseMotionListener(getUICanvas());
	}

	public UICanvas getUICanvas() {
		return uiCanvas;
	}

	public void setUICanvas(UICanvas uiCanvas) {
		this.uiCanvas = uiCanvas;
	}

	public MethodProvider getMethodProvider() {
		return methodProvider;
	}

	public void setMethodProvider(MethodProvider methodProvider) {
		this.methodProvider = methodProvider;
	}

	@Override
	public void onPaint(Graphics2D g) {
		// Reset some stuff that OSBot already changed within the Graphics2D
		// object.
		g.setColor(Color.BLACK);

		getUICanvas().paint(g);
	}
}
