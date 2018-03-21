package common.component;

import javax.swing.undo.UndoManager;

//DoManager
public class DoManager {
	private static DoManager doManager;
	private UndoManager undoManager;

	public static DoManager getInstance() {
		if (doManager == null)
			doManager = new DoManager();
		return doManager;
	}

	// 构造
	private DoManager() {
		undoManager = new UndoManager();
		undoManager.setLimit(10);
	}

	// 获取undoManager
	public UndoManager getUndoManager() {
		return undoManager;
	}

	// 重做
	public void reDo() {
		if (this.undoManager.canRedo())
			this.undoManager.redo();
	}

	// 撤销
	public void unDo() {
		if (this.undoManager.canUndo())
			this.undoManager.undo();

	}
}
