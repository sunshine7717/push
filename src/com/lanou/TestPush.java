package com.lanou;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;

@WebServlet("/test")
public class TestPush extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("Do Get");
		HttpSession session = req.getSession();
		int times;
		//如果登录成功清除Times
		String username = req.getParameter("username");
		String pwd = req.getParameter("pwd");
		if(username.equals("lgc") && pwd.equals("123")) {
			times = 0;
			session.setAttribute("times", times);
		} else {
			//如果登录不成功，需要判断是不是登录了三次
			if(session.getAttribute("times") != null) {
				times = Integer.parseInt(session.getAttribute("times").toString());
				times++;
				session.setAttribute("times", times);
			} else {
				times = 0;
				session.setAttribute("times", times);
			}
			if(times >=3) {
				JPushClient client = new JPushClient("9ad748607e2d4686e236c389", "3e3d6632c02cb8a645031ea9");
				PushPayload payLoad = PushPayload.alertAll("�����");
				try {
					client.sendPush(payLoad);
				} catch (APIConnectionException e) {
					e.printStackTrace();
				} catch (APIRequestException e) {
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doGet(req, resp);
	}
	
}
