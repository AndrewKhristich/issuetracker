package issuetracker.service;

import com.axmor.dao.ArticleDao;
import com.axmor.dao.impl.ArticleDaoImpl;
import com.axmor.exception.ArticleNotFoundException;
import com.axmor.model.Article;
import com.axmor.service.ArticleService;
import com.axmor.service.impl.ArticleServiceImpl;
import com.axmor.utils.DataBaseInterface;
import issuetracker.DataBaseTestUtil;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArticleServiceTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private DataBaseInterface dataBaseUtil;
    private ArticleDao articleDao;
    private ArticleService service;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initDB(){
        dataBaseUtil = new DataBaseTestUtil();
        articleDao = new ArticleDaoImpl(dataBaseUtil);
        dataBaseUtil.createAllTables(queryPath);
        service = new ArticleServiceImpl(articleDao);
    }

    @Test
    public void findAllArticlesTest(){
        List<Article> allArticles = service.findAllArticles();
        assertEquals(3, allArticles.size());
    }

    @Test
    public void findAllArticlesByNameTest(){
        String articleName = "article";
        List<Article> allArticlesByName = service.findAllArticlesByName(articleName);
        assertEquals(3, allArticlesByName.size());
        List<Article> first = service.findAllArticlesByName("first");
        assertEquals(1, first.size());
    }

    @Test
    public void findArticleByIdTest(){
        thrown.expect(ArticleNotFoundException.class);
        Long notRealId = 5L;
        service.findArticleById(notRealId);
        Long realId = 2L;
        Article articleById = service.findArticleById(realId);
        assertEquals(realId, articleById.getId());
    }

    @Test
    public void saveArticleTest(){
        Article article = new Article();
        article.setUsername("First");
        article.setDescription("AAA");
        article.setArticleName("new article");
        service.saveArticle(article);
        Article articleById = service.findArticleById(4L);
        assertEquals("First", articleById.getUsername());
        assertEquals("AAA", articleById.getDescription());
        assertEquals("new article", articleById.getArticleName());

    }

    @Test
    public void findAuthentificatedUserArticlesTest(){
        String name = "Second";
        List<Article> authentificatedUserArticles = service.findAuthentificatedUserArticles(name);
        assertEquals(2, authentificatedUserArticles.size());
        for (Article article : authentificatedUserArticles){
            assertEquals(name, article.getUsername());
        }
    }
}
