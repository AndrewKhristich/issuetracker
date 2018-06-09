package issuetracker.dao;

import com.axmor.dao.IssueDao;
import com.axmor.dao.CommentDao;
import com.axmor.dao.impl.IssueDaoImpl;
import com.axmor.dao.impl.CommentDaoImpl;
import com.axmor.model.Issue;
import com.axmor.model.Comment;
import com.axmor.utils.DataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;


public class CommentDaoTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private CommentDao commentDao;

    @Before
    public void initDB(){
        commentDao = new CommentDaoImpl();
        DataSource.createAllTables(queryPath);
    }

    @Test
    public void findAllCommentsByIssueIdTest(){
        Long id = 2l;
        List<Comment> comments = commentDao.findAllCommentsByIssueId(id);
        assertEquals(2, comments.size());
        for (Comment comment : comments){
            assertEquals(id, comment.getIssueId());
        }
    }

    @Test
    public void saveCommentTest(){
        IssueDao issueDao = new IssueDaoImpl();
        Comment comment = new Comment();
        comment.setUserName("First");
        Long articleId = 2l;
        comment.setIssueId(articleId);
        comment.setCommentDescription("AAAAA");
        String resolved = "Resolved";
        comment.setStatus(resolved);
        commentDao.saveComment(comment);
        List<Comment> allCommentsByArticleId = commentDao.findAllCommentsByIssueId(articleId);
        assertEquals(3, allCommentsByArticleId.size());
        Issue issueById = issueDao.findIssuesById(articleId);
        assertEquals(resolved, issueById.getStatus());
    }
}

