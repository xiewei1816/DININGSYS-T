package com.yqsh.diningsys.web.controller.deskBusiness;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yqsh.diningsys.web.controller.base.BaseController;
import com.yqsh.diningsys.web.model.archive.DgItemFile;
import com.yqsh.diningsys.web.model.deskBusiness.DeskBusinessSetting;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimit;
import com.yqsh.diningsys.web.model.deskBusiness.DgItemTimeLimitPic;
import com.yqsh.diningsys.web.service.deskBusiness.DgItemTimeLimitService;

@Controller
@RequestMapping("/DgItemTimeLimit")
public class DgItemTimeLimitController extends BaseController {
	@Autowired
	private DgItemTimeLimitService dgItemTimeLimitService;
   
	@RequestMapping(value = "/index")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("item", dgItemTimeLimitService.getOne());
		List<DgItemTimeLimitPic> zPicLimitPics = dgItemTimeLimitService
				.selectAllZPic();
		String zPicNames = "";
		if (zPicLimitPics.size() > 0) {
			for (int i = 0; i < zPicLimitPics.size(); i++) {
				zPicNames += zPicLimitPics.get(i).getPicNames() + "/";
			}
			zPicNames = zPicNames.substring(0, zPicNames.length() - 1);
		}
		List<DgItemTimeLimitPic> hPicLimitPics = dgItemTimeLimitService
				.selectAllZPic();
		String hPicNames = "";
		if (hPicLimitPics.size() > 0) {
			for (int i = 0; i < hPicLimitPics.size(); i++) {
				hPicNames += hPicLimitPics.get(i).getPicNames() + "/";
			}
			hPicNames = hPicNames.substring(0, hPicNames.length() - 1);
		}
		request.setAttribute("zPics", zPicNames);
		request.setAttribute("hPics", hPicNames);
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/item_time_limit");
		return model;
	}

	@RequestMapping("/getAllData")
	@ResponseBody
	public Object getAllData(DgItemTimeLimit dgItemTimeLimit) {
		List<DgItemTimeLimit> ret = dgItemTimeLimitService
				.getAllData(dgItemTimeLimit);
		return ret;
	}

	/**
	 * @return
	 */
	@RequestMapping(value = "/toAddPlanDish")
	public ModelAndView toAddPlanDish(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/meal_time_add");
		return model;
	}

	@RequestMapping(value = "/pic_index")
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		if (type.equals("1")) {
			List<DgItemTimeLimitPic> hPicLimitPics = dgItemTimeLimitService
					.selectAllHPic();
			request.setAttribute("count", dgItemTimeLimitService.picHCount());
			request.setAttribute("pic", hPicLimitPics);
		} else {
			List<DgItemTimeLimitPic> zPicLimitPics = dgItemTimeLimitService
					.selectAllZPic();
			request.setAttribute("count", dgItemTimeLimitService.picZCount());
			request.setAttribute("pic", zPicLimitPics);
		}
		request.setAttribute("type", request.getParameter("type"));
		if (type.equals("1")) {
			return new ModelAndView("deskBusiness/product_item/web_pic_h");
		} else {
			return new ModelAndView("deskBusiness/product_item/web_pic_z");
		}
	}

	@RequestMapping(value = "/pic_add_index")
	public ModelAndView pic_add_index(HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("type", request.getParameter("type"));
		ModelAndView model = new ModelAndView(
				"deskBusiness/product_item/web_pic_add");
		return model;
	}

	/**
	 * 
	 * 获取具体打折项下的品项
	 */
	@RequestMapping(value = "/getMealTimeAddDish")
	@ResponseBody
	public Object getMealTimeAddDish(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> ret = new HashMap<String, Object>();
		String chooseTrIds = request.getParameter("chooseTrIds");
		if (!StringUtils.isEmpty(chooseTrIds)) {
			List<DgItemTimeLimit> listRet = new ArrayList<DgItemTimeLimit>();
			List<Integer> ids = new ArrayList<Integer>();

			String str[] = chooseTrIds.split(",");
			for (int i = 0; i < str.length; i++) {
				ids.add(Integer.parseInt(str[i]));
			}
			if (ids.size() > 0) {
				List<DgItemFile> list2 = dgItemTimeLimitService
						.selectItemByAdd(ids);
				for (DgItemFile item : list2) {
					DgItemTimeLimit s = new DgItemTimeLimit();
					s.setId(-item.getId());
					s.setItemId(item.getId());
					s.setItemCode(item.getNum());
					s.setName(item.getName());
					s.setSaleLimit(0.0);
					s.setSurplusLimit(0.0);
					s.setBzPrice(item.getStandardPrice());
					listRet.add(s);
				}
				ret.put("success", true);
				ret.put("list", listRet);
			} else {
				ret.put("success", true);
				ret.put("list", null);
			}
			return ret;
		}
		ret.put("success", true);
		ret.put("list", null);
		return ret;
	}

	@RequestMapping("/saveData")
	@ResponseBody
	public Object saveData(String data,String zPics,String hPics) {
		Map<String,Object> mapObj=dgItemTimeLimitService.saveItemTimeLimit(data, zPics, hPics);
		return mapObj;
	}

	/**
	 * 
	 * 批量删除
	 * 
	 * @param ids
	 * @return
	 */

	@RequestMapping("/delData")
	@ResponseBody
	public Object delData(String ids) {
		Map<String, Object> ret = new HashMap<String, Object>();
		dgItemTimeLimitService.deleteIds(ids);
		ret.put("success", true);
		return ret;
	}
}