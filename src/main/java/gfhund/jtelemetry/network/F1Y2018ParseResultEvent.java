/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gfhund.jtelemetry.network;

import gfhund.jtelemetry.commontelemetry.AbstractPacket;
/**
 *
 * @author PhilippGL
 */
public abstract class F1Y2018ParseResultEvent {
    public abstract void resultEvent(AbstractPacket packet);
}
