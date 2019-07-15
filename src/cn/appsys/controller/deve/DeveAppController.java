package cn.appsys.controller.deve;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.tomcat.util.bcel.classfile.Constant;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_ADDPeer;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.Page;
import cn.appsys.service.deve.appcategory.DeveAppCategoryService;
import cn.appsys.service.deve.appinfo.DeveAppInfoService;
import cn.appsys.service.deve.appversion.AppVersionService;
import cn.appsys.service.deve.datadictionary.DeveDictionaryService;
@Controller
@RequestMapping("/user/info")
public class DeveAppController {
	//App信息
	@Resource(name="deveAppInfoService")
	private DeveAppInfoService deveAppInfoService;
	
	//分页
	@Resource(name="deveAppCategoryService")
	private DeveAppCategoryService deveAppCategoryService;
	
	//字典类Service
	@Resource(name="deveDictionaryService")
	private DeveDictionaryService deveDictionaryService;
	
	//版本信息
	@Resource(name="appVersionService")
	private AppVersionService appVersionService;
	
	
	@RequestMapping("/appversionmodify")
	public String appversionmodify(@RequestParam("vid") Integer id) {
		System.out.println(id);
		return null;
	}
	
	/**
	 * 保存版本信息
	 * @param appVersion
	 * @param session
	 * @param request
	 * @param model
	 * @param attach
	 * @return
	 */
	@RequestMapping("addversionsave")
	public String addversionsave(AppVersion appVersion,HttpSession session,
			HttpServletRequest request,Model model,
			@RequestParam(value="a_downloadLink",required= false) MultipartFile attach) {
	String downloadLink =  null;
	String apkLocPath = null;
	String apkFileName = null;
	if(!attach.isEmpty()){
		String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
		String oldFileName = attach.getOriginalFilename();//原文件名
		String suffix = FilenameUtils.getExtension(oldFileName);//原文件后缀
		if(suffix.equalsIgnoreCase("apk")){
			 AppInfo appInfo = deveAppInfoService.queryInfoByIdAndApk(appVersion.getAppId());
			 String apkName = appInfo.getAPKName();
			 apkFileName = apkName + "-" + appInfo.getVersionNo() + ".apk";//apk文件命名：apk名称+版本号+.apk
			 File targetFile = new File(path,apkFileName);
			 if(!targetFile.exists()){
				 targetFile.mkdirs();
			 }
			 try {
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("fileUploadError", "* apk文件上传失败！");
			} 
			downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
			apkLocPath = path+File.separator+apkFileName;
		}else{
			session.setAttribute("fileUploadError", "* 上传文件的格式不正确，请选择正确的apk格式的文件！");
		}
	}
	DevUser user = (DevUser) session.getAttribute("user");
	appVersion.setCreatedBy(user.getId());
	appVersion.setCreationDate(new Date());
	appVersion.setDownloadLink(downloadLink);
	appVersion.setApkLocPath(apkLocPath);
	appVersion.setApkFileName(apkFileName);
	try {
		if(appVersionService.insertAppVersion(appVersion)>0){
			//单击"保存"按扭之后，除了更新APP版本表，还需要更新APP基础信息表的version_id(该字段记录APP最新版本号)。
			AppInfo info = new AppInfo();
			info.setId(appVersion.getAppId());
			info.setVersionId(appVersion.getId()); //appVersion.getId()拿到的是刚插入后返回的id的值
			deveAppInfoService.modifyAppInfo(info); 
			return "redirect:/user/info/appInfoList";
		}
	} catch (Exception e) {
		e.printStackTrace();
	}
	return "redirect:/deve/appinfo/appversionadd/"+appVersion.getAppId();
}

	
	/**
	 * 跳转到新增版本信息
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/appversionadd")
	public String appversionadd(@RequestParam("id") Integer id,Model model) {
		List<AppVersion> appVersionList = appVersionService.getAppVersionById(id);
		AppVersion appVersion = new AppVersion();
		appVersion.setAppId(id);//将从appinfolist.jsp传递过来的appinfo的id保存到appVersion对象中，传递到appversionadd.jsp，用于后面的新增操作。
		model.addAttribute("appVersionList",appVersionList);
		model.addAttribute("appVersion",appVersion);
		return "developer/appversionadd";
	}
	
	/**
	 * 保存修改
	 * @param model
	 * @param appInfo
	 * @param session
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value = "/appinfomodifysave", method = RequestMethod.POST)
	public String appinfoModifySave(
			Model model,
			AppInfo appInfo,
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value = "attach", required = false) MultipartFile attach) {
		String logoLocPath = ""; // 上传图片的绝对路径
		String logoPicPath = ""; // 上传图片的相对路径
		if (!attach.isEmpty()) {
			// 获取文件上传所需要的路径
			String path = request.getSession().getServletContext()
					.getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename(); // 得到原文件名
			String suffix = FilenameUtils.getExtension(oldFileName); // 原文件的后缀
			if (attach.getSize() > 5000000) {
				model.addAttribute("fileUploadError", "* 上传文件的大小超过500KB，上传失败！");
			} else if (suffix.equalsIgnoreCase("jpg")
					|| suffix.equalsIgnoreCase("png")
					|| suffix.equalsIgnoreCase("jepg")
					|| suffix.equalsIgnoreCase("pneg")) {// 上传图片格式
				String fileName = appInfo.getAPKName() + ".jpg";// 上传LOGO图片命名:apk名称.apk
				File file = new File(path, fileName);
				if (!file.exists()) {
					file.mkdirs();
				}
				try {
					attach.transferTo(file);
				} catch (IllegalStateException | IOException e) {
					model.addAttribute("fileUploadError", "* 文件上传失败");
					e.printStackTrace();
				}
				logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
				logoLocPath = path + File.separator + fileName;
			}
		}
		
		
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		try {
			if(deveAppInfoService.modifyAppInfo(appInfo) > 0){
				return "redirect:/user/info/appInfoList";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "developer/appinfomodify";
	}

	
	/**
	 * 删除图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "delfile",method = RequestMethod.GET)
	@ResponseBody
	public Object delfile(@RequestParam(value = "id",required = false)Integer id) {
		Map<String,String> map = new HashMap<String,String>();
		if (id == null || id == 0) {
			map.put("result", "failed");
		} else {
			AppInfo appInfo = deveAppInfoService.queryInfoByIdAndApk(id);  //根据id查询APP信息
			System.out.println(appInfo.getLogoLocPath());
			File file = new File(appInfo.getLogoLocPath());
			if (file.exists()) {
				if (file.delete()) { //将服务器中的LOGO图片从文件夹中删除。	
					//将数据库中appinfo表里的logoLocPath与logoPicPath字段给清空。
					if (deveAppInfoService.updateLogoPathById(id,null) > 0) {
						map.put("result", "success");
					}
				}	
			} else {
				map.put("result", "fileNoExist");  //服务器上的图片文件不存在，删除失败
			}
		}
		return map;

	}
	
	/**
	 * 根据id查询app信息/修改
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping("/appinfomodify")
	public String appinfomodify(@RequestParam("id") Integer id,Model model) {
		AppInfo appInfo = deveAppInfoService.queryInfoByIdAndApk(id);
		model.addAttribute("appInfo",appInfo);
		return "/developer/appinfomodify";
	}
	
	
	/**
	 * 新增操作验证APK名字是否已存在
	 * @param request
	 * @return
	 */
	@RequestMapping(value="apkexist",method = RequestMethod.POST)
	@ResponseBody
	public Object apkexist(HttpServletRequest request) {
		Map<String, String> resouMap = new HashMap<>();
		String APKName = request.getParameter("APKName");
		System.out.println(APKName);
		int num = deveAppInfoService.apkexist(APKName);
		System.out.println(num);
		if (APKName == null || APKName == "") {
			resouMap.put("APKName", "empty");
		}else if (num > 0) {
			resouMap.put("APKName", "exist");
		}else {
			resouMap.put("APKName", "noexist");
		}
		return resouMap;
	}
	
	
	/**
	 * 实现新增保存操作
	 * @param appInfo
	 * @param session
	 * @param request
	 * @param attach
	 * @return
	 */
	@RequestMapping(value = "/appinfoaddsave",method = RequestMethod.POST)
	public String appinfoaddsave(AppInfo appInfo,HttpSession session,HttpServletRequest request,
			@RequestParam("a_logoPicPath") MultipartFile attach,Model model) {
		String logoPicPath = "";		//用于保存图片相对路径
		String logoLocPaht = "";		//用于保存图片的绝对路径
		
		if (!attach.isEmpty()) {	//不为空的情况，我们进行文件上传
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");   //获取文件上传所需的路径
			String oldFileName = attach.getOriginalFilename();		//得到上传图片的原文件名
			String suffix = FilenameUtils.getExtension(oldFileName);	//得到原文件的后缀
			if (attach.getSize() > 5000000) {	//如果上传的文件的大小超过500kb
				model.addAttribute("fileUploadError","* 上传文件的大小超过500kb，上传失败！");
			}else if (suffix.equalsIgnoreCase("jpg")
						||suffix.equalsIgnoreCase("png")
						|| suffix.equalsIgnoreCase("jpeg")
						|| suffix.equalsIgnoreCase("pneg")) {
						//判断用户上传的文件格式是否是jpg、png、jpeg、pneg格式
				String fileName = appInfo.getAPKName() + ".jpg";	//获取上传之后的文件名字，因为APKName是唯一
				File file = new File(path,fileName);	//根据文件对象
				if (!file.exists()) { //如果文件对象不存在，那么就创建这个目录
					file.mkdirs();  //创建这个目录
				}
				try {
					attach.transferTo(file);//将attach上传到指定的目录中去
				}catch(IllegalStateException | IOException e) {
					model.addAttribute("fileUploadError","* 文件上传失败");
					e.printStackTrace();
				}
				logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName; // 用于保存图片相对路劲
				logoLocPaht = path + File.separator + fileName;		//保存图片的绝对路径
			}
		}
		//在进行新增操作时，createdBy级creationDate赋值
		DevUser user = (DevUser) session.getAttribute("user");
		appInfo.setCreatedBy(user.getId());
		appInfo.setCreationDate(new Date());	//新增时间
		appInfo.setLogoLocPath(logoLocPaht);	//图片绝对路径
		appInfo.setLogoPicPath(logoPicPath);    //图片相对路径
		int result = deveAppInfoService.insertAppInfo(appInfo);
		if (result > 0) {
			return "redirect:/user/info/appInfoList"; //新增成功跳转到app列表信息页
		}else {
			return "developer/appinfoadd"; //新增失败又跳转回新增页面
		}
	}
	
	
	
	
	
	
	/**
	 * 获得当前类别下的子集类别列表
	 * @param pid
	 * @return
	 */
	@RequestMapping("/queryCategoryByParentId")
	@ResponseBody
	public Object queryCategoryByParentId(@RequestParam("pid") Integer pid){
		//根据当前类别ID查询当前类别下的子集类别信息列表
		List<AppCategory> categoryList = deveAppCategoryService.queryCategoryList(pid);
		return categoryList;
	}
	
	
	/**
	 * 绑定到新增信息的平台下拉框
	 * @return
	 */
	@RequestMapping(value = "getDataDictionaryByCode",method = RequestMethod.GET)
	@ResponseBody
	public Object getDataDictionaryByCode() {
		List<DataDictionary> listPlatform = deveDictionaryService.selePlatform();	//调用方法获得所属平台
		return listPlatform;
	}
	
	
	
	
	
