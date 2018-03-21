package common.timeSchedule;

import java.util.TimerTask;

public class TimeScheduleTask extends TimerTask {
	protected boolean isRunning = true;

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}

	@Override
	public void run() {
		if (this.isRunning) {

		}
	}

}
