package cn.shop.potal.service.impl;

import cn.shop.mapper.ProductImgMapper;
import cn.shop.mapper.ProductMapper;
import cn.shop.pojo.Product;
import cn.shop.pojo.ProductExample;
import cn.shop.pojo.ProductImg;
import cn.shop.pojo.ProductImgExample;
import cn.shop.potal.service.ProductPotalService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductPotalServiceImpl implements ProductPotalService{
    @Autowired
    ProductMapper productMapper;
    @Autowired
    ProductImgMapper productImgMapper;
    /**
     * @Description:    按id查询店铺下面的所有商品 分页
     * @Author:         oy
     * @CreateDate:     2018/11/23 0023 下午 3:05
     */
    @Override
    public List<Product> selectByExample(Product product, Integer pageNum, Integer pagesize) {
        ProductExample productExample=new ProductExample();
        ProductExample.Criteria criteria=productExample.createCriteria();
        //按照id查询
        criteria.andShopIdEqualTo(product.getShopId());
        if(product.getProductCategoryId()!=null&&product.getProductCategoryId()>-1){
            //按照商品类别
            criteria.andProductCategoryIdEqualTo(product.getProductCategoryId());
        }
        if(product.getProductName()!=null&&!"".equals(product.getProductName())){
            //按照商品名模糊查询
            criteria.andProductNameLike("%"+product.getProductName()+"%");
        }
        //分页
        PageHelper.startPage(pageNum,pagesize);

        return productMapper.selectByExample(productExample);
    }
    /**
     * @Description:    根据条件查出总行数
     * @Author:         oy
     * @CreateDate:     2018/11/23 0023 下午 3:34
     */
    @Override
    public Long countByExample(Product product) {
        ProductExample productExample=new ProductExample();
        ProductExample.Criteria criteria=productExample.createCriteria();
        //按照id查询
        criteria.andShopIdEqualTo(product.getShopId());
        if(product.getProductCategoryId()!=null&&product.getProductCategoryId()>-1){
        //按照商品类别
        criteria.andProductCategoryIdEqualTo(product.getProductCategoryId());
        }
        if(product.getProductName()!=null&&!"".equals(product.getProductName())){
            //按照商品名模糊查询
            criteria.andProductNameLike("%"+product.getProductName()+"%");
        }


        return productMapper.countByExample(productExample);
    }
    /**
     * @Description:    按照id查询商品
     * @Author:         oy
     * @CreateDate:     2018/11/23 0023 下午 3:59
     */
    @Override
    public Product selectByPrimaryKey(Integer productId) {
        Product product = productMapper.selectByPrimaryKey(productId);
        product.setProductImgs(getProductImgByProductId(productId));
        return product;
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
}
