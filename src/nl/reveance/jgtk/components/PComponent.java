package nl.reveance.jgtk.components;

import nl.reveance.jgtk.events.MouseHoverListener;
import nl.reveance.jgtk.rendering.DrawParameterRestorer;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.List;

/**
 * The abstract base class of every component.
 * 
 * @author Reveance
 */
public abstract class PComponent {
	// Some layout variables
	private PContainer parent;
	private Rectangle bounds;
	private boolean visible;

	// Listeners and such
	private List<MouseListener> mouseListeners;
	private List<MouseHoverListener> hoverListeners;
	private List<MouseMotionListener> mouseMotionListeners;

	// Draw params
	private BasicStroke border;
	private Color borderColor;
	private Color backgroundColor;
	private Image backgroundImage;
	private ImageObserver backgroundImageObserver;
	private Dimension backgroundImageSize;

	private boolean draggable;
	private PComponent dragTarget;
	protected DragHelper dragHelper;

	// States of the class
	private boolean hovered;

	// The DrawParameterRestorer is made static so that you can
	// easily override it and let components use another one
	private static Class<DrawParameterRestorer> drawParameterRestorer = DrawParameterRestorer.class;

	public PComponent() {
		this(0, 0, 0, 0);
	}

	public PComponent(int width, int height) {
		this(0, 0, width, height);
	}

	public PComponent(int x, int y, int width, int height) {
		mouseListeners = new ArrayList<MouseListener>();
		hoverListeners = new ArrayList<MouseHoverListener>();
		mouseMotionListeners = new ArrayList<MouseMotionListener>();
		this.dragHelper = new DragHelper();

		this.setBounds(x, y, width, height);
		this.setVisible(true);
	}

	/**
	 * Paint the current component. This wrapper method checks if the component
	 * should be painted (e.g. is not hidden) and uses a
	 * {@link DrawParameterRestorer} to reset the {@link Graphics2D} objects
	 * properties after the drawing of the generic component things (such as a
	 * border) and actual element-specific drawing by the protected
	 * {@link PComponent#draw(Graphics2D)} method is done.
	 * 
	 * @param g
	 *            {@link Graphics2D} The shared graphics context to paint the
	 *            component in.
	 */
	public void paint(Graphics2D g) {
		if (this.isVisible()) {
			DrawParameterRestorer dpr = null;
			try {
				dpr = getDrawParameterRestorer().newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}

			if (dpr == null)
				dpr = new DrawParameterRestorer();


			if(getParent() != null) {
				g.setClip(getParent().getAbsoluteBounds());
			}
			
			dpr.grab(g);

			if (this.getBorder() != null) {
				if (getBorderColor() != null)
					g.setColor(getBorderColor());

				g.setStroke(this.getBorder());

				Rectangle drawBounds = getAbsoluteBounds();
				float lw = getBorder().getLineWidth();
				drawBounds.setSize(drawBounds.width + (int) lw,
						drawBounds.height + (int) lw);
				drawBounds.setLocation(
						drawBounds.x - (int) Math.ceil((double) lw / 2),
						drawBounds.y - (int) Math.ceil((double) lw / 2));
				g.draw(drawBounds);
			}

			if (this.getBackgroundColor() != null) {
				g.setColor(getBackgroundColor());
				g.fill(getAbsoluteBounds());
			}
						
			if (this.getBackgroundImage() != null) {
				if (getBackgroundImageSize() == null) {
					g.drawImage(getBackgroundImage(), getAbsoluteBounds().x,
							getAbsoluteBounds().y, getBackgroundColor(),
							getBackgroundImageObserver());
				} else {
					g.drawImage(getBackgroundImage(), getAbsoluteBounds().x,
							getAbsoluteBounds().y,
							getBackgroundImageSize().width,
							getBackgroundImageSize().height,
							getBackgroundColor(), getBackgroundImageObserver());
				}
			}
			
			dpr.restore(g);

			this.draw(g);

			// Restore for next element
			dpr.restore(g);
		}
	}

	/**
	 * Draws the current component based on all defined properties.
	 * 
	 * @param g
	 *            {@link Graphics2D} The shared graphics object
	 */
	protected abstract void draw(Graphics2D g);

