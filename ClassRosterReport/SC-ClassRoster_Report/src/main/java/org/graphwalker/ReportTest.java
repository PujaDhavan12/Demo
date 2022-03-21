package org.graphwalker;

import controllers.ReportController;
import helpers.DriverHelper;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;

import static org.graphwalker.LoginTest.dependant;

@GraphWalker(value = "random(edge_coverage(100))")
public class ReportTest extends ExecutionContext implements org.graphwalker.Report {

    /*Verifying LaunchPad*/
    public void v_VerifyLaunchPad() {
        //No Functionality
    }

    /*Verify CLassRoster Report*/
    @Override
    public void v_VerifyClassRosterReport() {
        //No Functionality
    }

    /*Click On Reports*/
    public void e_ClickReports() {
        if (dependant) {
            boolean reports = ReportController.clickReports();
            DriverHelper.testExecution(reports);
        }
    }


}