package math_tutor.backend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TestService {

    public boolean recordTestCompletion(String testName, String studentName, int studentId, int score, String completionDate) {
        String insertQuery = """
            INSERT INTO tests (test_name, student_name, student_id, score, completion_date)
            VALUES (?, ?, ?, ?, ?)
        """;

        try (Connection connection = ConnectionDB.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {

            preparedStatement.setString(1, testName);
            preparedStatement.setString(2, studentName);
            preparedStatement.setInt(3, studentId);
            preparedStatement.setInt(4, score);
            preparedStatement.setDate(5, Date.valueOf(completionDate));

            return preparedStatement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<LeaderboardEntry> getLeaderboard() {
        List<LeaderboardEntry> entries = new ArrayList<>();
        String query = """
        SELECT student_id, student_name, MAX(score) AS max_score
        FROM tests
        GROUP BY student_id, student_name
        ORDER BY max_score DESC
    """;

        try (Connection connection = ConnectionDB.getInstance().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            while (resultSet.next()) {
                entries.add(new LeaderboardEntry(
                        resultSet.getInt("student_id"),
                        resultSet.getString("student_name"),
                        resultSet.getInt("max_score")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entries;
    }


    // In TestService.java

    public static ObservableList<StudentPerformanceEntry> getAllTestRecords() {
        ObservableList<StudentPerformanceEntry> records = FXCollections.observableArrayList();
        try (Connection conn = ConnectionDB.getInstance().getConnection()) {
            String query = "SELECT test_id, student_id, test_name, student_name, score, completion_date FROM tests";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()) {
                records.add(new StudentPerformanceEntry(
                        rs.getString("test_id"),
                        rs.getString("student_id"),
                        rs.getString("test_name"),
                        rs.getString("student_name"),
                        rs.getString("score"),
                        rs.getString("completion_date")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public static class StudentPerformanceEntry {
        private final String testId;
        private final String studentId;
        private final String testName;
        private final String studentName;
        private final String score;
        private final String completionDate;

        public StudentPerformanceEntry(String testId, String studentId, String testName,
                                       String studentName, String score, String completionDate) {
            this.testId = testId;
            this.studentId = studentId;
            this.testName = testName;
            this.studentName = studentName;
            this.score = score;
            this.completionDate = completionDate;
        }

        public String getTestId() { return testId; }
        public String getStudentId() { return studentId; }
        public String getTestName() { return testName; }
        public String getStudentName() { return studentName; }
        public String getScore() { return score; }
        public String getCompletionDate() { return completionDate; }
    }







    public static ObservableList<TestHistoryEntry> getTestHistory(String username) {
        ObservableList<TestHistoryEntry> history = FXCollections.observableArrayList();
        try (Connection conn = ConnectionDB.getInstance().getConnection()) {
            // Fixed column names to match actual database schema
            String query = """
                SELECT completion_date AS test_date, test_name, score 
                FROM tests 
                WHERE student_name = ?  -- Changed to student_name
            """;
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                history.add(new TestHistoryEntry(
                        rs.getString("test_date"),
                        rs.getString("test_name"),
                        rs.getInt("score")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return history;
    }

    public static class LeaderboardEntry {
        private final int studentId;
        private final String studentName;
        public final int score;

        public LeaderboardEntry(int studentId, String studentName, int score) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.score = score;
        }

        public int getStudentId() {
            return studentId;
        }

        public String getStudentName() {
            return studentName;
        }

        public int getScore() {
            return score;
        }
    }

    public static class TestHistoryEntry {
        public final String testDate;
        public final String testName;
        public final int score;

        public TestHistoryEntry(String testDate, String testName, int score) {
            this.testDate = testDate;
            this.testName = testName;
            this.score = score;
        }

        public String getTestDate() {
            return testDate;
        }

        public String getTestName() {
            return testName;
        }

        public int getScore() {
            return score;
        }
    }
}
