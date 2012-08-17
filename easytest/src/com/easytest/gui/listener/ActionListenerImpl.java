/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.easytest.gui.listener;

import java.awt.event.ActionListener;
import javax.swing.JFrame;

/**
 *
 * @author gilberto
 */
public abstract class ActionListenerImpl implements ActionListener {

    protected abstract JFrame getFrame();

    /**
     Centraliza o JFrame
     */
    protected final void centralizeFrame(JFrame child) {
        child.setBounds(getFrame().getX() + (getFrame().getWidth() / 2) - child.getWidth() / 2,
                getFrame().getY() + (getFrame().getHeight() / 2) - child.getHeight() / 2, child.getWidth(), child.getHeight());

    }
}
