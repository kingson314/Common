package common.component;

import java.awt.AWTEvent;
import java.awt.Component;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ShortcutManager implements AWTEventListener {
	public final static String[] ShortCut = new String[] { "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "F11", "F12", "ENTER", "BACK_SPACE", "TAB", "CANCEL",
			"CLEAR", "SHIFT", "CTRL", "ALT", "PAUSE", "CAPS_LOCK", "ESCAPE", "SPACE", "PAGE_UP", "PAGE_DOWN", "END", "HOME", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K",
			"L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0" };
	public final static Character[] ShortCutKey = new Character[] { KeyEvent.VK_F1, KeyEvent.VK_F2, KeyEvent.VK_F3, KeyEvent.VK_F4, KeyEvent.VK_F5, KeyEvent.VK_F6, KeyEvent.VK_F7,
			KeyEvent.VK_F8, KeyEvent.VK_F9, KeyEvent.VK_F10, KeyEvent.VK_F11, KeyEvent.VK_F12, KeyEvent.VK_ENTER, KeyEvent.VK_BACK_SPACE, KeyEvent.VK_TAB, KeyEvent.VK_CANCEL,
			KeyEvent.VK_CLEAR, KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_PAUSE, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_ESCAPE, KeyEvent.VK_SPACE,
			KeyEvent.VK_PAGE_UP, KeyEvent.VK_PAGE_DOWN, KeyEvent.VK_END, KeyEvent.VK_HOME, KeyEvent.VK_A, KeyEvent.VK_B, KeyEvent.VK_C, KeyEvent.VK_D, KeyEvent.VK_E,
			KeyEvent.VK_F, KeyEvent.VK_G, KeyEvent.VK_H, KeyEvent.VK_I, KeyEvent.VK_J, KeyEvent.VK_K, KeyEvent.VK_L, KeyEvent.VK_M, KeyEvent.VK_N, KeyEvent.VK_O, KeyEvent.VK_P,
			KeyEvent.VK_Q, KeyEvent.VK_R, KeyEvent.VK_S, KeyEvent.VK_T, KeyEvent.VK_U, KeyEvent.VK_V, KeyEvent.VK_W, KeyEvent.VK_X, KeyEvent.VK_Y, KeyEvent.VK_Z, KeyEvent.VK_1,
			KeyEvent.VK_2, KeyEvent.VK_3, KeyEvent.VK_4, KeyEvent.VK_5, KeyEvent.VK_6, KeyEvent.VK_7, KeyEvent.VK_8, KeyEvent.VK_9, KeyEvent.VK_0 };
	// 鼠标、键盘最近输入的时间，此时间用于统计本程序无操作的时间,在守护任务中可设置自动锁屏
	public static long AppInputTime = System.currentTimeMillis();

	// 使用单态模式
	private static ShortcutManager instance;

	// 快捷键与事件处理对象键值对
	private Map<String, ShortcutListener> listeners = new HashMap<String, ShortcutListener>();

	// 某组件上发生了快捷键列表中的快捷键事件, 如果他的父组件在被忽略组件列表中, 则放弃这个事件.
	private Set<Component> ignoredComponents;

	// 保存键盘上键与它的ascii码对应
	// 如果以某键的ascii码为下标, 数组中此下标的值为true, 说明此键被按下了.
	// 当此键被释放开后, 数组中对应的值修改为false
	private boolean[] keys;

	public static ShortcutManager getInstance() {
		if (instance == null)
			instance = new ShortcutManager();
		return instance;
	}

	private ShortcutManager() {
		keys = new boolean[256];
		ignoredComponents = new HashSet<Component>();
		// 键盘事件
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.KEY_EVENT_MASK);
		// 鼠标事件
		Toolkit.getDefaultToolkit().addAWTEventListener(this, AWTEvent.MOUSE_EVENT_MASK);
	}

	// 有键盘事件发生
	public void eventDispatched(AWTEvent event) {
		if (event.getClass() == KeyEvent.class) {
			// 记录键盘的输入时间
			AppInputTime = System.currentTimeMillis();
			KeyEvent keyBoardKey = (KeyEvent) event;
			if (keyBoardKey.getID() == KeyEvent.KEY_PRESSED) {
				// System.out.println("keyboard:" + keyBoardKey.getKeyCode());
				if (keys.length > keyBoardKey.getKeyCode())
					keys[keyBoardKey.getKeyCode()] = true;
				// 查找快捷键对应的处理对象, 然后调用事件处理函数
				String shortcut = searchShortcut();
				if (shortcut.indexOf("10.") >= 0) {// 过滤entry键
					shortcut = shortcut.replace("10.", "");
					// System.out.println("0:" + shortcut);
					ShortcutListener l = listeners.get(shortcut);
					if (l != null && !isIgnored(event)) {
						l.handle();
					}

				} else {
					// System.out.println("1:" + shortcut);
					ShortcutListener l = listeners.get(shortcut);
					if (l != null && !isIgnored(event)) {
						l.handle();
					}
				}
			} else if (keyBoardKey.getID() == KeyEvent.KEY_RELEASED) {
				if (keys.length > keyBoardKey.getKeyCode())
					keys[keyBoardKey.getKeyCode()] = false;
			}
		} else if (event.getClass() == MouseEvent.class) {
			// 记录鼠标的点击时间
			AppInputTime = System.currentTimeMillis();
		}
	}

	protected String searchShortcut() {
		// 每个键之间用一个"."来隔开.
		// 例如ctr + x的对应值为"17.88."
		StringBuilder shortcut = new StringBuilder();
		for (int i = 0; i < keys.length; ++i) {
			if (keys[i])
				shortcut.append(i).append(".");
		}
		return shortcut.toString();
	}

	// 查找此快捷键事件是否要被抛弃
	protected boolean isIgnored(AWTEvent event) {
		if (!(event.getSource() instanceof Component)) {
			return false;
		}

		boolean ignored = false;
		for (Component com = (Component) event.getSource(); com != null; com = com.getParent()) {
			if (ignoredComponents.contains(com)) {
				ignored = true;
				break;
			}
		}

		return ignored;
	}

	public void removeShortcutListener(ShortcutListener l) {
		String tempKey = null;
		for (Map.Entry<String, ShortcutListener> e : listeners.entrySet()) {
			if (e.getValue().equals(l)) {
				tempKey = e.getKey();
			}
		}

		listeners.remove(tempKey);
	}

	public void addShortcutListener(ShortcutListener l, int... keys) {
		// 快捷键的对应值按它们的键顺序大小来进行创建
		StringBuilder sb = new StringBuilder();
		Arrays.sort(keys);
		for (int i = 0; i < keys.length; ++i) {
			sb.append(keys[i]).append(".");
		}

		String shortcut = sb.toString();
		// 如果还不存在, 则加入
		if (listeners.containsKey(shortcut)) {
			System.out.println("The shourt cut is already used.");
		} else {
			// System.out.println(shortcut);
			listeners.put(shortcut, l);
		}
	}

	public void addIgnoredComponent(Component com) {
		ignoredComponents.add(com);
	}

	public void removeDiscardComponent(Component com) {
		ignoredComponents.remove(com);
	}

	public static interface ShortcutListener {
		void handle();
	}
	
	public static void main(String[] args) {
		ShortcutManager.getInstance().addShortcutListener(new ShortcutManager.ShortcutListener() {
			public void handle() {
				System.out.println(123);
			}
		},KeyEvent.VK_F12);
	}
}
