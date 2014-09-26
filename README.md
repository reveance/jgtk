Java Graphics2D Toolkit
====

A library to create user interfaces with components in a Graphics2D context.

An example of how to create an interface:
```java
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;

import nl.reveance.jgtk.paint.UICanvas;
import nl.reveance.jgtk.paint.components.PContainer;
import nl.reveance.jgtk.paint.components.PImage;
import nl.reveance.jgtk.paint.components.PLabel;
import nl.reveance.jgtk.paint.components.PLabel.Alignment;
import nl.reveance.jgtk.paint.components.PTextPane;
import nl.reveance.jgtk.paint.events.MouseHoverListener;

public class CustomPaint extends UICanvas {

	public CustomPaint(final Component reference) {
		super();
		setSize(1024, 768);

		try {
			// Create a container for a part of the paint
			PContainer container = new PContainer(500, 200);

			// Set the location to (50, 50)
			container.setLocation(50, 50);

			// Put a background image on the container, however note that if you
			// are doing this in an actionlistener, make sure you load the
			// images somewhere in the class before you use them, to avoid
			// blocking the ui.
			container
					.setBackgroundImage(ImageIO
							.read(new URL(
									"http://www.walkontile.com/wp-content/uploads/2011/02/metal-tiles-th.jpg")));

			// Create a border with 1 width and assign it a dark gray color
			container.setBorder(1);
			container.setBorderColor(Color.DARK_GRAY);

			// Create an image component
			PImage pimage = new PImage();

			// Make the image listen to drag events.
			pimage.setDraggable(true);

			// Set the drag target to the container. This means that the drag
			// events are going to influence the container (and thus all
			// components inside it) instead of only this element itself.
			pimage.setDragTarget(container);

			// Set the location to 5, 5. This is relative to the parent, which
			// is going to be the container
			pimage.setLocation(5, 5);

			// Load the image. If you are going to dynamically do this in
			// listeners, make sure you have loaded this beforehand.
			pimage.setImage(ImageIO
					.read(new URL(
							"http://findicons.com/files/icons/2338/reflection/128/cursor_move.png")));

			// The image is originally 128x128, but resize it to 32x32
			pimage.setImageSize(new Dimension(32, 32));

			// Add the image to the container
			container.add(pimage);

			// Create a text pane with some text on location (80, 30) with size
			// (300, 0). If height is set to 0 for a textpane, it will resize
			// itself based on the amount of text
			PTextPane textpane = new PTextPane("Strength xp: 10000\n"
					+ "Time ran: 4:30:36\n" + "Strength xp / h: 3000\n"
					+ "Blabla: 100k\n" + "Something else: 14914", 80, 30, 300,
					0);

			// I guess the rest here speaks for itself
			textpane.setBorder(1);
			textpane.setAlignment(Alignment.CENTER);
			textpane.setBackgroundColor(Color.LIGHT_GRAY);
			textpane.setTextColor(Color.DARK_GRAY);
			textpane.setPadding(20, 20, 20, 20);

			// Add the textpane to the container aswell
			container.add(textpane);

			// Finally, add the container to the paint
			this.add(container);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// This shows how you can make a button using a label, but the same
		// principle would apply when using a PImage to do so. When labels are
		// given any size 0, it will automatically size itselves based on the
		// content.
		final PLabel btn = new PLabel("Fancy button", 50, 300, 0, 0);

		// Give a new font
		btn.setFont(new Font("Verdana", Font.BOLD, 18));

		// Speaks for itself...
		btn.setBorder(1);
		btn.setPadding(10);
		btn.setAlignment(Alignment.CENTER);
		btn.setBackgroundColor(Color.LIGHT_GRAY);
		btn.setTextColor(Color.DARK_GRAY);

		// We add a HoverListener which is called when you hover in or out of
		// the element, to create some cool looking things. Maybe support for
		// this will be implemented in a better way soon, so that you don't have
		// to large listener classes just to change a color on hover
		btn.addHoverListener(new MouseHoverListener() {

			@Override
			public void onHoverIn(MouseEvent e) {
				btn.setBackgroundColor(Color.GRAY);
				btn.setTextColor(Color.WHITE);
				// Set cursor to HAND_CURSOR when hovering in. This is why we
				// needed the reference to a component in the constructor. If
				// you don't want any of such functionality you can leave the
				// constructor empty.
				reference.setCursor(Cursor
						.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void onHoverOut(MouseEvent e) {
				btn.setBackgroundColor(Color.LIGHT_GRAY);
				btn.setTextColor(Color.DARK_GRAY);

				// Set the cursor to default when hovering out.
				reference.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// We add a mouse listener to create an effect on mousePressed, and we
		// reset everything in mouseReleased
		btn.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// Check if the mouse is released inside or outside the absolute
				// bounds of the button. Note that you MUST use absolute bounds
				// to check this, as the normal bounds are relative to the
				// parent, where the MouseEvent is relative to the container
				// everything is painted in.
				if (btn.getAbsoluteBounds().contains(arg0.getPoint())) {
					btn.setBackgroundColor(Color.GRAY);
					btn.setTextColor(Color.WHITE);

					// Set cursor to HAND_CURSOR
					reference.setCursor(Cursor
							.getPredefinedCursor(Cursor.HAND_CURSOR));
				} else {
					btn.setBackgroundColor(Color.LIGHT_GRAY);
					btn.setTextColor(Color.DARK_GRAY);

					// Set cursor to DEFAULT
					reference.setCursor(Cursor
							.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				}

			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				btn.setBackgroundColor(Color.BLACK);
			}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseClicked(MouseEvent arg0) {}
		});

		// Add the button to the ui
		add(btn);
	}
}
```

You can preview your paint easily by creating the following main method somewhere:
```java
public static void main(String[] args) {
	final JPaintFrame paintFrame = new JPaintFrame();
	
	// Create an instance of our CustomPaint
	CustomPaint canvas = new CustomPaint(paintFrame);
	
	// Add it to the JPaintFrame
	pf.setUICanvas(canvas);
}
```
