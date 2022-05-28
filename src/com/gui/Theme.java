package com.gui;

import java.awt.Color;

public enum Theme {
    GREEN(
            Color.getHSBColor(0.3472222f, 0.050632913f, 0.92941177f),
            Color.getHSBColor(0.34313726f, 0.17f, 0.78431374f),
            new Color(49, 160, 54),
            new Color(11, 70, 13)
    ),
    BLUE(
            new Color(232, 237, 249),
            new Color(183, 192, 216),
            new Color(123, 97, 255),
            new Color(73, 64, 121)
    ),
    BROWN(
            new Color(245, 231, 206),
            new Color(191, 158, 131),
            new Color(232, 144, 75),
            new Color(124, 93, 68)
    );
    public final Color white;
    public final Color black;
    public final Color highlight;
    public final Color hint;
    Theme(Color white, Color black, Color highlight, Color hint){
        this.white = white;
        this.black = black;
        this.highlight = highlight;
        this.hint = hint;
    }
}
