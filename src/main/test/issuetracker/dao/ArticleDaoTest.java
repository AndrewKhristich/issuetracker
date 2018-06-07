package issuetracker.dao;

import com.axmor.dao.ArticleDao;
import com.axmor.dao.impl.ArticleDaoImpl;
import com.axmor.model.Article;
import com.axmor.utils.DataBaseInterface;
import issuetracker.DataBaseTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ArticleDaoTest {

    private final String queryPath = "src\\main\\resources\\public\\sql\\create_tables";
    private DataBaseInterface dataBaseUtil;
    private ArticleDao articleDao;

    @Before
    public void initDB(){
        dataBaseUtil = new DataBaseTestUtil();
        articleDao = new ArticleDaoImpl(dataBaseUtil);
        dataBaseUtil.createAllTables(queryPath);
    }

    @Test
    public void findAllArticlesTest(){
        List<Article> allArticles = articleDao.findAllArticles();
        assertEquals(3, allArticles.size());
    }

    @Test
    public void findAuthentificatedUserArticles(){
        String name = "Second";
        List<Article> authentificatedUserArticles = articleDao.findAuthentificatedUserArticles(name);
        assertEquals(2, authentificatedUserArticles.size());
        for (Article article : authentificatedUserArticles){
            assertEquals(name, article.getUsername());
        }
    }

    @Test
    public void findArticlesByNameTest(){
        String search = "first";
        String searchAll = "article";

        List<Article> firstArticle = articleDao.findArticlesByName(search);
        List<Article> allArticles = articleDao.findArticlesByName(searchAll);

        assertEquals(1, firstArticle.size());
        assertEquals(3, allArticles.size());
    }

    @Test
    public void findArticleByIdTest(){
        Long id = 2L;
        Article articleById = articleDao.findArticleById(id);
        assertNotNull(articleById);
        assertEquals(id, articleById.getId());
    }

    @Test
    public void saveArticleTest(){
        Article article = new Article();
        article.setUsername("First");
        article.setDescription("AAAAAAA");
        article.setArticleName("new article");
        articleDao.saveArticle(article);
        Article articleById = articleDao.findArticleById(4L);
        assertEquals("First", articleById.getUsername());
        assertEquals("AAAAAAA", articleById.getDescription());
        assertEquals("new article", articleById.getArticleName());
    }
}
