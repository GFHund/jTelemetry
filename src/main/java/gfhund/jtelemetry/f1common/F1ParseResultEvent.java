/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.f1common;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;

/**
 *
 * @author PhilippHolzmann
 */
public abstract class F1ParseResultEvent {
    public abstract void resultEvent(AbstractPacket packet);
}
