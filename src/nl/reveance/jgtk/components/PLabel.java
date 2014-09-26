package nl.reveance.jgtk.components;

import java.awt.*;

public class PLabel extends PComponent {
	private String text;
	private Font font;
	private PLabel.Alignment alignment = Alignment.LEFT;
	private Color textColor;
	private int paddingLeft, paddingRight, paddingTop, paddingBottom;

	public enum Alignment {
		LEFT, CENTER, RIGHT
	}

	public PLabel(String text) {
		this(text, 0, 0);
	}

	@Override
	protected void draw(Graphics2D g) {
		if (getFont() != null)
			g.setFont(getFont());

		int linewidth = g.getFontMetrics().stringWidth(this.text);
		int lineheight = g.getFontMetrics().getAscent()
				+ g.getFontMetrics().getDescent();

		if (getBounds().getSize().width == 0) {
			// Calculate width based on font etc.
			getBounds().setSize(linewidth+ getPaddingLeft() + getPaddingRight(), getBounds().height);
		}

		if (getBounds().getSize().height == 0) {
			getBounds().setSize(getBounds().width, lineheight + getPaddingTop() + getPaddingBottom());
		}

		int drawX = getAbsoluteBounds().x;

		if (getAlignment() == Alignment.CENTER
				&& linewidth != getBounds().width) {
			drawX += (getBounds().width - linewidth - getPaddingLeft() - getPaddingRight()) / 2;
		} else if (getAlignment() == Alignment.RIGHT
				&& linewidth != getBounds().width) {
			drawX += (getBounds().width - linewidth);
		}

		drawX += getPaddingLeft();

		if (getTextColor() != null)
			g.setColor(getTextColor());
		g.drawString(getText(), drawX, getAbsoluteBounds().y
				+ g.getFontMetrics().getAscent() + getPaddingTop());
	}

	public PLabel(String text, int x, int y, int width, int height) {
		super(x, y, width, height);
		setText(text);
	}

	public PLabel(String text, int width, int height) {
		this(text, 0, 0, width, height);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
	}

	public PLabel.Alignment getAlignment() {
		return alignment;
	}

	public void setAlignment(PLabel.Alignment alignment) {
		this.alignment = alignment;
	}

	public Color getTextColor() {
		return textColor;
	}

	public void setTextColor(Color textColor) {
		this.textColor = textColor;
	}

	public int getPaddingTop() {
		return paddingTop;
	}

	public void setPaddingTop(int paddingTop) {
		this.paddingTop = paddingTop;
	}

	public int getPaddingLeft() {
		return paddingLeft;
	}

	public void setPaddingLeft(int paddingLeft) {
		this.paddingLeft = paddingLeft;
	}

	public int getPaddingRight() {
		return paddingRight;
	}

	public void setPaddingRight(int paddingRight) {
		this.paddingRight = paddingRight;
	}

	public int getPaddingBottom() {
		return paddingBottom;
	}

	public void setPaddingBottom(int paddingBottom) {
		this.paddingBottom = paddingBottom;
	}

	public void setPadding(int paddingLeft, int paddingTop, int paddingRight,
			int paddingBottom) {
		setPaddingLeft(paddingLeft);
		setPaddingTop(paddingTop);
		setPaddingRight(paddingRight);
		setPaddingBottom(paddingBottom);
	}

	public void setPadding(int paddingLeftRight, int paddingTopBottom) {
		setPaddingLeft(paddingLeftRight);
		setPaddingTop(paddingTopBottom);
		setPaddingRight(paddingLeftRight);
		setPaddingBottom(paddingTopBottom);
	}

	public void setPadding(int paddingAll) {
		setPaddingLeft(paddingAll);
		setPaddingTop(paddingAll);
		setPaddingRight(paddingAll);
		setPaddingBottom(paddingAll);
	}
}
