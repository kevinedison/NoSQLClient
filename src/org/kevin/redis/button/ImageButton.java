/**
 * ImageButton.java
 * kevin 2013-4-25
 * @version 0.1
 */
package org.kevin.redis.button;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

/**
 * @author kevin
 * @since jdk1.6
 */
public class ImageButton extends JButton {

    private Border emptyBorder = BorderFactory.createEmptyBorder(0, 0, 0, 0);
    private Color roverBorderColor = Color.gray;
    private Border roverBorder = new Border() {

        public void paintBorder(Component c, Graphics g, int x, int y,
                int width, int height) {
            g.setColor(roverBorderColor);
            g.drawRect(x, y, width - 1, height - 1);
        }

        public Insets getBorderInsets(Component c) {
            return new Insets(1, 1, 1, 1);
        }

        public boolean isBorderOpaque() {
            return true;
        }
    };

    /**
     * 
     */
    public ImageButton(String text) {
    }

    public ImageButton(ImageIcon icon) {
        setSize(icon.getImage().getWidth(null), icon.getImage().getHeight(null));
        setIcon(icon);
        setMargin(new Insets(0, 0, 0, 0));// 将边框外的上下左右空间设置为0
        setIconTextGap(0);// 将标签中显示的文本和图标之间的间隔量设置为0
        setBorderPainted(false);// 不打印边框
        setBorder(null);// 除去边框
        setText(null);// 除去按钮的默认名称
        setFocusPainted(false);// 除去焦点的框
        setContentAreaFilled(false);// 除去默认的背景填充

        this.setOpaque(false);
        this.setBorder(emptyBorder);
        this.setContentAreaFilled(false);
        this.setFocusPainted(false);
        this.setRolloverEnabled(true);

        this.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                if (isRolloverEnabled()) {
                    setBorder(roverBorder);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isRolloverEnabled()) {
                    setBorder(emptyBorder);
                }
            }
        });
    }

    private void setImage() {
        ImageIcon ico = new ImageIcon("images/ico.png");
        ImageButton button = new ImageButton(ico);
        // button.setPressedIcon(pressedIcon);
        // button.setRolloverIcon(rolloverIcon);
        // button.setSelectedIcon(selectedIcon);
        // button.setRolloverSelectedIcon(rolloverSelectedIcon);
        // button.setDisabledIcon(disabledIcon);
        // button.setDisabledSelectedIcon(disabledSelectedIcon);
    }
}
