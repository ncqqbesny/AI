#!/bin/sh

pp=/usr/local/target
export JAR_NAME_EUREKA=app_base-0.0.1-SNAPSHOT.jar
export JAR_NAME_EUREKAa=app_device-0.0.1-SNAPSHOT.jar


export REGISTER=$pp/$JAR_NAME_EUREKA
export REGISTERa=$pp/$JAR_NAME_EUREKAa
 
case "$1" in
 
start)
        ## 启动register
        echo "REGISTER开始启动----------------------"
		REGISTER_pid=`ps -ef | grep -w $JAR_NAME_EUREKA | grep -v "grep" | awk '{print $2}'`
		if [ "$REGISTER_pid" != "" ]; then
            echo "===$JAR_NAME_EUREKA 进程已经存在"
		else
		        nohup java  -jar $REGISTER  --spring.profiles.active=prd --spring.devjob.boot=debug 1>/dev/null 2>&1 &
		fi
        until [ -n "$REGISTER_pid" ]
            do
              REGISTER_pid=`ps -ef | grep -w $JAR_NAME_EUREKA | grep -v "grep" | awk '{print $2}'` 
            done
        echo "----------------------REGISTER启动成功pid:$REGISTER_pid"	
      
       echo "===startAll success==="
		 ## 启动register
        echo "REGISTERa开始启动----------------------"
		REGISTERa_pid=`ps -ef | grep -w $JAR_NAME_EUREKAa | grep -v "grep" | awk '{print $2}'`
		if [ "$REGISTERa_pid" != "" ]; then
            echo "===$JAR_NAME_EUREKa 进程已经存在"
		else
		        nohup java  -jar $REGISTERa  --spring.profiles.active=prd 1>/dev/null 2>&1 &
		fi
        until [ -n "$REGISTERa_pid" ]
            do
              REGISTERa_pid=`ps -ef | grep -w $JAR_NAME_EUREKAa | grep -v "grep" | awk '{print $2}'` 
            done
        echo "----------------------REGISTERa启动成功pid:$REGISTERa_pid"
	
        echo "===startAll success==="
        ;;
 stop)
        P_ID=`ps -ef | grep -w $JAR_NAME_EUREKA | grep -v "grep" | awk '{print $2}'`
        if [ "$P_ID" == "" ]; then
            echo "===$JAR_NAME_EUREKA process not exists or stop success"
        else
            kill -9 $P_ID
            echo "$JAR_NAME_EUREKA killed success"
        fi
		
        echo "===stop success==="
		 Pa_ID=`ps -ef | grep -w $JAR_NAME_EUREKAa | grep -v "grep" | awk '{print $2}'`
        if [ "$Pa_ID" == "" ]; then
            echo "===$JAR_NAME_EUREKa process not exists or stop success"
        else
            kill -9 $Pa_ID
            echo "$JAR_NAME_EUREKa killed success"
        fi
		
        echo "===stop success==="
        ;;   
 
restart)
        $0 stop
        sleep 2
        $0 start
        echo "===restart success==="
        ;;   
esac	
exit 0
