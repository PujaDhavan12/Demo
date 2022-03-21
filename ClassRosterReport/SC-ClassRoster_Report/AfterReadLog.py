import os
import platform

path = os.getcwd()
if(platform.system() != "Windows"):
    path = path + "/SC-ClassRoster_Report/log/AfterReadLog.txt"
else:
    path = path + "\log\\AfterReadLog.txt"
try:
    f = open(path, "r")
    for x in f:
        print(x)
except IOError:
    print "Read File handled for SC-ClassRoster_Report...."