	/**
	 * This method processes a {@link MouseEvent}. It looks if the positions of
	 * the mouse are within the bounds of the current component and then calls
	 * the relevant methods of the listeners. If an event is of type
	 * MOUSE_MOVED, MOUSE_DRAGGED or MOUSE_RELEASED it will always be called,
	 * even if it is outside the bounds of the current element
	 * <p>
	 * Note that this means that it only processes the event for itself and does
	 * not dispatch the event to any children in the case of a
	 * {@link PContainer} component.
	 * 
	 * @param e
	 *            The {@link MouseEvent} to be processed
	 */
	public boolean handleMouseEvent(MouseEvent e) {
		Point clicked = e.getPoint();

		if (getAbsoluteBounds().contains(clicked)
				|| e.getID() == MouseEvent.MOUSE_MOVED
				|| e.getID() == MouseEvent.MOUSE_DRAGGED
				|| e.getID() == MouseEvent.MOUSE_RELEASED) {
			switch (e.getID()) {
			case MouseEvent.MOUSE_CLICKED:
				for (MouseListener listener : this.mouseListeners)
					listener.mouseClicked(e);

				break;
			case MouseEvent.MOUSE_MOVED:
				if (getAbsoluteBounds().contains(clicked)) {
					if (!this.hovered) {
						this.hovered = true;
						for (MouseHoverListener listener : this.hoverListeners)
							listener.onHoverIn(e);
					}
				} else {
					if (this.hovered) {
						this.hovered = false;
						for (MouseHoverListener listener : this.hoverListeners)
							listener.onHoverOut(e);
					}
				}

				for (MouseMotionListener listener : this.mouseMotionListeners)
					listener.mouseMoved(e);
				break;
			case Event.MOUSE_DRAG:
				for (MouseMotionListener listener : this.mouseMotionListeners)
					listener.mouseDragged(e);
				break;
			case Event.MOUSE_ENTER:
				for (MouseListener listener : this.mouseListeners)
					listener.mouseEntered(e);
				break;
			case Event.MOUSE_EXIT:
				for (MouseListener listener : this.mouseListeners)
					listener.mouseExited(e);
				break;
			case Event.MOUSE_DOWN:
				for (MouseListener listener : this.mouseListeners)
					listener.mousePressed(e);
				break;
			case Event.MOUSE_UP:
				for (MouseListener listener : this.mouseListeners)
					listener.mouseReleased(e);
				break;
			}

			return true;
		}

		return false;
	}

	public void addMouseListener(MouseListener ml) {
		this.mouseListeners.add(ml);
	}

	public void removeMouseListener(MouseListener ml) {
		this.mouseListeners.remove(this.mouseListeners.indexOf(ml));
	}

	public void addHoverListener(MouseHoverListener hl) {
		this.hoverListeners.add(hl);
	}

	public void removeHoverListener(MouseHoverListener hl) {
		this.hoverListeners.remove(this.hoverListeners.indexOf(hl));
	}

	public void addMouseMotionListener(MouseMotionListener mml) {
		this.mouseMotionListeners.add(mml);
	}

	public void removeMouseMotionListener(MouseMotionListener mml) {
		this.mouseMotionListeners
				.remove(this.mouseMotionListeners.indexOf(mml));
	}

	public List<MouseListener> getMouseListeners() {
		return mouseListeners;
	}

	public void setMouseListeners(List<MouseListener> mouseListeners) {
		this.mouseListeners = mouseListeners;
	}

	public List<MouseHoverListener> getHoverListeners() {
		return hoverListeners;
	}

	public void setHoverListeners(List<MouseHoverListener> hoverListeners) {
		this.hoverListeners = hoverListeners;
	}

	public List<MouseMotionListener> getMouseMotionListeners() {
		return mouseMotionListeners;
	}

