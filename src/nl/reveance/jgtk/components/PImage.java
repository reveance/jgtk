package nl.reveance.jgtk.components;

import java.awt.*;
import java.awt.image.ImageObserver;

/**
 * 
 * @author Reveance
 * 
 */
public class PImage extends PComponent {
	private Image image;
	private Dimension imageSize;
	private ImageObserver imageObserver;
	private Color backgroundColor;

	@Override
	public void draw(Graphics2D g) {
		if (this.getBounds().width == 0) {
			this.getBounds().width = getImageWidth() == 0 ? getImage().getWidth(getImageObserver()) : getImageWidth();
		}

		if (this.getBounds().height == 0) {
			this.getBounds().height = getImageHeight() == 0 ? getImage().getHeight(getImageObserver()) : getImageHeight();
		}

		if (getImageSize() == null) {
			g.drawImage(getImage(), getAbsoluteBounds().x,
					getAbsoluteBounds().y, getBackgroundColor(),
					getImageObserver());
		} else {
			g.drawImage(getImage(), getAbsoluteBounds().x,
					getAbsoluteBounds().y, getImageWidth(), getImageHeight(),
					getBackgroundColor(), getImageObserver());
		}
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public ImageObserver getImageObserver() {
		return imageObserver;
	}

	public void setImageObserver(ImageObserver imageObserver) {
		this.imageObserver = imageObserver;
	}

	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Dimension getImageSize() {
		return imageSize;
	}

	public void setImageSize(Dimension imageSize) {
		this.imageSize = imageSize;
	}

	public void setImageWidth(int width) {
		if (getImageSize() == null)
			setImageSize(new Dimension(width, 0));
		else
			getImageSize().width = width;
	}

	public void setImageHeight(int height) {
		if (getImageSize() == null)
			setImageSize(new Dimension(height, 0));
		else
			getImageSize().height = height;
	}

	public int getImageWidth() {
		if (getImageSize() == null)
			return 0;

		return getImageSize().width;
	}

	public int getImageHeight() {
		if (getImageSize() == null)
			return 0;

		return getImageSize().height;
	}
}
