#!/bin/bash

# Variables
TEST=false
MBT=C:\\SANJAY\\MBT\\Docker\\MBT_SUITE\\Reports

LOGS=${MBT}/logs
current_time=$(date '+%m.%d.%Y-%I.%M%p')
COUNTER=0
URL=https://hooks.slack.com/services/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

RED='\033[0;31m'
LGREEN='\033[0;32m'
NC='\033[0m'
LGREEN='\033[1;32m'

# Go to mbt directory and create a logs file
cd ${MBT}
mkdir ${LOGS}/${current_time}

# Run test function
run_test() {
  CWD=${pwd}
  cd $PWD/$1
  test=$1
  printf '%s\n' "${PWD##*/} is running..."
  
  DATA_SC_LOGIN_BEFORE=$(python ${MBT}"/SC-ClassRoster_Report/BeforeReadLog.py")
  DATA_SC_LOGIN_AFTER=$(python ${MBT}"/SC-ClassRoster_Report/AfterReadLog.py")
  
  curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA_SC_LOGIN_BEFORE:hourglass_flowing_sand:'}" ${URL}
	
  mvn graphwalker:test >> ${LOGS}/${current_time}/${PWD##*/}.txt
  DATA=$(python ${MBT}/log_parser.py -l ${LOGS}/${current_time}/${PWD##*/}.txt)
  python ${MBT}/log_parser.py -l ${LOGS}/${current_time}/${PWD##*/}.txt
  
  if python ${MBT}/log_parser.py -l ${LOGS}/${current_time}/${PWD##*/}.txt | grep -q 'ERROR'; then
    printf "${RED} ==> ${1} Failed.\n ${NC}";
    curl -X POST -H 'Content-type: application/json' --data "{'text': '*[SC | SC - LOGIN] $current_time*'}" ${URL}

    curl -X POST -H 'Content-type: application/json' --data "{'text': '\`❌ $test\`'}" ${URL}
    curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA'}" ${URL}
  
  elif cat ${LOGS}/${current_time}/${PWD##*/}.txt | grep -q 'FAILURE'; then
    printf "${RED} ==> ${1} Failed.\n ${NC}";
    curl -X POST -H 'Content-type: application/json' --data "{'text': '*[SC | SC - LOGIN] $current_time*'}" ${URL}
    curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA'}" ${URL}
else
  #if [[ "$?" -ne 0 ]] ; then
        printf "${LGREEN} ==> ${1} Passed..\n ${NC}"
  fi
  cd ..
  
  DATA_SC_LOGIN=$(python ${MBT}"/SC-ClassRoster_Report/readLog.py")
}

# --------------Test run--------------
printf "${RED} ==> Testing has begun\n ${NC}"
start=`date +%s`

run_test SC-ClassRoster_Report
wait
ps -ef | grep 'chrome' | grep -v grep | awk '{print $2}' | xargs -r kill -9
sleep 5

end=`date +%s`
runtime=$((end-start))
printf "${LGREEN} \n==> Done. ${NC}"
# --------------End run--------------

curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA_SC_LOGIN'}" ${URL}

curl -X POST -H 'Content-type: application/json' --data "{'text': '*[SC | SC - LOGIN | DONE] $current_time*'}" ${URL}

curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA_SC_LOGIN_AFTER:white_check_mark:'}" ${URL}
curl -X POST -H 'Content-type: application/json' --data '{"text":"`Killed all chrome processes`"}' ${URL}
curl -X POST -H 'Content-type: application/json' --data "{'text':'$DATA'}" ${URL}
