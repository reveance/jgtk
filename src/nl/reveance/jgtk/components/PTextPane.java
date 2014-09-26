package nl.reveance.jgtk.components;

import java.awt.FontMetrics;
import java.awt.Graphics2D;

public class PTextPane extends PLabel {

	public PTextPane(String text, int x, int y, int width, int height) {
		super(text, x, y, width, height);
	}

	public PTextPane(String text, int width, int height) {
		super(text, width, height);
	}

	public PTextPane(String text) {
		super(text);
	}

	@Override
	protected void draw(Graphics2D g) {
		if (getFont() != null)
			g.setFont(getFont());

		if (getTextColor() != null)
			g.setColor(getTextColor());

		String[] paragraphs = getText().split("\n");
		int curHeight = getPaddingTop();
		FontMetrics fm = g.getFontMetrics();

		for (String paragraph : paragraphs) {
			int lineheight = g.getFontMetrics().getAscent()
					+ g.getFontMetrics().getDescent();

			String[] words = paragraph.split(" ");

			String line = "";
			int wordIndex = 0;
			for (String word : words) {
				line += word + " ";
				String nextWord = wordIndex + 1 == words.length ? ""
						: words[wordIndex + 1] + " ";
				wordIndex++;

				if (fm.stringWidth(line) + fm.stringWidth(nextWord)
						+ getPaddingLeft() + getPaddingRight() < getBounds().getWidth()
						&& wordIndex != words.length) {
					continue;
				}

				int linewidth = fm.stringWidth(line);

				int drawX = getAbsoluteBounds().x;

				if (getAlignment() == Alignment.CENTER
						&& linewidth != getBounds().width) {
					drawX += (getBounds().width - linewidth - getPaddingLeft() - getPaddingRight()) / 2;
				} else if (getAlignment() == Alignment.RIGHT
						&& linewidth != getBounds().width) {
					drawX += (getBounds().width - linewidth);
				}

				drawX += getPaddingLeft();

				g.drawString(line, drawX, getAbsoluteBounds().y
						+ g.getFontMetrics().getAscent() + curHeight);
				curHeight += lineheight;

				line = "";
			}
		}

		if (getHeight() == 0)
			getBounds().setSize(getWidth(), curHeight + getPaddingBottom());
	}
}
