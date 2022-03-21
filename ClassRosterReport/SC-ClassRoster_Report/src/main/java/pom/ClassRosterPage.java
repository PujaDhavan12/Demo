package pom;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ClassRosterPage {

    private String os = null;
    String dir = null;
    Properties properties = new Properties();
    InputStream input = null;

    public ClassRosterPage() {
        try {
            dir = System.getProperty("user.dir");
            dir = dir.replace("SC-ClassRoster_Report", "");
            os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                input = new FileInputStream(dir + "\\Properties\\ClassRosterPage.properties");
            } else {
                input = new FileInputStream(dir + "/Properties/ClassRosterPage.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String getClassRosterTab() {
        return properties.getProperty("CLASSROSTER_TAB");
    }

    public String getBlueFilters() {
        return properties.getProperty("BLUE_FILTERS");
    }

    public String getGradeFilters() {
        return properties.getProperty("GRADE_FILTER");
    }

    public String getAllGradeFilters() { return properties.getProperty("ALL_GRADE"); }

    public String getFirstGrade(){return properties.getProperty("FIRST_GRADE"); }

    public String getSchoolFilter(){return properties.getProperty("SCHOOL_FILTER"); }

    public String getFirstSchool(){return properties.getProperty("FIRST_SCHOOL"); }

    public String getFirstSchoolHisd(){return properties.getProperty("HISD_FIRST_SCHOOL"); }

    public String getRefresh(){return properties.getProperty("REFRESH"); }

    public String getTABLE(){return properties.getProperty("TABLE"); }

    public String getFullScreenButton(){return properties.getProperty("FULL_SCREEN_BUTTON"); }

    public String getFullScreenTable(){return properties.getProperty("FULL_SCREEN_TABLE"); }

    public String getExitScreenButton(){return properties.getProperty("EXIT_FULL_SCREEN_BUTTON"); }

    public String getSdhcExitScreenButton(){return properties.getProperty("EXIT_FULL_SCREEN_BUTTON_SDHC"); }

    public String getTeacherFirstGrade(){return properties.getProperty("FIRST_GRADE_TEACHER"); }

    public String getTeacherFilter(){return properties.getProperty("TEACHER_FILTER"); }

    public String getFirstTeacher(){return properties.getProperty("FIRST_TEACHER"); }

    public String getTeacherFirstGradeSdhc(){return properties.getProperty("FIRST_GRADE_SDHC"); }


}
