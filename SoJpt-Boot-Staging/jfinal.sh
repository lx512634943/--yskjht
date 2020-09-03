#!/bin/bash
# ----------------------------------------------------------------------
#
# 使用说明：
# 1: 该脚本使用前需要首先修改 MAIN_JAR 值，使其指向实际的启动类
#
# 2：使用命令行 ./jfinal.sh start | stop | restart 可启动/关闭/重启项目  
#
# 3: JAVA_OPTS 可传入标准的 java 命令行参数，例如 -Xms256m -Xmx1024m 这类常用参数
#
# 4: 函数 start() 给出了 4 种启动项目的命令行，根据注释中的提示自行选择合适的方式
#
# ----------------------------------------------------------------------

# 启动JAR，该脚本文件用于别的项目时要改这里
MAIN_JAR=SoJpt-Boot-1.0.jar

if [[ "$MAIN_JAR" == "SoJpt-Boot-Staging.jar" ]]; then
    echo "请先修改 MAIN_JAR 的值为你自己项目启动jar，然后再执行此脚本。"
	exit 0
fi

COMMAND="$1"

if [[ "$COMMAND" != "start" ]] && [[ "$COMMAND" != "stop" ]] && [[ "$COMMAND" != "restart" ]]; then
	echo "Usage: $0 start | stop | restart"
	exit 0
fi


# Java 命令行参数，根据需要开启下面的配置，改成自己需要的，注意等号前后不能有空格
 JAVA_OPTS="-Xms32m -Xmx64m"

# 生成 class path 值
APP_BASE_PATH=$(cd `dirname $0`; pwd)
CP=${APP_BASE_PATH}/config:${APP_BASE_PATH}/lib/*

function start()
{
    # 运行为后台进程，并在控制台输出信息
    java -Xverify:none ${JAVA_OPTS} -cp ${CP} -jar ./lib/${MAIN_JAR} &

    # 运行为后台进程，并且不在控制台输出信息
    # nohup java -Xverify:none ${JAVA_OPTS} -cp ${CP} -jar ./lib/${MAIN_JAR} >/dev/null 2>&1 &

    # 运行为后台进程，并且将信息输出到 output.log 文件
    # nohup java -Xverify:none ${JAVA_OPTS} -cp ${CP} -jar ./lib/${MAIN_JAR} > output.log &

    # 运行为非后台进程，多用于开发阶段，快捷键 ctrl + c 可停止服务
    # java -Xverify:none ${JAVA_OPTS} -cp ${CP} -jar ./lib/${MAIN_JAR}
}

function stop()
{
    # 支持集群部署
    kill `pgrep -f ${APP_BASE_PATH}` 2>/dev/null
    
    # kill 命令不使用 -9 参数时，会回调 onStop() 方法，确定不需要此回调建议使用 -9 参数
    # kill `pgrep -f ./lib/${MAIN_JAR}` 2>/dev/null

    # 以下代码与上述代码等价
    # kill $(pgrep -f ./lib/${MAIN_JAR}) 2>/dev/null
}

if [[ "$COMMAND" == "start" ]]; then
	start
elif [[ "$COMMAND" == "stop" ]]; then
    stop
else
    stop
    start
fi
