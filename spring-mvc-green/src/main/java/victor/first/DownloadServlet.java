package victor.first;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

public class DownloadServlet implements Servlet {

	@Override
	public void destroy() {
	}

	@Override
	public ServletConfig getServletConfig() {
		return null;
	}

	@Override
	public String getServletInfo() {
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
//		PrintWriter writer = httpResponse.getWriter();
		ServletOutputStream outputToUser = httpResponse.getOutputStream();
		FileInputStream file = new FileInputStream("/home/training/Downloads/pdf.pdf");
		
		IOUtils.copy(file, outputToUser);
		
		httpResponse.setHeader("Content-Type", "application/pdf");
	}

}
