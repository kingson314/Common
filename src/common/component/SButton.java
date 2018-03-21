package common.component;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import consts.Const;


public class SButton extends JButton {
	private static final long serialVersionUID = -49062483358425318L;

	public SButton(String text, String imagePath) {
		super(text);
		this.setIcon(new ImageIcon(imagePath));
		this.setFont(Const.tfont);
		this.setOpaque(false);
		// this.setFocusable(true);
		// this.setBorderPainted(false);
	}

	public SButton(String text) {
		super(text);
		// 没图标
		this.setFont(Const.tfontBtn);
		this.setOpaque(false);
		// this.setFocusable(true);
		// this.setBorderPainted(false);
	}

	// @Override
	// protected void paintComponent(Graphics g) {
	// BufferedImage img;
	// try {
	// img = ImageIO.read(new File(this.imageIcon));
	// g.drawImage(img, 0, 0, null);
	// } catch (IOException e) {
	// e.printStackTrace();
	// }
	// }
}
