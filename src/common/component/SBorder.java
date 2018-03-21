package common.component;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import consts.Const;

public class SBorder {

	public static Border getTitledBorder(String title) {
		return new TitledBorder(null, title, TitledBorder.LEADING, TitledBorder.TOP, Const.tfont);
	}

	public static Border getTitledBorder() {
		return new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, Const.tfont);
	}

	public static Border getLineBorder(Color color, int thickness) {
		return new LineBorder(color, thickness);
	}

	public static Border getLineBorder() {
		return new LineBorder(Color.gray, 2);
	}
}
