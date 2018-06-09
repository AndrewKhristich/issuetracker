package issuetracker.service;

import com.axmor.dao.IssueDao;
import com.axmor.dao.impl.IssueDaoImpl;
import com.axmor.exception.IssueNotFoundException;
import com.axmor.model.Issue;
import com.axmor.service.IssueService;
import com.axmor.service.impl.IssueServiceImpl;
import com.axmor.utils.DataSource;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class IssueServiceTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private IssueDao issueDao;
    private IssueService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initDB(){
        issueDao = new IssueDaoImpl();
        DataSource.createAllTables(queryPath);
        service = new IssueServiceImpl(issueDao);
    }

    @Test
    public void findAllArticlesTest(){
        List<Issue> allIssues = service.findAllIssues();
        assertEquals(3, allIssues.size());
    }

    @Test
    public void findAllArticlesByNameTest(){
        String articleName = "issue";
        List<Issue> allArticlesByName = service.findAllIssuesByName(articleName);
        assertEquals(3, allArticlesByName.size());
        List<Issue> first = service.findAllIssuesByName("first");
        assertEquals(1, first.size());
    }

    @Test
    public void findArticleByIdTest(){
        thrown.expect(IssueNotFoundException.class);
        Long notRealId = 5L;
        service.findIssuesById(notRealId);
        Long realId = 2L;
        Issue issueById = service.findIssuesById(realId);
        assertEquals(realId, issueById.getId());
    }

    @Test
    public void saveArticleTest(){
        Issue issue = new Issue();
        issue.setUsername("First");
        issue.setDescription("AAA");
        issue.setIssueName("new issue");
        service.saveIssue(issue);
        Issue issueById = service.findIssuesById(4L);
        assertEquals("First", issueById.getUsername());
        assertEquals("AAA", issueById.getDescription());
        assertEquals("new issue", issueById.getIssueName());

    }

    @Test
    public void findAuthentificatedUserArticlesTest(){
        String name = "Second";
        List<Issue> authentificatedUserIssues = service.findAuthentificatedUserIssue(name);
        assertEquals(2, authentificatedUserIssues.size());
        for (Issue issue : authentificatedUserIssues){
            assertEquals(name, issue.getUsername());
        }
    }
}
