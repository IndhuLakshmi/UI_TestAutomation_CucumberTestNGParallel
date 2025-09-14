package com.framework.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry Analyzer for flaky tests - retries up to 3 times.
 */
public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private final int maxRetry = 3;

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxRetry) {
            count++;
            return true;
        }
        return false;
    }
}
