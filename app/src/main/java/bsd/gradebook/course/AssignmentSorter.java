package bsd.gradebook.course;

import java.util.Comparator;

public class AssignmentSorter {

    public static class AlphabeticalAssignmentSorter implements Comparator<Course.Assignment> {

        private static final AlphabeticalAssignmentSorter instance = new AlphabeticalAssignmentSorter();

        public static AlphabeticalAssignmentSorter getInstance() {
            return instance;
        }

        private AlphabeticalAssignmentSorter() { }

        @Override
        public int compare(Course.Assignment lhs, Course.Assignment rhs) {
            return lhs.name.compareTo(rhs.name);
        }
    }

    public static class DateAssignmentSorter implements Comparator<Course.Assignment> {

        @Override
        public int compare(Course.Assignment lhs, Course.Assignment rhs) {
            return lhs.date.compareTo(rhs.date);
        }
    }
}
