package com.dopo.game;

import com.dopo.game.presentation.views.MainMenuView;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) {}
            new MainMenuView().setVisible(true);
        });
    }
}