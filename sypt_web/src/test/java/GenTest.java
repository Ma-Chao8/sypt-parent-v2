import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;

import javax.sql.DataSource;

public class GenTest {
    private static String author="lgc";//作者
    private static String outputDir="C:\\Users\\guopeng\\Desktop\\lgc\\sypt_parent\\sypt_web\\src\\main\\java";
    private static String driver="com.mysql.jdbc.Driver";
    private static String url="jdbc:mysql://192.168.3.3:3306/sypt?useUnicode=true&characterEncoding=utf8";
    private static String username="root";
    private static String password="root";
    private static String tablePrefix="sys_";
    private static String[] tables= {"sys_user"};
    private static String parentPackage="com.lgc";
    private static String dao="dao";
    private static String service="service";
    private static String entity="entity";
    private static String controller="controller";
    private static String mapperxml="dao";


    @Test
    public void testGennerator(){
        //全局配置
        GlobalConfig config = new GlobalConfig();
        config.setAuthor(author)//作者
                .setOutputDir(outputDir)//生成输出的路径
                .setFileOverride(false)//文件是否覆盖
               .setIdType(IdType.AUTO)//主键生成策略
               .setServiceName("%sevice")//service接口生成策略
               .setBaseResultMap(true)//是否生成ResultMap
                .setBaseColumnList(true);//生成通用sql字段
        //数据库配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setDbType(DbType.MYSQL)
                .setDriverName(driver)
                .setUrl(url)
                .setUsername(username)
                .setPassword(password);

        //策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
         strategyConfig.setCapitalMode(true)//全局大写命名
                        .setNaming(NamingStrategy.underline_to_camel)//数据库表映射到实体类的命名策略
                        .setTablePrefix(tablePrefix)//表前缀
                        .setInclude(tables);//生成的表

        //包命名策略
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(parentPackage)
                .setMapper(dao)
                .setService(service)
                .setController(controller)
                .setEntity(entity)
                .setXml(mapperxml);

        //5.整合配置
        AutoGenerator autoGenerator = new AutoGenerator();
        autoGenerator.setGlobalConfig(config)
                .setDataSource(dataSourceConfig)
                .setStrategy(strategyConfig)
                .setPackageInfo(packageConfig);
        //执行
        autoGenerator.execute();
    }

}
