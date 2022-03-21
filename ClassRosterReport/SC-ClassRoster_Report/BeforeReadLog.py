import os
import platform

path = os.getcwd()
if(platform.system() != "Windows"):
    path = path + "/SC-ClassRoster_Report/log/BeforeReadLog.txt"
else:
    path = path + "\log\\BeforeReadLog.txt"
try:
    f = open(path, "r")
    for x in f:
        print(x)
except IOError:
    print "Read File handled for SC-ClassRoster_Report...."