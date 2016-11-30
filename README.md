# nhesb


nhEsb 为开源 ESB 项目，提供服务发布、查找和治理功能。
为了避免ESB性能瓶颈，在设计理念上采用旁路的方式。
ESB服务提供页面可以注册服务地址、访问权限，提供接口查询服务地址信息，记录服务访问日志。
这样系统间交互调用实际链接虽然不通过ESB，但认可起到服务治理的效果。提供统一的调用框架供系统嵌入。



客户端和服务端输入参数统一使用NhCmdRequest
客户端和服务端输入参数统一使用NhCmdResult

客户端通过INhCmdService接口实现类调用远程服务
INhCmdService接口目前有cxf和akka两种实现类。
NhCmdResult execNhCmd(NhCmdRequest nhCmdRequest)
INhCmdService接口实现类需要通过工厂类获取
NhEsbClientFactory或NhEsbAkkaClientFactory

服务端通过继承INhCmdHandler接口实现服务handler
且handler的命名需要符合规范
Ws+命令名称+CmdHandler
handler类需要使用spring管理
bean的id需要符合规范
ws+命令名称+CmdHandler


客户端A调用远程B系统Test服务的例子（底层选择基于cxf）

NhCmdRequest nhCmdRequest=new NhCmdRequest();//生成输入参数对象
nhCmdRequest.setCmdName("Test");//设置命令名称，在服务端会映射为名为wsTestCmdHandler的springBean对象
nhCmdRequest.setFromSysId("A");//设置客户端系统标识A
nhCmdRequest.setToSysId("B");//设置服务端系统标识B
INhCmdService cmdService=NhEsbClientFactory.getClient("B");//输入服务端系统标识B获取合适的INhCmdService
NhCmdResult result=null;
try {
result = cmdService.execNhCmd(nhCmdRequest);//远程调用
} catch (Exception e) {
e.printStackTrace();
}	
//打印返回的结果数据
retStr=String.format("status=%d,code=%s,data=%s",
result.getResultStatus(),result.getResultCode(),result.getResultData());

cxf客户端配置
NhEsbClientFactory工厂中设置不同目标系统访问地址（目标系统标识由NhEsbAddress中的sysid确定）
NhEsbClientFactory.getClient("B");通过工厂取INhCmdService时输入参数选择不同目标系统
	<bean class="com.nh.esb.ws.NhEsbClientFactory" init-method="init">
	<!--  <property name="configUrl" value="${nhesb.config.url}"></property>  -->
	<!-- <property name="remoteConfigFlag" value="true"></property> -->
	<property name="addressMap4Bean" ref="addressSysB"></property>
	</bean>
	
	<bean id="addressSysB" class="com.nh.esb.core.NhEsbAddress">
	<property name="sysid" value="B"></property>
	<property name="ip" value="localhost"></property>
	<property name="port" value="8080"></property>
	<property name="url" value="http://localhost:8080/demo-service/webservice/nhCmdService"></property>

</bean>

cxf远程服务端配置
在远程服务端容器中需配置暴露cxf接口监听服务和cxf框架
NhCmdServiceImpl负责转发调用指定的WsxxxCmdHandler实现
	<bean id="nhCmdServiceImpl" class="com.nh.esb.service.ws.NhCmdServiceImpl">
	</bean>
	<jaxws:endpoint id="nhCmdService" address="/nhCmdService"
			implementor="#nhCmdServiceImpl">
	</jaxws:endpoint>


管理端sql
CREATE TABLE `nhesb_service_address` (
  `uuid` varchar(50) default NULL,
  `sysid` varchar(50) default NULL,
  `ip` varchar(255) default NULL,
  `port` varchar(10) default NULL,
  `url` varchar(500) default NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

