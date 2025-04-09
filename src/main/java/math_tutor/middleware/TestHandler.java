package math_tutor.middleware;

import math_tutor.backend.TestService;

public class TestHandler {
    private TestService testService;

    public TestHandler() {
        this.testService = new TestService();
    }

    public boolean handleTestCompletion(String testName, String studentName, int studentId, int score, String completionDate) {
        return testService.recordTestCompletion(testName, studentName, studentId, score, completionDate);
    }
}
