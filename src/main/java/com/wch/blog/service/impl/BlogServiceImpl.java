package com.wch.blog.service.impl;

import com.wch.blog.bean.Blog;
import com.wch.blog.bean.BlogTag;
import com.wch.blog.bean.Tag;
import com.wch.blog.dao.BlogDao;
import com.wch.blog.dao.BlogTagDao;
import com.wch.blog.dao.TagDao;
import com.wch.blog.exception.ValidationException;
import com.wch.blog.service.BlogService;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
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
        blogDao.saveBatchBlogTag(blogTags);
        //2.更新时间
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = simpleDateFormat.format(date);
        blog.setUpdateTime(simpleDateFormat.parse(format));
        blogDao.updateBlog(blog);
        return 1;
    }

    @Transactional
    @Override
    public List<Blog> getShowAll(){

        List<Blog> blogs = blogDao.selectByShowAll();
        for(Blog blog : blogs){
            blog.setUser(null);
            blog.setCreateTime(null);
        }
        return blogs;
    }

    @Override
    public  Map<Integer,List<Blog>> getTimeSequence() {
        //1、根据时间倒序查询所有的博客
        List<Blog> blogs = blogDao.selectTimeSequence();
        //2、获得所有博客的最早最晚时间
        Blog blogTime = blogDao.selectTimeMaxAndMin();
        Date maxTime = blogTime.getMaxTime();
        Date minTime = blogTime.getMinTime();
        //3、对时间格式化
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //最新时间
        int max = Integer.valueOf(simpleDateFormat.format(maxTime).split("-")[0]);
        //最早时间
        int min = Integer.valueOf(simpleDateFormat.format(minTime).split("-")[0]);
        int format = 0;
        Map<Integer,List<Blog>> map = new LinkedHashMap<>();
        int i=0;
        int j=0;
       for(;max>=min;max--){
           List<Blog> temp = new ArrayList<>();;
           for(Blog blog : blogs){
               Date updateTime = blog.getUpdateTime();
               if(updateTime==null){
                   continue;
               }
               format = Integer.valueOf(simpleDateFormat.format(updateTime).split("-")[0]);
               if(format==max){
                   temp.add(blog);
               }
           }
           map.put(max,temp);
       }
       return map;
    }
}
