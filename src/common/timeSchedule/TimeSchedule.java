package common.timeSchedule;

import java.util.Timer;

/**
 * @Description: 简单的时间调度
 * @date Feb 20, 2014
 * @author:fgq
 */
public class TimeSchedule {
	private Timer timer;
	private TimeScheduleTask timerTask;
	private long delay;
	public boolean isRunning = false;

	/**
	 * 
	 * @Description:
	 * @param appName
	 *            应用模块
	 * @param timerTask
	 * @date Feb 20, 2014
	 * @author:fgq
	 */
	public TimeSchedule(TimeScheduleTask timerTask, long delay) {
		this.timerTask = timerTask;
		this.delay = delay;
	}

	public void start() {
		if (isRunning == false) {
			isRunning = true;
			this.timerTask.setRunning(true);
		}
		if (timer == null) {
			timer = new Timer();// 生成一个Timer对象
			timer.schedule(this.timerTask, 0, this.delay);// 还有其他重载方法...
		}
	}

	public void stop() {
		isRunning = false;
		this.timerTask.setRunning(false);
	}

	public static void main(String[] args) throws InterruptedException {
		class MyTimerTask extends TimeScheduleTask {// 继承TimerTask类

			int i = 0;

			public void run() {
				if (this.isRunning)
					System.out.println(i);
				i++;
			}
		}
		TimeSchedule timesShedule = new TimeSchedule(new MyTimerTask(), 1000);
		timesShedule.start();
		Thread.sleep(2000);
		timesShedule.stop();
		Thread.sleep(2000);
		// timesShedule.start();
	}
}
