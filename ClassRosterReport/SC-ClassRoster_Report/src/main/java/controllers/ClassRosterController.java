package controllers;

import helpers.DriverHelper;
import pom.ClassRosterPage;
import utils.ConsoleLogger;
import utils.Dynamic;

public class ClassRosterController {

    private static ClassRosterPage classRosterPage=new ClassRosterPage();

    /*Verifying Display Of ClassRoster Tab*/
    public static boolean verifyClassRosterPage()
    {
        return DriverHelper.verifyDisplayByXpath(classRosterPage.getClassRosterTab());
    }

    //Clicking on Class Roster Tab
    public static boolean clickClassRosterTab()
    {
        boolean res=false;
        try {
            DriverHelper.clickXpath(classRosterPage.getClassRosterTab());
            DriverHelper.waitUntilLoaderInvisible();
            res=true;
        }catch(Exception e)
        {
            ConsoleLogger.DebugLog("Exception Handled For Click Class Roster Tab");
        }
        return res;
    }

    //Verifying Filters in class roster tab displaying Or not
    public static boolean verifyFilersDisplay()
    {
      return DriverHelper.verificationOfElements(classRosterPage.getBlueFilters());
    }

    //Select Grade and School Filters
    public  static boolean selectClassRosterFilters()
    {
        boolean res=false;
        try{
             //Click on Grade Filter
            DriverHelper.clickXpath(classRosterPage.getGradeFilters());
            if(Dynamic.getUserLevel().equalsIgnoreCase("District")) {
                //Select All Grade
                 DriverHelper.clickXpath(classRosterPage.getAllGradeFilters());
                //Click On First Grade from Grade dropdown
                DriverHelper.clickXpath(classRosterPage.getFirstGrade());
                //CLick on School filter
                DriverHelper.clickXpath(classRosterPage.getSchoolFilter());
                //Click on First School From School Dropdown
                if(Dynamic.getInstance().equalsIgnoreCase("HISD"))
                {
                    DriverHelper.clickXpath(classRosterPage.getFirstSchoolHisd());
                }else {
                    DriverHelper.clickXpath(classRosterPage.getFirstSchool());
                }
                //Click on refresh button
            }else if(Dynamic.getUserLevel().equalsIgnoreCase("Teacher"))
            {
                boolean allGrade=DriverHelper.verifyDisplayByXpath(classRosterPage.getAllGradeFilters());
                if(allGrade) {
                    DriverHelper.clickXpath(classRosterPage.getAllGradeFilters());
                    DriverHelper.clickXpath(classRosterPage.getTeacherFirstGradeSdhc());
                }else{
                    DriverHelper.clickXpath(classRosterPage.getTeacherFirstGrade());
                }
            }else if(Dynamic.getUserLevel().equalsIgnoreCase("campus"))
            {
                //Select All Grade
                DriverHelper.clickXpath(classRosterPage.getAllGradeFilters());
                //Click On First Grade from Grade dropdown
                DriverHelper.clickXpath(classRosterPage.getFirstGrade());
                //Click On Teacher Filter
                DriverHelper.clickXpath(classRosterPage.getTeacherFilter());
                //Click on First Teacher
                DriverHelper.clickXpath(classRosterPage.getFirstTeacher());
            }
            DriverHelper.clickXpath(classRosterPage.getRefresh());
            res=true;
        }catch(Exception e)
        {
            ConsoleLogger.DebugLog("Exception Handled For Select Filters Method");
        }
        return res;
    }

    //Verify Table Display Or not
    public static boolean verifyTableDisplay()
    {
        DriverHelper.waitUntilLoaderInvisible();
        return DriverHelper.verifyDisplayByXpath(classRosterPage.getTABLE());
    }

    //Click on FullScreen Button
    public static boolean clickFullScreenButton()
    {
        boolean res=false;
        try {
            DriverHelper.clickXpath(classRosterPage.getFullScreenButton());
            res=true;
        }catch(Exception e)
        {
            ConsoleLogger.DebugLog("Exception Handled Click Full Screen Button");
        }
        return res;
    }

    //Verifying Full Screen Table Display or not
    public static boolean verifyFullScreenTable()
    {
        //Verifying Table display after click on full screen button
       boolean res= DriverHelper.verifyDisplayByXpath(classRosterPage.getFullScreenTable());
       return res;
    }
    //Click Exit Screen Button
    public static void clickExitFullScreen()
    {
        try {
            //Clicking on Exit Screen Button
            if(Dynamic.getInstance().equalsIgnoreCase("HISD") ||Dynamic.getInstance().equalsIgnoreCase("SDHC"))
            {
                DriverHelper.clickXpath(classRosterPage.getSdhcExitScreenButton());
            }else {
                DriverHelper.clickXpath(classRosterPage.getExitScreenButton());
            }
        }catch (Exception e)
        {
            ConsoleLogger.DebugLog("Exception Handled Exit Full Screen Button");
        }
    }
}
