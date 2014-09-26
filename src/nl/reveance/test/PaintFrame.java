package nl.reveance.test;

import nl.reveance.jgtk.paint.JPaintFrame;

public class PaintFrame {

	public static void main(String[] args) {
		final JPaintFrame pf = new JPaintFrame();
		CustomPaint canvas = new CustomPaint(pf);
		pf.setUICanvas(canvas);
	}
}
