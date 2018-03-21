package common.timeSchedule;


import java.util.Timer;
import java.util.TimerTask;

/**
 * @Description: 简单的时间调度
 * @date Feb 20, 2014
 * @author:fgq
 */
public class TimeSchedule1 {
	private Timer timer;
	private TimerTask timerTask;
	private long delay;

	/**
	 * 
	 * @Description:
	 * @param appName
	 *            应用模块
	 * @param timerTask
	 * @date Feb 20, 2014
	 * @author:fgq
	 */
	public TimeSchedule1(TimerTask timerTask, long delay) {
		this.timerTask = timerTask;
		this.delay = delay;
	}

	public void start() {
		if (timer == null) {
			timer = new Timer();// 生成一个Timer对象
			timer.schedule(timerTask, 0, delay);// 还有其他重载方法...
		}
	}

	public boolean isRunning() {
		if (timer == null)
			return false;
		return true;
	}

	public void stop() {
		timer.cancel();
		timer.purge();
		timer = null;
	}

	public static void main(String[] args) throws InterruptedException {

		class MyTimerTask extends TimerTask {// 继承TimerTask类
			int i = 0;

			public void run() {
				System.out.println(i);
				i++;
			}
		}
		TimeSchedule1 timesShedule = new TimeSchedule1(new MyTimerTask(), 1000);
		timesShedule.start();
		Thread.sleep(2000);
		timesShedule.stop();
		Thread.sleep(2000);
	}
}
