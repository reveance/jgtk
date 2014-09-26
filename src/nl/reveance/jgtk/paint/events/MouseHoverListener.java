package nl.reveance.jgtk.paint.events;

import java.awt.event.MouseEvent;

import nl.reveance.jgtk.paint.components.PComponent;

/**
 * An interface to listen to hover changes from a {@link PComponent}
 * 
 * @author Reveance
 */
public interface MouseHoverListener {
	public void onHoverIn(MouseEvent e);

	public void onHoverOut(MouseEvent e);
}
