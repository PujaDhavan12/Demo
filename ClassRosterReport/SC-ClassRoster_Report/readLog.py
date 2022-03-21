import os
import platform

path = os.getcwd()
if(platform.system() != "Windows"):
   path = path + "/SC-ClassRoster_Report/log//readLog.txt"
else:
    path = path + "\SC-ClassRoster_Report\log\\readLog.txt"
f = open(path, "r")
for x in f:
  print(x)
