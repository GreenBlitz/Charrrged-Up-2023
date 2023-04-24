package edu.greenblitz.utils;

import edu.greenblitz.tobyDetermined.RobotMap;
import edu.wpi.first.wpilibj.util.Color;

public class LEDSequence {

    private Color color;
    private double duration;
    private double waitBeforeStartTime;

    private RobotMap.LED.Sections section;
    private LEDSequence next;

    public LEDSequence(Color color, double duration, double waitBeforeStartTime, RobotMap.LED.Sections section, LEDSequence next) {
        this.color = color;
        this.duration = duration;
        this.waitBeforeStartTime = waitBeforeStartTime;
        this.section = section;
        this.next = next;
    }

    public LEDSequence(Color color) {
        this.color = color;
        this.duration = 0;
        this.waitBeforeStartTime = 0;
        this.section = RobotMap.LED.Sections.all;
        this.next = null;
    }

    //with:

    public LEDSequence withDuration (double duration){
        this.duration = duration;
        return this;
    }
    public LEDSequence withBeforeStartTime (double time){
        this.duration = time;
        return this;
    }
    public LEDSequence withNext (LEDSequence next){
        this.next = next;
        return this;
    }


    public LEDSequence setSection (RobotMap.LED.Sections section){
        this.section = section;
        return this;
    }
    public LEDSequence setColor (Color color){
        this.color = color;
        return this;
    }
    public LEDSequence setDuration (double duration){
        this.duration = duration;
        return this;
    }
    public LEDSequence setBeforeStartTime (double time){
        this.waitBeforeStartTime = time;
        return this;
    }


    public Color getColor() {
        return color;
    }

    public double getDuration() {
        return duration;
    }

    public double getWaitBeforeStartTime() {
        return waitBeforeStartTime;
    }

    public void setWaitBeforeStartTime(double waitBeforeStartTime) {
        this.waitBeforeStartTime = waitBeforeStartTime;
    }

    public RobotMap.LED.Sections getSection() {
        return section;
    }

    public LEDSequence getNext() {
        return next;
    }

    public void setNext(LEDSequence next) {
        this.next = next;
    }
}
