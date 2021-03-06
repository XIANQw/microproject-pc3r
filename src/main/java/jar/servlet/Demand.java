package jar.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.*;
import javax.servlet.http.*;

import jar.bean.*;
import jar.dao.*;
import jar.util.ToJson;

import java.util.*;

public class Demand {

	public static void createSearchAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		int idu = ((UserBean) req.getSession().getAttribute("user")).getId();
		String destination = req.getParameter("destination").toLowerCase();

		Date checkin = Date.valueOf(req.getParameter("checkin"));
		Date checkout = Date.valueOf(req.getParameter("checkout"));
		String numPeople = req.getParameter("nb");
		String type = req.getParameter("type");
		String smoker = req.getParameter("smoker");

		DemandBean cmd = new DemandBean();
		cmd.setCheckin(checkin);
		cmd.setCheckout(checkout);
		cmd.setIdu(idu);
		cmd.setStatus("Pending");
		req.getSession().setAttribute("cmd", cmd);

		HashMap<String, String> attrs = new HashMap<>();
		attrs.put("city", destination);
		attrs.put("persons", numPeople);
		attrs.put("type", type);
		attrs.put("smoker", smoker);

		List<RessourceBean> tmp = RessourceDao.getRessourcesFrom(attrs);
		String data = "[", json = "", info = "";
		int cpt = 0;
		if (tmp.size() > 0) {
			for (int i = 0; i < tmp.size() - 1; i++) {
				RessourceBean res = tmp.get(i);
				if (res.getIdu() != idu) {
					data += res.toJson();
					data += ",";
					cpt += 1;
				}
			}
			data += tmp.get(tmp.size() - 1).toJson();
			cpt += 1;
		}
		data += "]";
		if (tmp.size() > 0) {
			info = "We find " + cpt + " resource for you !";
			json = ToJson.toJson(data, info, 1);
		} else {
			info = "Sorry.. we have not found any resource";
			json = ToJson.toJson(data, info, 0);
		}
		resp.getWriter().write(json);
		System.out.println(json);
	}

	public static void sendDemandAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		int idr = Integer.parseInt(req.getParameter("id"));
		java.util.Date createTime = new java.util.Date();
		DemandBean cmd = (DemandBean) req.getSession().getAttribute("cmd");
		cmd.setIdr(idr);
		cmd.setCreateTime(createTime);
		DemandDao.saveDemand(cmd);
		String json = "{\"info\":\"Your demand sended successfully\"}";
		resp.getWriter().write(json);
	}

	public static void getSendedDemandsAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		int owner = ((UserBean) req.getSession().getAttribute("user")).getId();
		HashMap<String, String> attrs = new HashMap<>();
		attrs.put("idu", Integer.toString(owner));
		List<DemandBean> demands = DemandDao.getDemandsFrom(attrs);
		String json = "[";
		for (int i = 0; i < demands.size() - 1; i++) {
			attrs.clear();
			attrs.put("id", Integer.toString(demands.get(i).getIdr()));
			RessourceBean res = RessourceDao.getRessourcesFrom(attrs).get(0);
			json += "{\"demand\":" + demands.get(i).toJson() + ", \"res\":" + res.toJson() + "}, ";
		}
		if (demands.size() > 0) {
			attrs.clear();
			attrs.put("id", Integer.toString(demands.get(demands.size() - 1).getIdr()));
			RessourceBean res = RessourceDao.getRessourcesFrom(attrs).get(0);
			json += "{\"demand\":" + demands.get(demands.size() - 1).toJson() + ", \"res\":" + res.toJson() + "}";
		}
		json += "]";
		resp.getWriter().write(json);
		System.out.println(json);
	}

	public static void deleteDemandAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		String id = req.getParameter("id");
		HashMap<String, String> attrs = new HashMap<>();
		attrs.put("id", id);
		DemandDao.deleteDemandsFrom(attrs);
		resp.getWriter().write("{\"info\": \"Your demande has been deleted successfully\"}");
	}

	public static void getRecievedDemandsAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		int owner = ((UserBean) req.getSession().getAttribute("user")).getId();
		HashMap<String, String> attrs = new HashMap<>();
		attrs.put("idu", Integer.toString(owner));
		List<RessourceBean> ressources = RessourceDao.getRessourcesFrom(attrs);
		List<DemandBean> demands = new ArrayList<DemandBean>();
		HashMap<DemandBean, RessourceBean> dmdRes = new HashMap<>();
		for (RessourceBean res : ressources) {
			attrs.clear();
			attrs.put("idr", Integer.toString(res.getId()));
			List<DemandBean> dmds = DemandDao.getDemandsFrom(attrs);
			demands.addAll(dmds);
			for (DemandBean dmd : dmds) {
				dmdRes.put(dmd, res);
			}
		}
		String json = "[";
		for (int i = 0; i < demands.size() - 1; i++) {
			int idu = demands.get(i).getIdu();
			String username = UserDao.getUsername(idu);
			String jsonUser = "{\"id\":" + idu + ",\"username\":\"" + username + "\"}";
			json += "{\"demand\":" + demands.get(i).toJson() + ",\"demander\":" + jsonUser + ",\"res\":"
					+ dmdRes.get(demands.get(i)).toJson() + "}, ";
		}
		if (demands.size() > 0) {
			int idu = demands.get(demands.size() - 1).getIdu();
			String username = UserDao.getUsername(idu);
			String jsonUser = "{\"id\":" + idu + ",\"username\":\"" + username + "\"}";
			json += "{\"demand\":" + demands.get(demands.size() - 1).toJson() + ",\"demander\":" + jsonUser
					+ ",\"res\":" + dmdRes.get(demands.get(demands.size() - 1)).toJson() + "}";
		}
		json += "]";
		resp.getWriter().write(json);
		System.out.println(json);
	}

	public static void acceptDemandAjax(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		if (!Client.sessionValide(req, resp)) {
			String json = ToJson.toJson("", "Session invalid, reconnect please ...", 0);
			resp.getWriter().write(json);
			return;
		}
		String idc = req.getParameter("id");
		HashMap<String, String> attrs = new HashMap<>();
		attrs.put("id", idc);
		DemandBean dmd = DemandDao.getDemandsFrom(attrs).get(0);
		attrs.clear();
		attrs.put("idr", Integer.toString(dmd.getIdr()));
		String status = "Accepted";
		attrs.put("status", status);
		List<DemandBean> cmdsOfRes = DemandDao.getDemandsFrom(attrs);
		boolean conflict = false;
		for (DemandBean com : cmdsOfRes) {
			Date dmdIn = java.sql.Date.valueOf(dmd.getCheckin().toString()),
					dmdOut = java.sql.Date.valueOf(dmd.getCheckout().toString()),
					comIn = java.sql.Date.valueOf(com.getCheckin().toString()),
					comOut = java.sql.Date.valueOf(com.getCheckout().toString());

			if (dmdOut.before(comIn) || dmdIn.after(comOut))
				continue;
			else {
				conflict = true;
				break;
			}
		}
		if (conflict) {
			String json = "{\"info\":\"This demand could not be accepted, it will cause a conflict\", \"status\":0}";
			resp.getWriter().write(json);
		} else {
			dmd.setStatus(status);
			DemandDao.updateDemand(dmd);
			String json = "{\"info\":\"The demand has been accepted\", \"status\":1}";
			resp.getWriter().write(json);
		}
	}
}