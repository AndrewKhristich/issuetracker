package issuetracker.dao;

import com.axmor.dao.ArticleDao;
import com.axmor.dao.CommentDao;
import com.axmor.dao.UserDao;
import com.axmor.dao.impl.ArticleDaoImpl;
import com.axmor.dao.impl.CommentDaoImpl;
import com.axmor.dao.impl.UserDaoImpl;
import com.axmor.model.Article;
import com.axmor.model.Comment;
import com.axmor.utils.DataBaseInterface;
import issuetracker.DataBaseTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class CommentDaoTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private DataBaseInterface dataBaseUtil;
    private CommentDao commentDao;

    @Before
    public void initDB(){
        dataBaseUtil = new DataBaseTestUtil();
        commentDao = new CommentDaoImpl(dataBaseUtil);
        dataBaseUtil.createAllTables(queryPath);
    }

    @Test
    public void findAllCommentsByArticleIdTest(){
        Long id = 2l;
        List<Comment> comments = commentDao.findAllCommentsByArticleId(id);
        assertEquals(2, comments.size());
        for (Comment comment : comments){
            assertEquals(id, comment.getArticleId());
        }
    }

    @Test
    public void saveCommentTest(){
        ArticleDao articleDao = new ArticleDaoImpl(dataBaseUtil);
        Comment comment = new Comment();
        comment.setUserName("First");
        Long articleId = 2l;
        comment.setArticleId(articleId);
        comment.setCommentDescription("AAAAA");
        String resolved = "Resolved";
        comment.setStatus(resolved);
        commentDao.saveComment(comment);
        List<Comment> allCommentsByArticleId = commentDao.findAllCommentsByArticleId(articleId);
        assertEquals(3, allCommentsByArticleId.size());
        Article articleById = articleDao.findArticleById(articleId);
        assertEquals(resolved, articleById.getStatus());
    }
}

