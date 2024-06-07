package ru.hse.edu.stalivanov.fireplugin.funcblocks;

import java.io.Serial;
import java.io.Serializable;

public enum ObjectTypes implements Serializable
{
    smokeDetector,
    alarm,
    stickyPiston;

    @Serial
    private static final long serialVersionUID = 1;
}
