package com.wkh.measurement;

import java.io.PrintStream;

public class LoggerContext {

    private static final LoggerContext instance = new LoggerContext();

    public static LoggerContext getInstance() {
        return instance;
    }

    private class LogItem {

        private String correlationId;

        private long startTime = -1;

        private long endTime = -1;

        public LogItem(String aCorrelationId) {
            correlationId = aCorrelationId;
        }

        public String getCorrelationId() {
            return correlationId;
        }

        public long getStartTime() {
            return startTime;
        }

        public void setStartTime(long startTime) {
            this.startTime = startTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public long getDuration() {
            return endTime - startTime;
        }
    }

    private static ThreadLocal<LogItem> logItem = new ThreadLocal<LogItem>();

    public void start(String corrId) {
        logItem.set(new LogItem(corrId));
        logItem.get().setStartTime(System.currentTimeMillis());
    }

    public void finish(String aCorrId, PrintStream aPrintStream) {
        logItem.get().setEndTime(System.currentTimeMillis());
        aPrintStream.printf("Execution time %s %d%n (ms): \n", aCorrId, logItem.get().getDuration());
    }
}
