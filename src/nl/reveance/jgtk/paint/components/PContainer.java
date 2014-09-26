package nl.reveance.jgtk.paint.components;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import nl.reveance.jgtk.paint.exception.HierarchyException;

public class PContainer extends PComponent {
	List<PComponent> components;

	public PContainer() {
		this(0, 0);
	}

	public PContainer(int width, int height) {
		this(0, 0, width, height);
	}

	public PContainer(int x, int y, int width, int height) {
		super(x, y, width, height);
		components = new ArrayList<PComponent>();
	}

	/**
	 * Let all the components draw themselves.
	 */
	@Override
	public void draw(Graphics2D g) {
		// Take over size from parent if necessary
		if (getBounds().width == 0)
			getBounds().setSize(
					getParent() != null ? getParent().getWidth() : 0,
					getBounds().height);

		if (getBounds().height == 0)
			getBounds().setSize(getBounds().width,
					getParent() != null ? getParent().getHeight() : 0);

		// Let the components paint theirselves
		for (PComponent comp : components) {
			comp.paint(g);
		}
	}

	/**
	 * This method processes a {@link MouseEvent}. The event is dispatched to
	 * all elements present in this container. After that is done
	 * {@link PComponent#handleMouseEvent(MouseEvent)} is triggered on the
	 * current element.
	 */
	@Override
	public boolean handleMouseEvent(MouseEvent e) {
		// Done in reverse order because the components are being painted in
		// normal order, meaning the last is on top. The one on top should
		// receive events earlier.
		for (int i = components.size() - 1; i >= 0; i--) {
			if (components.get(i).handleMouseEvent(e)
					&& e.getID() != MouseEvent.MOUSE_RELEASED
					&& e.getID() != MouseEvent.MOUSE_MOVED
					&& e.getID() != MouseEvent.MOUSE_DRAGGED)
				return true;
		}

		return super.handleMouseEvent(e);
	}

	/**
	 * Add a {@link PComponent} to the container, which is automatically
	 * assigned the current instance as a parent.
	 * 
	 * @param component
	 *            {@link PComponent} The component to be added to current
	 *            container
	 */
	public void add(PComponent component) {
		try {
			if (component.getParent() != null)
				throw new HierarchyException("Component already has a parent");

			if (this.components.add(component)) {
				component.setParent(this);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Remove a {@link PComponent} from this container after which the current
	 * instance is removed as parent for the component.
	 * 
	 * @param component
	 *            {@link PComponent} The component to be removed
	 */
	public void removeComponent(PComponent component) {
		if (this.components.remove(this.components.indexOf(component)) != null) {
			component.setParent(null);
		}
	}

	public PComponent getComponentAt(int index) {
		return this.components.get(index);
	}
}
