package common.component;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1342953482999226708L;
	private int width;
	private int height;
	private String imagePath;

	public ImagePanel(int width, int height, String imagePath) {
		super();
		this.setLayout(null);
		this.width = width;
		this.height = height;
		this.imagePath = imagePath;
	}

	@Override
	protected void paintComponent(Graphics g) {
		try {
			BufferedImage img = null;
			File imageFile = new File(this.imagePath);
			if (imageFile.exists()) {
				img = ImageIO.read(imageFile);
			}
			g.drawImage(img, 0, 0, width, height, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
