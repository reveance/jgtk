package nl.reveance.testscript;

import nl.reveance.jgtk.paint.OSBotPaint;
import nl.reveance.test.CustomPaint;

import org.osbot.rs07.script.Script;
import org.osbot.rs07.script.ScriptManifest;

@ScriptManifest(author = "Reveance", info = "Paint lib test", name = "Painter", version = 1, logo = "")
public class TestScript extends Script {

	public TestScript() {
		super();
	}

	@Override
	public void onStart() throws InterruptedException {
		// TODO Auto-generated method stub
		super.onStart();
		log("Initializing paint");
		CustomPaint customPaint = new CustomPaint(this.getBot().getCanvas());
		new OSBotPaint(customPaint, this);
	}

	@Override
	public int onLoop() throws InterruptedException {
		// TODO Auto-generated method stub
		return 0;
	}

}
