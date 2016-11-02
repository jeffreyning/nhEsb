package test;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nh.esb.core.NhCmdRequest;
import com.nh.esb.core.NhCmdResult;
import com.nh.esb.ws.INhCmdService;

/**
 * Servlet implementation class MyTest
 */
public class MyTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public MyTest() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cmdName=request.getParameter("cmdName");
		String retStr="";
		if(cmdName.equals("Config")){
			NhCmdRequest nhCmdRequest4Config=new NhCmdRequest();
			nhCmdRequest4Config.setCmdName(cmdName);
			nhCmdRequest4Config.setFromSysId("sysA");
			nhCmdRequest4Config.setToSysId("NHESB");
			INhCmdService cmdServiceConfig=TestUtil.getNhCmdService4Config();
			NhCmdResult result=cmdServiceConfig.execNhCmd(nhCmdRequest4Config);
			retStr=String.format("status=%d,code=%s,data=%s",
					result.getResultStatus(),result.getResultCode(),result.getResultData());
		}else if(cmdName.equals("Test")){
			NhCmdRequest nhCmdRequest=new NhCmdRequest();
			nhCmdRequest.setCmdName(cmdName);
			nhCmdRequest.setFromSysId("SYSA");
			nhCmdRequest.setToSysId("SYSB");
			INhCmdService cmdService=TestUtil.getNhCmdService();
			NhCmdResult result=cmdService.execNhCmd(nhCmdRequest);	
			retStr=String.format("status=%d,code=%s,data=%s",
					result.getResultStatus(),result.getResultCode(),result.getResultData());
		}
		response.getWriter().append(retStr);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
