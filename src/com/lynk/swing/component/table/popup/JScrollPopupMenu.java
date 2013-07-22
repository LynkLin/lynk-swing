package com.lynk.swing.component.table.popup;

/*
 * JScrollPopupMenu.java
 *
 * Not to be used in publications, commercial or business applications
 * without permission of the copyright holder.
 *
 * Copyright (c) 2010 Warren Cross, http://crossfamily.me.uk
 */

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.event.MenuKeyListener;
import javax.swing.event.MenuKeyEvent;


/**
 * <P>JScrollPopupMenu is an extension (direct replacement) for JPopupMenu but
 * adds scrolling functionality to deal with long menu lists. The speed of
 * scrolling can be controlled along with the number of items shown.
 * <ul>
 *   <li>minimum number of menu items to keep visible is 5
 *   <li>minimum scroll speed is 10ms
 * </ul>
 * <P>(<b>note</b>, number of max items shown is defined as number of items shown between
 * top/bottom autoscrolling buttons.
 *
 * <P>Design only supports addition of JMenuItems only - for now.
 *
 * @author Warren Cross
 * @version 1.0
 */
public class JScrollPopupMenu extends JPopupMenu {
	private static final long serialVersionUID = 1L;
	
	final static int MAXITEMS = 5;
    final static int MINSPEED = 10;

    Timer timer;
    int   speed       = 50;
    int   showitems   = 10;
    JMenuItem titem   = null;
    JMenuItem bitem   = null;
    boolean scrollup  = false;
    int     scrollpos = 1;

    public JScrollPopupMenu() {
        super();
    }

    /**
     * Set the speed in 1/1000s for auto menu scrolling (min speed = 10ms)
     *
     * @param sp speed of scrolling
     */
     public void setScrollSpeed(int sp) {
         speed = sp;
         if (speed < MINSPEED) speed = MINSPEED;
     }

    /**
     * Number of menu items to show when both top/bottom scroll JMenuItems are shown
     *
     * @param maxitems number of menu items to show
     */
     public void setMaxItems(int maxitems) {
         showitems = maxitems;
         if (showitems < MAXITEMS) showitems = MAXITEMS;
     }

    /**
     * Inner timer task class  - executed when auto scrolling is active
     */
    private class timerTask extends TimerTask {
        public void run() {
            scrollMenu();
        }
    }

    /**
     * Scroll the menu one item defined by scrollup variable
     *
     * Scroll the menu by one item in the defined direction and add/remove the
     * top/bottom scroll items as needed.
     */
    private void scrollMenu() {
        int icount = getSubElements().length - 2;

        if (scrollup) {
            /**
             * Scroll the menu up
             */
            if (scrollpos < 2) {
                getComponent(0).setVisible(false);
                getComponent(1).setVisible(true);
                if (timer != null) {
                    timer.cancel();
                }
                return;
            }

            if (scrollpos > 1) {
                getComponent(scrollpos).setVisible(true);
                getComponent(scrollpos + showitems).setVisible(false);
                scrollpos--;
            }
            if (!getComponent(icount + 1).isVisible()) {
                getComponent(icount + 1).setVisible(true);
            }

        } else {
            /**
             * Scroll the menu down
             */
            if (scrollpos > 0) {
                getComponent(0).setVisible(true);
            }

            if ((icount - scrollpos) > showitems) {
                getComponent(scrollpos).setVisible(false);
                getComponent(scrollpos + showitems).setVisible(true);
                scrollpos++;
            } else {
                getComponent(icount).setVisible(true);
                getComponent(icount + 1).setVisible(false);
                if (timer != null) {
                    timer.cancel();
                }
            }
        }
    }

    /**
     * Appends new JMenuItem to Menu and adds scroll buttons if needed
     *
     * @param item JMenuItem to add
     * @returns new JMenuItem
     */
    @Override
    public JMenuItem add(JMenuItem item) {
        int icount = getSubElements().length;

        super.add(item);

        if (icount > showitems) {
            if (titem == null) {
                /**
                 * Add scroll up button along with listeners to control movement (keyboard and mouse)
                 */
            	titem = new JMenuItem("向上滚动", new ImageIcon(this.getClass().getResource("/resources/images/arrow_up.png")));
            	item.setForeground(Color.WHITE);
            	item.setBackground(Color.BLACK);
                insert(titem, 0);
                titem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        upFocusGained();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        focusLost();
                    }
                });
                titem.addMenuKeyListener(new MenuKeyListener() {
                    public void menuKeyTyped(MenuKeyEvent e) {}
                    public void menuKeyReleased(MenuKeyEvent e) {}
                    public void menuKeyPressed(MenuKeyEvent e) {
                        scrollKeyPressed(e, 1);
                    }
                });
            }
            titem.setVisible(false);
            /*
             * for long lists make sure downward button is at the bottom
             */
            if (bitem == null) {
                bitem = new JMenuItem("向下滚动", new ImageIcon(this.getClass().getResource("/resources/images/arrow_down.png")));
                item.setForeground(Color.WHITE);
            	item.setBackground(Color.BLACK);
                bitem.addMouseListener(new MouseAdapter() {

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        downFocusGained();
                    }
                    @Override
                    public void mouseExited(MouseEvent e) {
                        focusLost();
                    }
                });
                bitem.addMenuKeyListener(new MenuKeyListener() {
                    public void menuKeyTyped(MenuKeyEvent e) {}
                    public void menuKeyReleased(MenuKeyEvent e) {}
                    public void menuKeyPressed(MenuKeyEvent e) {
                        scrollKeyPressed(e, 0);
                    }
                });
                super.add(bitem);
            } else {
                remove(getComponentIndex(bitem));
                super.add(bitem);
            }
            /*
             * Hide items that are greater than our show list
             *
             */
            item.setVisible(false);
        }

        return item;
    }

    /**
     * Mouse focus received to initiate upward scroll motion
     */
    private void upFocusGained() {
        scrollup = true;
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new timerTask(), speed, speed);
    }

    /**
     * Mouse focus gained for downward scroll motion
     */
    private void downFocusGained() {
        scrollup = false;
        if (timer == null) {
            timer = new Timer();
        }
        timer.schedule(new timerTask(), speed, speed);
    }

    /**
     * Mouse focus lost to up/down scroll motion
     */
    private void focusLost() {
        timer.cancel();
        timer = null;
    }

    /**
     * Received up/Down keyboard events
     *
     * @param e keyboard event
     * @param dir keyboard events double up so only listen to direction we are going
     */
    private void scrollKeyPressed(MenuKeyEvent e, int dir) {
        /**
         * direction required as both up/down menuitems catch key events
         */
        if ((e.getKeyCode() == 40) && (dir == 0)) {
            scrollup = false;
            scrollMenu();
        }
        if ((e.getKeyCode() == 38) && (dir == 1)) {
            scrollup = true;
            scrollMenu();
        }
    }
}