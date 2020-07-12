package com.wch.blog;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.Tag;
import com.wch.blog.bean.Type;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.dao.TagDao;
import com.wch.blog.dao.TypeDao;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
public class TypeDaoTest {

    @Resource
    TypeDao typeDao;

    @Test
    public void insertTest(){
        Type type = new Type();
        type.setTypeName("技术框架");
        int i = typeDao.insertType(type);
        System.out.println(i);
    }

    @Test
    public void updateTest() {
        Type type = new Type();
        type.setId(2L);
        type.setTypeName("生活趣事");
        int i = typeDao.updateType(type);
        System.out.println(i);
    }

    @Test
    public void delete() {
        int i = typeDao.deleteType(2l);
        System.out.println(i);
    }

    @Test
    public void seleteListTest() {
        List<Type> types = typeDao.selectList();
        types.forEach(System.out::println);
    }

    @Test
    public void selectShowTypeAndBlogTest(){
        System.out.println(typeDao.selectShowTypeAndBlog(null));
    }

    @Test
    public void seleteOneTest() {
        Type type = typeDao.selectById(1l);
        System.out.println(type);;
    }
    @Resource
    TagDao tagDao;
    @Test
    public void tagupdateTest() {
        Tag tag = new Tag();
        tag.setId(1L);
        tag.setTagName("hahahah222");
        int i = tagDao.updateTag(tag);
        System.out.println(i);
    }

    @Resource
    BlogDao blogDao;
    @Test
    public void selectByBlogId(){
        Blog blog = blogDao.selectById(1L);
        System.out.println(blog);
    }
}
