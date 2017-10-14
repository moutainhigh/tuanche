package com.taisf.web.oms.supplierProductPackage;

import com.jk.framework.base.entity.DataTransferObject;
import com.jk.framework.base.page.PagingResult;
import com.jk.framework.base.utils.Check;
import com.jk.framework.base.utils.JsonEntityTransform;
import com.jk.framework.log.utils.LogUtil;
import com.taisf.services.base.entity.EmployeeSupplierEntity;
import com.taisf.services.base.service.EmployeeSupplierService;
import com.taisf.services.common.valenum.ProductClassifyEnum;
import com.taisf.services.permission.entity.EmployeeEntity;
import com.taisf.services.product.api.ProductService;
import com.taisf.services.product.entity.ProductEntity;
import com.taisf.services.supplier.api.SupplierPackageService;
import com.taisf.services.supplier.dto.SupplierProductRequest;
import com.taisf.services.supplier.entity.SupplierPackageEntity;
import com.taisf.services.supplier.vo.SupplierPackageVO;
import com.taisf.web.oms.common.constant.LoginConstant;
import com.taisf.web.oms.common.page.PageResult;
import com.taisf.web.oms.supplierProductController.SupplierProductController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("supplierProductPackage/")
public class SupplierProductPackageController {

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierProductController.class);

    @Autowired
    private SupplierPackageService supplierPackageService;

    @Autowired
    private ProductService productService;

    @Autowired
    private EmployeeSupplierService employeeSupplierService;

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:菜单录入列表
     **/
    @RequestMapping("list")
    public String list(HttpServletRequest request) {
        return "supplierPackage/supplierPackageList";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/11
     * @description:分页查询商品列表数据
     **/
    @RequestMapping("pageList")
    @ResponseBody
    public PageResult pageList(HttpServletRequest request, SupplierProductRequest supplierProductRequest) {
        PageResult pageResult = new PageResult();
        try {
            DataTransferObject<PagingResult<SupplierPackageEntity>> dto = supplierPackageService.pageListSupplierProduct(supplierProductRequest);
            if (!Check.NuNObj(dto.getData())) {
                List<SupplierPackageVO> listVo = new ArrayList<>();
                dto.getData().getList().parallelStream().forEach((x) -> {
                    SupplierPackageVO vo = new SupplierPackageVO();
                    BeanUtils.copyProperties(x, vo);
                    vo.setBigName(productService.getProductById(x.getBigCode()).getData().getProductName());
                    vo.setSmallName(productService.getProductById(x.getSmallCode()).getData().getProductName());
                    vo.setSuName(productService.getProductById(x.getSuCode()).getData().getProductName());
                    vo.setTangName(productService.getProductById(x.getTangCode()).getData().getProductName());
                    vo.setDrinkName(productService.getProductById(x.getDrinkCode()).getData().getProductName());
                    vo.setFoodName(productService.getProductById(x.getFoodCode()).getData().getProductName());
                    vo.setFruitName(productService.getProductById(x.getFruitCode()).getData().getProductName());
                    listVo.add(vo);
                });
                pageResult.setRows(listVo);
                pageResult.setTotal((long) listVo.size());
            }
        } catch (Exception e) {
            LogUtil.info(LOGGER, "params:{}", JsonEntityTransform.Object2Json(supplierProductRequest));
            LogUtil.error(LOGGER, "error:{}", e);
            return new PageResult();
        }
        return pageResult;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:去添加组合套餐页面
     **/
    @RequestMapping("toAdd")
    public String toAdd(HttpServletRequest request) {
        //不同分类集合
        productCla01sifyList(request);
        return "supplierPackage/addSupplierPackage";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:新增组合套餐
     **/
    @RequestMapping("addSupplierProductPackage")
    @ResponseBody
    public DataTransferObject<Void> addSupplierProductPackage(HttpServletRequest request, SupplierPackageEntity supplierPackageEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(supplierPackageEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            //商家code
            HttpSession session = request.getSession();
            EmployeeEntity employeeEntity = (EmployeeEntity) session.getAttribute(LoginConstant.SESSION_KEY);
            //根据userId 得到 商家code
            EmployeeSupplierEntity employeeSupplierEntity = employeeSupplierService.getByUserId(employeeEntity.getUserId());
            supplierPackageEntity.setSupplierCode(employeeSupplierEntity.getUserId());
            dto = supplierPackageService.saveSupplierPackage(supplierPackageEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            LogUtil.info(LOGGER, "param:{}", JsonEntityTransform.Object2Json(supplierPackageEntity));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }


    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:删除组合套餐
     **/
    @RequestMapping("deleteSupplierPackage")
    @ResponseBody
    public DataTransferObject<Void> deleteSupplierPackage(HttpServletRequest request, Integer id) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(id)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            dto = supplierPackageService.deleteByPrimaryKey(id);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            LogUtil.info(LOGGER, "param:{}", id);
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统错误");
            return dto;
        }
        return dto;
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:去编辑组合套餐页面
     **/
    @RequestMapping("toedit")
    public String toedit(HttpServletRequest request, Integer id) {
        //不同分类集合
        productCla01sifyList(request);
        //当前编辑的组合套餐
        SupplierPackageEntity packageEntity = supplierPackageService.getSupplierPackageById(id).getData();
        request.setAttribute("packageEntity", packageEntity);
        return "supplierPackage/editSupplierPackage";
    }

    /**
     * @author:zhangzhengguang
     * @date:2017/10/13
     * @description:新增组合套餐
     **/
    @RequestMapping("updateSupplierProductPackage")
    @ResponseBody
    public DataTransferObject<Void> updateSupplierProductPackage(HttpServletRequest request, SupplierPackageEntity supplierPackageEntity) {
        DataTransferObject<Void> dto = new DataTransferObject<>();
        if (Check.NuNObj(supplierPackageEntity)) {
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("参数异常");
            return dto;
        }
        try {
            dto = supplierPackageService.updateSupplierPackage(supplierPackageEntity);
        } catch (Exception e) {
            LogUtil.error(LOGGER, "error:{}", e);
            LogUtil.info(LOGGER, "param:{}", JsonEntityTransform.Object2Json(supplierPackageEntity));
            dto.setErrCode(DataTransferObject.ERROR);
            dto.setErrorMsg("系统异常");
            return dto;
        }
        return dto;
    }

    public void productCla01sifyList(HttpServletRequest request) {
        //不同分类集合
        List<ProductEntity> dahunList = productService.getListByClassify(ProductClassifyEnum.DAHUN.getCode()).getData();
        List<ProductEntity> xiaohunList = productService.getListByClassify(ProductClassifyEnum.XIAOHUN.getCode()).getData();
        List<ProductEntity> suList = productService.getListByClassify(ProductClassifyEnum.SU.getCode()).getData();
        List<ProductEntity> tangList = productService.getListByClassify(ProductClassifyEnum.TANG.getCode()).getData();
        List<ProductEntity> yinpinList = productService.getListByClassify(ProductClassifyEnum.YINPIN.getCode()).getData();
        List<ProductEntity> zhushiList = productService.getListByClassify(ProductClassifyEnum.ZHUSHI.getCode()).getData();
        List<ProductEntity> shuiguoList = productService.getListByClassify(ProductClassifyEnum.SHUIGUO.getCode()).getData();
        request.setAttribute("dahunList", dahunList);
        request.setAttribute("xiaohunList", xiaohunList);
        request.setAttribute("suList", suList);
        request.setAttribute("tangList", tangList);
        request.setAttribute("yinpinList", yinpinList);
        request.setAttribute("zhushiList", zhushiList);
        request.setAttribute("shuiguoList", shuiguoList);
    }
}
