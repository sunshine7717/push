package com.lanou;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.PushPayload;

@WebServlet("/test")
public class TestServlet extends HttpServlet {

	int a = 0;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String name1 = "jing";
		String pwd1 = "jing";
		String name = req.getParameter("name");
		String pwd = req.getParameter("pwd");

		HttpSession session = req.getSession();

		if (name.equals(name1) && !pwd.equals(pwd1)) {

			a = a + 1;

			session.setAttribute("times", a);

			if (session.getAttribute("times").equals(3)) {
				JPushClient jpushClient = new JPushClient("9ad748607e2d4686e236c389", "3e3d6632c02cb8a645031ea9");
				System.out.println("==========jin=============");
				PushPayload payload = PushPayload.alertAll("你好,你已经登录失败三次请勿再次登录");

				try {
					jpushClient.sendPush(payload);

				} catch (APIConnectionException e) {

					e.printStackTrace();
				} catch (APIRequestException e) {

					e.printStackTrace();
				}

			} else if (name.equals(name1) && pwd.equals(pwd1)) {
				a = 0;
				session.setAttribute("times", a);

			}

		}

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		this.doGet(req, resp);
	}

}
