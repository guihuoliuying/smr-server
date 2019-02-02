package zws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import zws.test.TestPkgScanSeq;

public class Main {
    // ni mei de 
    @Autowired public TestPkgScanSeq obj; // 4

    public static void main(String[] args) {
        ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
//        System.out.println(obj.actorSystem);

        System.out.println(appCtx.getBean("main").getClass());
        System.out.println(new Main().getClass());
        System.out.println(Main.class);

        System.out.println(appCtx.getBean("main").getClass() == Main.class);
        System.out.println(new Main().getClass() == Main.class);
    }

}
