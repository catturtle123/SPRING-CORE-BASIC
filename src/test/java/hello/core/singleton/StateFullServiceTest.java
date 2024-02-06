package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import static org.junit.jupiter.api.Assertions.*;

class StateFullServiceTest {

    @Test
    void statefulServiceSingleton() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StateFullService stateService1 = ac.getBean(StateFullService.class);
        StateFullService stateService2 = ac.getBean(StateFullService.class);
        
        //ThreadA: A사용자 10000원 주문
        stateService1.order("userA", 10000);
        //ThreadB: B사용자 20000원 주문
        stateService2.order("userB", 20000);

        //ThreadA: 사용자A가 주문 금액을 조회
        int price = stateService1.getPrice();
        System.out.println("price = " + price);

        Assertions.assertThat(stateService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StateFullService stateFullService() {
            return new StateFullService();
        }
    }

}