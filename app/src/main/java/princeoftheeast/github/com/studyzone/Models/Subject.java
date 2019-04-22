package princeoftheeast.github.com.studyzone.Models;

/**
 * Created by M Chowdhury on 13/02/2019.
 */

public class Subject {
    private String subject;
    private String subjectTime;

    public Subject() {
    }
    public Subject(String subject, String subjectTime) {
        this.subject = subject;
        this.subjectTime = subjectTime;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setSubjectTime(String subjectTime) {
        this.subjectTime = subjectTime;
    }

    public String getSubjectTime() {
        return subjectTime;
    }
}
