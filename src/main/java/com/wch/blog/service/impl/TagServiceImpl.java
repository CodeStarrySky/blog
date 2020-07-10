package com.wch.blog.service.impl;

import com.wch.blog.bean.Tag;
import com.wch.blog.bean.Type;
import com.wch.blog.dao.TagDao;
import com.wch.blog.service.TagService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    @Resource
    private TagDao tagDao;
    @Override
    public List<Tag> getAll() {
        return tagDao.selectAll();
    }

    @Override
    public Tag getTag(Long id) {
        return tagDao.selectById(id);
    }

    @Override
    public int saveTag(Tag tag) {
        return tagDao.saveTag(tag);
    }

    @Override
    public int updateTag(Tag tag) {
        return tagDao.updateTag(tag);
    }

    @Override
    public int deleteTag(Long id) {
        return tagDao.deleteTag(id);
    }

    @Override
    public Tag checkTagName(String tagName) {
        return tagDao.selectByName(tagName);
    }

    @Transactional
    @Override
    public List<Tag> getLimitTag(Integer number){
        return tagDao.selectByLimit(number);
    }

    @Override
    public List<Tag> getShowTagAndBlog() {
        return tagDao.selectShowTagAndBlog();
    }
}
