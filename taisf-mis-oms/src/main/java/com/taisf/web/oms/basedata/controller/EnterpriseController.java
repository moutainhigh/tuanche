package com.taisf.web.oms.basedata.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.base.entity.AreaRegionEntity;
import com.taisf.services.base.service.AreaRegionService;
import com.taisf.services.enterprise.dto.EnterpriseListRequest;
import com.taisf.services.enterprise.entity.EnterpriseEntity;
import com.taisf.services.permission.api.EmployeeService;
import com.taisf.services.permission.entity.EmployeeEntity;
import com.taisf.services.supplier.api.SupplierService;
import com.taisf.services.supplier.entity.SupplierEntity;
import com.taisf.web.oms.common.page.PageResult;

@Controller
@RequestMapping("base/enterprise")
public class EnterpriseController {

	private static final Logger LOGGER = LoggerFactory.getLogger(EnterpriseController.class);

	@Resource(name = "ups.employeeServiceProxy")
	private EmployeeService employeeService;
	
	@Resource(name = "base.areaRegionServiceProxy")
	private AreaRegionService areaRegionService;
	
	@Resource(name = "supplier.supplierServiceProxy")
	private SupplierService supplierService;
	
	@RequestMapping("list")
	public String list(HttpServletRequest request) {
		// 员工列表
		DataTransferObject<List<EmployeeEntity>> employeeDto = employeeService.findAllEmployee();
		List<EmployeeEntity> employees = employeeDto.getData();
		request.setAttribute("employees", employees);

		return "enterprise/enterpriseList";
	}
	
	@RequestMapping("pageList")
    @ResponseBody
	public PageResult pageList(HttpServletRequest request, EnterpriseListRequest enterpriseRequest) {
		LogUtil.debug(LOGGER, "分页查询企业列表请求参数:{}", JsonEntityTransform.Object2Json(enterpriseRequest));
        PageResult result = new PageResult();
        try{
            if(Check.NuNObj(enterpriseRequest)){
                enterpriseRequest = new EnterpriseListRequest();
            }
            /*DataTransferObject<PagingResult<ProductVO>> resultDto = productService.getProductListPage(enterpriseRequest);
            if(resultDto.getCode()==DataTransferObject.ERROR){
                return result;
            }
            PagingResult<ProductVO> page = resultDto.getData();
            result.setRows(page.getList());
            result.setTotal(page.getTotal());*/
        }catch (Exception e){
            LogUtil.error(LOGGER, "分页查询企业列表异常:{}",e);
            return result;
        }
        return result;
	}

	/**
	 * 跳转企业信息页面(编辑或查看)
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("operate")
	public String operate(HttpServletRequest request) {
		// operate=3 添加 operate=2 编辑 operate=1 查看详情
		String operate = request.getParameter("operate");
		String id = request.getParameter("id");
		LogUtil.info(LOGGER, "跳转商品信息页面请求参数:{}", id, operate);

		if (Check.NuNStr(operate)) {
			return "redirect:/base/enterprise/list";
		}
		
		request.setAttribute("operate", operate);
		
		//所有省份 集合
		List<AreaRegionEntity> provinceList = areaRegionService.findAllAreaRegion(2);
		request.setAttribute("provinceList", provinceList);
		
		if(!("3").equals(operate) && !Check.NuNObj(id)) {
			/* DataTransferObject<ProductEntity> resultDto = productService.getProductById(ValueUtil.getintValue(id));
             if(resultDto.getCode()==DataTransferObject.ERROR){
                 return "redirect:/his/product/productList";
             }
             ProductEntity product = resultDto.getData();
             request.setAttribute("product",product);*/
			
			EnterpriseEntity entity = new EnterpriseEntity();
			
			if(!Check.NuNStr(entity.getProvinceCode())){
				List<AreaRegionEntity> citylist = areaRegionService.findAllByParentCode(Integer.parseInt(entity.getProvinceCode()));
				request.setAttribute("citylist", citylist);
			}
			if(!Check.NuNStr(entity.getCityCode())){
				List<AreaRegionEntity> countylist = areaRegionService.findAllByParentCode(Integer.parseInt(entity.getCityCode()));
				request.setAttribute("countylist", countylist);
			}
		}
		

		// 员工列表
		DataTransferObject<List<EmployeeEntity>> employeeDto = employeeService.findAllEmployee();
		List<EmployeeEntity> employees = employeeDto.getData();
		request.setAttribute("employees", employees);
		
		// 员工列表
		DataTransferObject<List<SupplierEntity>> supplierDto = supplierService.getAllSupplierList();
		List<SupplierEntity> suppliers = supplierDto.getData();
		request.setAttribute("suppliers", suppliers);

		return "enterprise/enterpriseOperate";
	}
	

}
