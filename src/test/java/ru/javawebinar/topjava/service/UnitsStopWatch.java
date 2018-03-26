package ru.javawebinar.topjava.service;

import org.junit.AssumptionViolatedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;

public class UnitsStopWatch extends Stopwatch {
    @Override
    protected void succeeded(long nanos, Description description) {
        System.out.println(description + "SUCCESS:" +  nanos);
    }

    @Override
    protected void failed(long nanos, Throwable e, Description description) {
        System.out.println(description + "FAILED" + nanos);
    }

    @Override
    protected void skipped(long nanos, AssumptionViolatedException e, Description description) {
        System.out.println(description + "SKIPPED" + nanos);
    }



}
