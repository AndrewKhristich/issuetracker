package issuetracker.dao;

import com.axmor.dao.IssueDao;
import com.axmor.dao.impl.IssueDaoImpl;
import com.axmor.model.Issue;
import com.axmor.utils.DataSource;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueDaoTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private IssueDao issueDao;

    @Before
    public void initDB(){
        issueDao = new IssueDaoImpl();
        DataSource.createAllTables(queryPath);
    }

    @Test
    public void findAllArticlesTest(){
        List<Issue> allIssues = issueDao.findAllIssues();
        assertEquals(3, allIssues.size());
    }

    @Test
    public void findAuthentificatedUserArticles(){
        String name = "Second";
        List<Issue> authentificatedUserIssues = issueDao.findAuthentificatedUserIssues(name);
        assertEquals(2, authentificatedUserIssues.size());
        for (Issue issue : authentificatedUserIssues){
            assertEquals(name, issue.getUsername());
        }
    }

    @Test
    public void findArticlesByNameTest(){
        String search = "first";
        String searchAll = "issue";

        List<Issue> firstIssue = issueDao.findIssuesByName(search);
        List<Issue> allIssues = issueDao.findIssuesByName(searchAll);

        assertEquals(1, firstIssue.size());
        assertEquals(3, allIssues.size());
    }

    @Test
    public void findArticleByIdTest(){
        Long id = 2L;
        Issue issueById = issueDao.findIssuesById(id);
        assertNotNull(issueById);
        assertEquals(id, issueById.getId());
    }

    @Test
    public void saveArticleTest(){
        Issue issue = new Issue();
        issue.setUsername("First");
        issue.setDescription("AAAAAAA");
        issue.setIssueName("new issue");
        issueDao.saveIssues(issue);
        Issue issueById = issueDao.findIssuesById(4L);
        assertEquals("First", issueById.getUsername());
        assertEquals("AAAAAAA", issueById.getDescription());
        assertEquals("new issue", issueById.getIssueName());
    }
}
