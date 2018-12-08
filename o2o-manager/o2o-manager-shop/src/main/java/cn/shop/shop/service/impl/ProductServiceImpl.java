package cn.shop.shop.service.impl;

import cn.shop.dto.ProductExecution;
import cn.shop.enums.ProductStateEnum;
import cn.shop.mapper.ProductImgMapper;
import cn.shop.mapper.ProductMapper;
import cn.shop.pojo.Product;
import cn.shop.pojo.ProductExample;
import cn.shop.pojo.ProductImg;
import cn.shop.pojo.ProductImgExample;
import cn.shop.shop.service.ProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yzg
 * @date 2018/11/19 - 11:28
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductImgMapper productImgMapper;

    protected int insertProject(Product product){
        int effectedNum = productMapper.insertSelective(product);
        return effectedNum;
    }
    /**
     * 查询所有商品
     * @return
     */
    @Override
    public ProductExecution queryProduct(Product productCondition) {
        ProductExample example = new ProductExample();
        ProductExample.Criteria criteria = example.createCriteria();
        //判断shopid是否为空
        if (productCondition.getShopId()!=null){
            criteria.andShopIdEqualTo(productCondition.getShopId());
        }
        //分页
        PageHelper.startPage(1, 999);
        //执行查询
        List<Product> productList = productMapper.selectByExample(example);

        ProductExecution pe = new ProductExecution();

        PageInfo<Product> plist=new PageInfo<>(productList);
        pe.setProductList(plist);
        pe.setCount((int)plist.getTotal());
        return pe;
    }

    /**
     * 添加商品
     * @param product
     * @param
     * @return
     */
    @Override
    public ProductExecution addProduct(Product product,String imgAddrs) {
        if (product != null  && product.getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
//            ----------test
            product.setEnableStatus(1);
            try {
                int effectedNum=insertProject(product);
                //调用批量插入图片的方法
                int imgs=addProductImgs(imgAddrs,product.getProductId());
                if (imgs <= 0) {
                    throw new RuntimeException("创建商品失败");
                }
            } catch (Exception e) {
                throw new RuntimeException("创建商品失败:" + e.toString());
            }
            return new ProductExecution(ProductStateEnum.SUCCESS, product);
        } else {
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    /**
     * 根据id查找商品信息
     * @param productId
     * @return
     */
    @Override
    public Product getProductById(int productId) {
        //执行查询
        Product product = productMapper.selectByPrimaryKey(productId);
        //获得图片信息
        List<ProductImg> productImgList = getProductImgByProductId(product.getProductId());
        //图片信息存入商品信息中
        product.setProductImgs(productImgList);
        return product;
    }

    /**
     * 修改商品
     * @param product
     * @return
     */
    @Override
    public ProductExecution updateProduct(Product product,String imgAddrs) {
        ProductExecution execution = new ProductExecution();
        //删除图片信息
        deleteProductImg(product.getProductId());
        //重新添加图片信息
        int imgs = addProductImgs(imgAddrs,product.getProductId());
        if(imgs>0){
            //创建条件
            ProductExample example = new ProductExample();
            ProductExample.Criteria criteria = example.createCriteria();
            //根据productId修改
            criteria.andProductIdEqualTo(product.getProductId());
            //执行修改
            int count = productMapper.updateByExampleSelective(product,example);
            if(count>0){
                execution.setState(ProductStateEnum.SUCCESS.getState());
            }else{
                execution.setState(ProductStateEnum.INNER_ERROR.getState());
            }
        }else {
            execution.setState(ProductStateEnum.INNER_ERROR.getState());
        }
        return execution;
    }




    /**
     * 通过商品id获取图片集合
     * @param productId
     * @return
     */
    private List<ProductImg> getProductImgByProductId(Integer productId){
        ProductImgExample example = new ProductImgExample();
        ProductImgExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        return productImgMapper.selectByExample(example);
    }

    /**
     * 批量添加图片
     * @param imgAddrs
     * @param productId
     * @return
     */
    private int addProductImgs(String imgAddrs,Integer productId){
        int count = 0;
        //执行插入
        String[] imgaddr = imgAddrs.split(",");
        List<ProductImg> imgs = new ArrayList<>();
        ProductImg productImg = new ProductImg();
        productImg.setCreateTime(new Date());
        productImg.setProductId(productId);
        for (String s : imgaddr) {
            if(s==null||"".equals(s)){
                break;
            }
            productImg.setImgAddr(s);
            imgs.add(productImg);
        }
        try {
            System.out.println("imgs:"+imgs.size());
            count = productImgMapper.insertList(imgs);
        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 删除图片信息
     * @param productId
     * @return
     */
    private int deleteProductImg(Integer productId){
        int count = 0;
        ProductImgExample example = new ProductImgExample();
        ProductImgExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(productId);
        count = productImgMapper.deleteByExample(example);
        return count;
    }

    /**
     * 商品上下架
     * @param productId
     * @return
     */
    @Override
    public ProductExecution updateStatusProduct(Integer productId,Integer enableStatus) {
        //创建商品
        Product product = new Product();
        product.setProductId(productId);
        //设置商品状态
        product.setEnableStatus(enableStatus);
        //进行修改
        try {
            productMapper.updateByPrimaryKeySelective(product);
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
    }

    /**
     * 删除商品
     * @param productId
     * @return
     */
    @Override
    public ProductExecution deleteProduct(Integer productId) {
        try {
            productMapper.deleteByPrimaryKey(productId);
            return new ProductExecution(ProductStateEnum.SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new ProductExecution(ProductStateEnum.INNER_ERROR);
        }
    }
}
