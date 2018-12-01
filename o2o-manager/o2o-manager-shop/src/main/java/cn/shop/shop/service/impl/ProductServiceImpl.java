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
import cn.shop.utlis.baidu.Point;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
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
        if (productCondition.getShopId()!=null){
            criteria.andShopIdEqualTo(productCondition.getShopId());
        }
        PageHelper.startPage(1, 999);

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
     * @param imgAddr
     * @return
     */
    @Transactional
    @Override
    public ProductExecution addProduct(Product product,String imgAddr) {
        if (product != null  && product.getShopId() != null) {
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
//            ----------test
            product.setEnableStatus(1);
            try {
                int effectedNum=insertProject(product);
                System.out.println(effectedNum+"---------------------------------->添加商品<----------------------------------");
                System.out.println(product.getProductId()+"----------------------------------商品ID----------------------------------");
                ProductImg productImg=new ProductImg();
                productImg.setCreateTime(new Date());
                productImg.setProductId(product.getProductId());
                productImg.setImgAddr(imgAddr);
                int imgs=productImgMapper.insertSelective(productImg);
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
        return productMapper.selectByPrimaryKey(productId);
    }

    @Transactional
    protected int updatePro(Product product){
        return productMapper.updateByPrimaryKeySelective(product);
    }

    @Override
    public Product modifyProduct(Product product,String imgArr) {
        updatePro(product);
        ProductImgExample example = new ProductImgExample();
        ProductImgExample.Criteria criteria = example.createCriteria();
        criteria.andProductIdEqualTo(product.getProductId());

        ProductImg productImg=new ProductImg();
        productImg.setImgAddr(imgArr);
        productImg.setCreateTime(new Date());
        int rs=productImgMapper.updateByExampleSelective(productImg,example);
        Product product1=productMapper.selectByPrimaryKey(product.getProductId());
        return product1;
    }

    @Override
    public Integer deleteProduct(Integer productId) {
        Product product=productMapper.selectByPrimaryKey(productId);
//        0下架  1商品可见
        product.setEnableStatus(0);
        Integer rs=productMapper.updateByPrimaryKeySelective(product);
        return rs;
    }


}