	/**
	 * 负责按条件分页查询App信息列表
	 * @param querySoftwareName		软件名称
	 * @param queryStatus			软件状态
	 * @param queryFlatformId		所属平台
	 * @param queryCategoryLevel1	一级分类的类别ID
	 * @param queryCategoryLevel2	二级类别的类别ID
	 * @param queryCategoryLevel3	三级类别的类别ID
	 * @param currPageNo			当前页码
	 * @param model					保存数据
	 * @return	查询到分页记录
	 */
	@RequestMapping("/appInfoList")
	public String list(
			@RequestParam(value = "querySoftwareName",required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus",required = false) Integer queryStatus,
			@RequestParam(value = "queryFlatformId",required = false) Integer queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1",required = false) Integer queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2",required = false,defaultValue = "0") Integer queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3",required = false,defaultValue = "0") Integer queryCategoryLevel3,
			@RequestParam(value = "currPageNo",required = false,defaultValue = "1") Integer currPageNo,Model model
			) {
		//1、获取总记录数
		Integer totalCount = deveAppInfoService.queryTotalCount(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3);
		//2、封装Page实体类
		Page page = new Page();
		page.setCurrPageNo(currPageNo); //当前页码
		page.setPageSize(5); 	//每页显示数据行数
		page.setTotalCount(totalCount);  //总记录数
		//3、调用Service层按条件并分页查询App信息列表
		List<AppInfo> appInfoList = deveAppInfoService.queryAppInfoPage(querySoftwareName, queryStatus, queryFlatformId, queryCategoryLevel1, queryCategoryLevel2, queryCategoryLevel3, currPageNo, page.getPageSize());
		//4、调用Service层去查询状态信息
		List<DataDictionary> listState = deveDictionaryService.seleName();	//调用方法获得APP状态
		//5、调用Service层去查询所属平台信息
		List<DataDictionary> listPlatform = deveDictionaryService.selePlatform();	//调用方法获得所属平台
		//6、调用Service层去查询一级分类列表信息
		List<AppCategory> categoryLevel1List = deveAppCategoryService.queryCategoryList(null);
		//57、将查询到的数据保存到Model对象中（要做到查询参数的回显）
		List<AppCategory> categoryLevel2List = null;
		List<AppCategory> categoryLevel3List = null;
		if (queryCategoryLevel2 != 0 ) {
			 categoryLevel2List = queryCategoryLevel1==0 ?null : deveAppCategoryService.queryCategoryList(queryCategoryLevel1);
			if (queryCategoryLevel3 != 0 ) {
				 categoryLevel3List = queryCategoryLevel2==0 ?null : deveAppCategoryService.queryCategoryList(queryCategoryLevel2);
			}
		}
		model.addAttribute("pages",page);
		model.addAttribute("appInfoList",appInfoList);
		model.addAttribute("listState", listState);
		model.addAttribute("listPlatform", listPlatform);
		model.addAttribute("categoryLevel1List",categoryLevel1List);
		model.addAttribute("querySoftwareName",querySoftwareName);
		model.addAttribute("queryStatus",queryStatus);
		model.addAttribute("queryFlatformId",queryFlatformId);
		model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);
		model.addAttribute("categoryLevel2List",categoryLevel2List);
		model.addAttribute("categoryLevel3List",categoryLevel3List);
		//6、跳转到appinfolist.jsp
		return "developer/appinfolist";
	}
	
	
	

	
	/**
	 * 跳转新增app基础信息
	 * @return
	 */
	@RequestMapping("/appInfoAdd")
	public String appinfoAdd() {
		return "developer/appinfoadd";
	}
	
	
	
	
	
}
