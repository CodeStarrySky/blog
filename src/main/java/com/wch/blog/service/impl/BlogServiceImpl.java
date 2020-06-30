package com.wch.blog.service.impl;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import com.wch.blog.bean.Tag;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.dao.BlogTagDao;
import com.wch.blog.dao.TagDao;
import com.wch.blog.exception.ValidationException;
import com.wch.blog.service.BlogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.SimpleFormatter;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    BlogDao blogDao;

    @Resource
    BlogTagDao blogTagDao;

    @Resource
    TagDao tagDao;
    public List<Blog> getAll(){

        List<Blog> blogs = blogDao.selectAll();

        return blogs;
    }

    public List<Blog> getByBlog(Blog blog){
        return blogDao.selectByBlog(blog);
    }

    @Override
    public int deleteBlog(Long id) {
        return blogDao.deleteById(id);
    }

    @Transactional
    @Override
    public Blog findById(Long id){
        Blog blog = blogDao.selectById(id);
        List<Tag> tags = blog.getTags();
        String tagIds = "";
        for(Tag tag : tags){
            tagIds = tagIds + tag.getId()+ ",";
        }
//        System.out.println(tagIds.substring(0,tagIds.length()-1));
        blog.setTagIds(tagIds);
        return blog;
    }

    @Transactional
    @Override
    public int save(Blog blog) throws ParseException {
        Date date = new Date();
        SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = simpleFormatter.format(date);
        blog.setCreateTime(simpleFormatter.parse(str));
        blog.setUpdateTime(simpleFormatter.parse(str));
        int i = blogDao.saveBlog(blog);
        System.out.println(blog);
        String tagIds = blog.getTagIds();
        if(tagIds==null||tagIds.equals("")){
            throw new ValidationException("至少选择一个标签");
        }
        String[] tagIdArr = tagIds.split(",");
        List<BlogTag> blogTags = new ArrayList<>();

        for(String tagId : tagIdArr){
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blog.getId());
            blogTag.setTagId(Long.parseLong(tagId));
            blogTags.add(blogTag);
        }
        int n = blogDao.saveBatchBlogTag(blogTags);
        if(n<1){
            throw new ValidationException("标签保存失败");
        }


        return i;
    }

    @Transactional
    @Override
    public int updateBlog(Blog blog) throws ParseException {
        //1.更新blog_tag的关联关系
        blogDao.deleteBlogTagByBlogId(blog.getId());
        String tagIds = blog.getTagIds();
        String[] tagIdArr = tagIds.split(",");
        List<BlogTag> blogTags = new ArrayList<>();
        for(String tagId : tagIdArr){
            BlogTag blogTag = new BlogTag();
            blogTag.setBlogId(blog.getId());
            blogTag.setTagId(Long.parseLong(tagId));
            blogTags.add(blogTag);
        }
        blogDao.saveBatchBlogTag(blogTags);
        //2.更新时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        blog.setUpdateTime(simpleDateFormat.parse(format));
        blogDao.updateBlog(blog);
        return 1;
    }







}