	public void setMouseMotionListeners(
			List<MouseMotionListener> mouseMotionListeners) {
		this.mouseMotionListeners = mouseMotionListeners;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Dimension getSize() {
		if (getBounds() != null)
			return getBounds().getSize();

		return null;
	}

	public int getWidth() {
		if (getSize() != null)
			return getSize().width;

		return 0;
	}

	public int getHeight() {
		if (getSize() != null)
			return getSize().height;

		return 0;
	}

	public Point getLocation() {
		if (getBounds() != null)
			return getBounds().getLocation();

		return null;
	}

	public int getX() {
		if (getLocation() != null)
			return getLocation().x;

		return 0;
	}

	public int getY() {
		if (getLocation() != null)
			return getLocation().y;

		return 0;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public void setBounds(int x, int y, int width, int height) {
		this.setBounds(new Rectangle(x, y, width, height));
	}

	public void setLocation(int x, int y) {
		if (this.getBounds() != null)
			this.getBounds().setLocation(x, y);
		else
			this.setBounds(x, y, 0, 0);
	}

	public void setLocation(Point p) {
		this.setLocation(p.x, p.y);
	}

	public void setSize(int width, int height) {
		if (this.getBounds() != null)
			this.getBounds().setSize(width, height);
		else
			this.setBounds(0, 0, width, height);
	}

	public void setSize(Dimension size) {
		this.setSize(size.width, size.height);
	}

	public Rectangle getAbsoluteBounds() {
		Rectangle current = new Rectangle(this.getBounds());

		PComponent parent = this;

		while ((parent = parent.getParent()) != null) {
			current.setLocation(
					(int) (parent.getBounds().getX() + current.getX()),
					(int) (parent.getBounds().getY() + current.getY()));
		}

		return current;
	}

	public BasicStroke getBorder() {
		return border;
	}

	public void setBorder(BasicStroke border) {
		this.border = border;
	}

	public void setBorder(int width) {
		this.border = new BasicStroke(width);
	}

	public void setBorder(float width, int cap, int join) {
		this.border = new BasicStroke(width, cap, join);
	}

	public void setBorder(float width, int cap, int join, float miterlimit) {
		this.border = new BasicStroke(width, cap, join, miterlimit);
	}

	public void setBorder(float width, int cap, int join, float miterlimit,
			float[] dash, float dash_phase) {
		this.border = new BasicStroke(width, cap, join, miterlimit, dash,
				dash_phase);
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public boolean isHovered() {
		return hovered;
	}

	public PContainer getParent() {
		return parent;
	}

	public void setParent(PContainer parent) {
		this.parent = parent;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public static Class<DrawParameterRestorer> getDrawParameterRestorer() {
		return drawParameterRestorer;
	}

	/**
	 * Set a custom {@link DrawParameterRestorer}. Make sure that it has either
	 * no constructor or one without any arguments, so it can be instantiated
	 * with {@link Class#newInstance()}.
	 * 
	 * @param drawParameterRestorer
	 *            The {@link DrawParameterRestorer} to be used for clearing
	 *            {@link Graphics2D} arguments in between draws of components.
	 */
	public static void setDrawParameterRestorer(
			Class<DrawParameterRestorer> drawParameterRestorer) {
		PComponent.drawParameterRestorer = drawParameterRestorer;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public boolean isDraggable() {
		return draggable;
	}

	public void setDraggable(boolean draggable) {
		this.draggable = draggable;
		if (draggable) {
			this.addMouseListener(this.dragHelper);
			this.addMouseMotionListener(this.dragHelper);
		} else {
			this.removeMouseListener(this.dragHelper);
			this.removeMouseMotionListener(this.dragHelper);
		}
	}

	public PComponent getDragTarget() {
		return dragTarget;
	}

	public void setDragTarget(PComponent dragTarget) {
		this.dragTarget = dragTarget;
	}

	public Image getBackgroundImage() {
		return backgroundImage;
	}

	public void setBackgroundImage(Image backgroundImage) {
		this.backgroundImage = backgroundImage;
	}

	public ImageObserver getBackgroundImageObserver() {
		return backgroundImageObserver;
	}

	public void setBackgroundImageObserver(ImageObserver backgroundImageObserver) {
		this.backgroundImageObserver = backgroundImageObserver;
	}

	public Dimension getBackgroundImageSize() {
		return backgroundImageSize;
	}

	public void setBackgroundImageSize(Dimension backgroundImageSize) {
		this.backgroundImageSize = backgroundImageSize;
	}

	private class DragHelper implements MouseListener, MouseMotionListener {

		private boolean dragging = false;
		private int curX = 0;
		private int curY = 0;

		@Override
		public void mouseDragged(MouseEvent e) {
			if (!dragging)
				return;

			PComponent target = PComponent.this.dragTarget != null ? PComponent.this.dragTarget
					: PComponent.this;

			Rectangle bounds = target.getBounds();

			bounds.setLocation(bounds.x + (e.getX() - curX),
					bounds.y + (e.getY() - curY));

			curX = e.getX();
			curY = e.getY();
		}

		@Override
		public void mouseMoved(MouseEvent e) {}

		@Override
		public void mouseClicked(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

		@Override
		public void mousePressed(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				dragging = true;
				curX = e.getX();
				curY = e.getY();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (e.getButton() == MouseEvent.BUTTON1) {
				dragging = false;
			}
		}

	}
}
