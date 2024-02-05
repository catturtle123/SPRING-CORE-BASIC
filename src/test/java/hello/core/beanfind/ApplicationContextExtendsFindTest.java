package hello.core.beanfind;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

public class ApplicationContextExtendsFindTest {

    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 중복 오류가 발생한다.")
    public void findBeanByParentTypeDuplicate() throws Exception {
        //given

        Assertions.assertThrows(NoUniqueBeanDefinitionException.class, () -> ac.getBean(DiscountPolicy.class));
        //when

        //then
    }

    @Test
    @DisplayName("부모 타입으로 조회시, 자식이 둘 이상 있으면, 빈이름을 지정하면 된다.")
    public void findBeanByParentTypeBeanName() throws Exception {
        //given
        DiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", DiscountPolicy.class);
        //when

        Assertions.assertInstanceOf(DiscountPolicy.class, discountPolicy);
        //then
    }

    @Test
    @DisplayName("특정 하위 타입으로 조회")
    public void findBeanBySubType() throws Exception {
        //given
        RateDiscountPolicy discountPolicy = ac.getBean("rateDiscountPolicy", RateDiscountPolicy.class);
        //when

        Assertions.assertInstanceOf(RateDiscountPolicy.class, discountPolicy);
        //then
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기")
    public void findBeanByParentType() throws Exception {
        //given
        Map<String, DiscountPolicy> beansOfType = ac.getBeansOfType(DiscountPolicy.class);
        //when

        //then
        Assertions.assertEquals(beansOfType.size(), 2);
    }

    @Test
    @DisplayName("부모 타입으로 모두 조회하기 - Object")
    public void findBeanByObjectType() throws Exception {
        //given
        Map<String, Object> beansOfType = ac.getBeansOfType(Object.class);
        //when
        for (String s : beansOfType.keySet()) {
            System.out.println("key = " + s + " value = " + beansOfType.get(s));
        }
        //then
//        Assertions.assertEquals(beansOfType.size(), 2);
    }


    @Configuration
    static class TestConfig {
        @Bean
        public DiscountPolicy rateDiscountPolicy() {
            return new RateDiscountPolicy();
        }

        @Bean
        public DiscountPolicy fixDiscountPolicy() {
            return new FixDiscountPolicy();
        }
    }
}
